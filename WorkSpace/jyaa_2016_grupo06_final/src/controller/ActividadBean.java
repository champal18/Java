package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modelo.Actividad;
import modelo.Ruta;
import modeloDAO.ActividadDAO;
import modeloDAO.RutaDAO;

public class ActividadBean 
{
	private ActividadDAO actDao = new ActividadDAO();
	private Actividad act = new Actividad();
	private List<Actividad> listaActividades = actDao.recuperarActividades();
	private Actividad actSeleccionada = new Actividad();
	private boolean control = false;
	
	public ActividadBean(){}
	
	public String altaActividad()
	{
		this.control = false;
		act.setHabilitada(true);
		actDao.guardarActividad(act);
		return "admin_opOk";
	}
	
	public ActividadDAO getActDao() 
	{
		return actDao;
	}

	public void setActDao(ActividadDAO actDao) {
		this.actDao = actDao;
	}

	public Actividad getAct() 
	{
		if(!control)
		{
			this.control = true;
			this.act = new Actividad();
		}
		return act;
	}

	public void setAct(Actividad act) {
		this.act = act;
	}
	
	public List<Actividad> getListaActividades() 
	{
		this.listaActividades = actDao.recuperarActividades();
		if(listaActividades == null)
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No existe registro de actividades creadas!"));
		return listaActividades;
	}

	public void setListaActividades(List<Actividad> listaActividades) {
		this.listaActividades = listaActividades;
	}
	
	public String habilitarActividad(Actividad selec)	// --> parametro enviado desde el xhtml
	{
		selec.setHabilitada(!selec.getHabilitada());
		actDao.modificarActividad(selec);
		return null;
	}
	
	public Actividad getActSeleccionada() {
		return actSeleccionada;
	}

	public void setActSeleccionada(Actividad actSeleccionada) {
		this.actSeleccionada = actSeleccionada;
	}
	
	public String selecEditar(Actividad selec)
	{
		this.actSeleccionada = selec;
		return "editar_actividad";
	}
	
	public void selecEliminar(Actividad selec)
	{
		this.actSeleccionada = selec;
	}
	
	public String editarActividad()
	{
		for(Actividad actLista : listaActividades)
		{
			if(this.act.getNombre().toLowerCase().equals(actLista.getNombre().toLowerCase()))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ya existe una actividad con este nombre!"));
				return null;
			}
		}
		this.actSeleccionada.setNombre(this.act.getNombre());
		actDao.modificarActividad(actSeleccionada);
		this.act = new Actividad();
		return "admin_opOk";
	}

	
	public String eliminarSelec()
	{
		RutaDAO rDao = new RutaDAO();
		if(rDao.recuperarRutasActividad(this.actSeleccionada.getId()) == null)
		{
			this.actDao.eliminarActividad(this.actSeleccionada);
			return "admin_opOk";
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Existen rutas asociadas a esta actividad!"));
			return null;	// Redireccionar desde faceConfig a una pagina de error!
		}
		
	}
	
	public SelectItem[] getActividadValues()
	{
		if(listaActividades != null)
		{
			SelectItem[] items = new SelectItem[listaActividades.size()];
		    int i = 0;
		    for(i=0;i<listaActividades.size();i++)
		    {
		      items[i] = new SelectItem(listaActividades.get(i).getId(), listaActividades.get(i).getNombre());
		    }
		    return items;
		}
		else 
			return new SelectItem[0];
	}
	
	public SelectItem[] getActividadHabilitadasValues()
	{
		List<Actividad> actDisponibles = actDao.recuperarActividades();
		
		if(actDisponibles != null)
		{
			for (Iterator<Actividad> iterator = actDisponibles.iterator(); iterator.hasNext();) 
	    	{
			    Actividad a = iterator.next();
			    if (!a.getHabilitada()) 
			    {
			        // Remove the current element from the iterator and the list.
			        iterator.remove();
			    }
	    	}
	
			SelectItem[] items = new SelectItem[actDisponibles.size()];
		    int i = 0;
		    for(i=0;i<actDisponibles.size();i++)
		    {
		      items[i] = new SelectItem(actDisponibles.get(i).getId(), actDisponibles.get(i).getNombre());
		    }
		    return items;
		}
		else
			return new SelectItem[0];
	}
	
}
