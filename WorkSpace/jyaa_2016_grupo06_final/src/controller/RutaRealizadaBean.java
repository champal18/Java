package controller;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import modelo.Ruta;
import modelo.RutaRealizada;
import modelo.Persona;
import modeloDAO.PersonaDAO;
import modeloDAO.RutaDAO;
import modeloDAO.RutaRealizadaDAO;

public class RutaRealizadaBean 
{
	private RutaRealizadaDAO rDao = new RutaRealizadaDAO();
	private RutaRealizada rutaRealizada = new RutaRealizada();
	private String fueRealizada = new String();
	
	public RutaRealizada getRutaRealizada()
	{
		this.rutaRealizada = new RutaRealizada();
		return rutaRealizada;
	}

	public void setRutaRealizada(RutaRealizada rutaRealizada) {
		this.rutaRealizada = rutaRealizada;
	}
	
	public RutaRealizadaBean(){}
	
	public String altaRutaRealizada()
	{
		// Busco la persona que realizo la ruta en la BD.
		HttpSession session;
		FacesContext context = FacesContext.getCurrentInstance();
	    session = (HttpSession) context.getExternalContext().getSession(true);
		Long idOwner = (Long) session.getAttribute("usrId");
		Persona owner = new Persona();
		PersonaDAO pDao = new PersonaDAO();
		owner = pDao.recuperarPersona(idOwner);
		this.rutaRealizada.setOwner(owner);
		
		// Busco la ruta que fue seleccionada en la BD
		Long idRuta = (Long) session.getAttribute("idRuta");
		RutaDAO rutaDao = new RutaDAO();
		Ruta ruta = rutaDao.recuperarRuta(idRuta);
		this.rutaRealizada.setRuta(ruta);
		
		float valorTotal = ruta.getPromedio() * ruta.getCantRealizadas();
		ruta.setCantRealizadas(ruta.getCantRealizadas()+1);
		
		float promedio = (valorTotal+this.rutaRealizada.getValoracion()) / ruta.getCantRealizadas();
		int aux = (int)(promedio*100);
		float prom = aux/100f;
		
		ruta.setPromedio(prom);
		
		this.rDao.guardarRuta(this.rutaRealizada);
		rutaDao.modificarRuta(ruta);
		
		return "usuario_opOk";
	}

	public String getFueRealizada()
	{
		HttpSession session;
		FacesContext context = FacesContext.getCurrentInstance();
	    session = (HttpSession) context.getExternalContext().getSession(true);
		Long idOwner = (Long) session.getAttribute("usrId");
		Long idRuta = (Long) session.getAttribute("idRuta");
		
		RutaDAO rutaDao = new RutaDAO();
		Ruta ruta = rutaDao.recuperarRuta(idRuta);
		
		if(ruta.getOwner().getId() != idOwner)	// Si la ruta seleccionada NO pertenece al usuario que intenta valorar
		{
			List<RutaRealizada> lista = this.rDao.fueRealizada(idOwner);
			if(lista != null)
			{
				for (RutaRealizada rutaRealizada : lista) 
				{
					if(rutaRealizada.getRuta().getId() == idRuta)
						return "disabled";
				}
			}
			return "";
		}
		else
			return "disabled";
	}

	public void setFueRealizada(String fueRealizada) {
		this.fueRealizada = fueRealizada;
	}
}
