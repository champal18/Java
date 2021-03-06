package controller;

import java.util.Collections;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import jersey.repackaged.com.google.common.collect.Lists;
import modelo.Persona;
import modelo.Ruta;
import modelo.Sexo;
import modeloDAO.PersonaDAO;
import modeloDAO.RutaDAO;

public class AdminBean 
{
	private PersonaDAO pDao = new PersonaDAO();
	private Persona adminLogin;
	
	// Lista de usuarios de la BD
	private List<Persona> listaUsuarios = pDao.recuperarUsuarios();
	private List<Persona> backupUsuarios = pDao.recuperarUsuarios();

	// Enumerativo para definir orden actual de la lista
	private enum orden {Alfabeticamente, Antiguedad, Cantidad_De_Rutas};
	private enum criterio {Ascendente, Descendente};

	// Ordenamiento actual
	private orden ordenActual = orden.Alfabeticamente;
	private criterio criterioActual = criterio.Ascendente;
	
	// Flags para evitar procesamiento de mas
	private boolean flagOrden = false;

	public AdminBean(){}
	
	public String habilitarPersona(Persona selec)	// --> parametro enviado desde el xhtml
	{
		selec.setHabilitado(!selec.getHabilitado());
		pDao.modificarPersona(selec);
		return null;
	}
	
	public Persona getAdminLogin()
	{
		if(this.adminLogin == null)
		{	
			HttpSession session;
			FacesContext context = FacesContext.getCurrentInstance();
		    session = (HttpSession) context.getExternalContext().getSession(true);
		       
			Long id = (Long) session.getAttribute("usrId");
			if(id != null)
			{
				this.adminLogin = pDao.recuperarPersona(id);
			}
		}
		return adminLogin;
	}

	public void setAdminLogin(Persona adminLogin) {
		this.adminLogin = adminLogin;
	}
	
	public String editarPersona()
	{
		pDao.modificarPersona(adminLogin);
		return "editarAdmin";
	}
	
	public SelectItem[] getGenderValues()
	{
		SelectItem[] items = new SelectItem[Sexo.values().length];
	    int i = 0;
	    for(Sexo g: Sexo.values())
	    {
	      items[i++] = new SelectItem(g, g.name());
	    }
	    return items;
	 }

	public List<Persona> getBackupUsuarios() {
		return backupUsuarios;
	}

	public void setBackupUsuarios(List<Persona> backupUsuarios) {
		this.backupUsuarios = backupUsuarios;
	}

	public List<Persona> getListaUsuarios()
	{
		this.backupUsuarios =  pDao.recuperarUsuarios();
		if(backupUsuarios != null)
		{
			this.listaUsuarios.clear();
			this.listaUsuarios.addAll(backupUsuarios);
			if(!flagOrden)
				ordenar();
		}
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Persona> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	private void ordenar()
	{
		switch(this.ordenActual)
		{
		case Alfabeticamente:
			Collections.sort(this.listaUsuarios, Persona.Comparators.NAME);
			break;
		case Antiguedad:
			Collections.sort(this.listaUsuarios, Persona.Comparators.FECHA);
			break;
		case Cantidad_De_Rutas:
			Collections.sort(this.listaUsuarios, Persona.Comparators.CANTRUTAS);
			break;
		default:
			break;
		}
		if(criterioActual == criterio.Descendente)
			this.listaUsuarios = Lists.reverse(listaUsuarios);
	}
	
	public orden getOrdenActual() {
		return ordenActual;
	}

	public void setOrdenActual(orden ordenActual) 
	{
		this.ordenActual = ordenActual;
	}

	public criterio getCriterioActual() 
	{
		return criterioActual;
	}

	public void setCriterioActual(criterio criterioActual) 
	{
		this.criterioActual = criterioActual;
	}
	
	public int obtenerCantRutas(Persona o)
	{
		RutaDAO rDao = new RutaDAO();
		List<Ruta> lista = rDao.recuperarRutasUsuario(o.getId());
		if(lista != null)
			return lista.size();
    	return 0;
	}
	
	public String setOrden(int num)
	{
		if(num == 0)
			this.ordenActual = orden.Alfabeticamente;
		else
		{
			if(num==1)
				this.ordenActual = orden.Antiguedad;
			else
				this.ordenActual = orden.Cantidad_De_Rutas;
		}
		this.flagOrden = false;
		return null;
	}
	
	public String setCriterio(int num)
	{
		if(num == 0)
		{
			this.criterioActual = criterio.Ascendente;
		}
		else
		{
			this.criterioActual = criterio.Descendente;
		}
		this.flagOrden = false;
		return null;
	}
	
	public String getCriterioStyle(int id)
	{
		if(id==0)	// Si esta en orden Descendente
		{
			if(criterioActual == criterio.Descendente)
				return "btn btn-info";
			else
				return "btn btn-primary";
		}
		else	// Si esta en orden Ascendente
		{
			if(criterioActual == criterio.Ascendente)
				return "btn btn-info";
			else
				return "btn btn-primary";
		}
	}
	
	public String getOrdenStyle(int opcion)
	{
		switch(opcion)
		{
		case 1:
			if(this.ordenActual == orden.Alfabeticamente)
				return "btn btn-info";
			break;
		case 2:
			if(this.ordenActual == orden.Antiguedad)
				return "btn btn-info";
			break;
		case 3:
			if(this.ordenActual == orden.Cantidad_De_Rutas)
				return "btn btn-info";
			break;
		}
		return "btn btn-primary";
	}
	
}
