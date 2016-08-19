package controller;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import modelo.Ruta;
import modelo.Actividad;
import modelo.Dificultad;
import modelo.Formato;
import modelo.Persona;
import modelo.Privacidad;
import modeloDAO.ActividadDAO;
import modeloDAO.PersonaDAO;
import modeloDAO.PuntoDao;
import modeloDAO.RutaDAO;

public class RutaBean {


	private RutaDAO rDao = new RutaDAO();
	private Ruta ruta = new Ruta();
	private long idActividad;
	
	private boolean control = true;

	public RutaBean(){}
	
	public Ruta getRuta() {
		if(!control)
		{
			this.ruta= new Ruta();
			this.control = true;
		}
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}
	
	public String altaRuta()
	{
		PersonaDAO pDAO= new PersonaDAO();
		Persona persona= new Persona();
		ActividadDAO aDAO= new ActividadDAO();
		Actividad actividad=new Actividad();
		
		HttpSession session;
		FacesContext context = FacesContext.getCurrentInstance();
	    session = (HttpSession) context.getExternalContext().getSession(true);
	       
		Long id = (Long) session.getAttribute("usrId");
		
		persona=pDAO.recuperarPersona(id);
		actividad=aDAO.recuperarActividad(idActividad);
		
		ruta.setPromedio(0);
		ruta.setCantRealizadas(0);
		ruta.setFotos(null);
		ruta.setOwner(persona);
		ruta.setRegistroRealizadas(null);
		ruta.setActividad(actividad);

		
		rDao.guardarRuta(ruta);
		
		PuntoDao puntoDao = PuntoDao.instance;
		puntoDao.guardarPuntos(this.ruta);
		
		this.control= false;
		return "usuario_opOk";
	}
	
	public SelectItem[] getPrivacidadValues()
	{
		SelectItem[] items = new SelectItem[Privacidad.values().length];
	    int i = 0;
	    for(Privacidad p: Privacidad.values())
	    {
	      items[i++] = new SelectItem(p, p.name());
	    }
	    return items;
	  }
	
	public SelectItem[] getFormatoValues()
	{
		SelectItem[] items = new SelectItem[Formato.values().length];
	    int i = 0;
	    for(Formato f: Formato.values())
	    {
	      items[i++] = new SelectItem(f, f.name());
	    }
	    return items;
	}
	
	public SelectItem[] getDificultadValues()
	{
		SelectItem[] items = new SelectItem[Dificultad.values().length];
	    int i = 0;
	    for(Dificultad d: Dificultad.values())
	    {
	      items[i++] = new SelectItem(d, d.name());
	    }
	    return items;
	}

	public long getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(long idActividad) {
		this.idActividad = idActividad;
	}
	
}
