package controller;

import java.util.List;

import javax.faces.model.SelectItem;

import modelo.Actividad;
import modeloDAO.ActividadDAO;

public class ActividadBean 
{
	private ActividadDAO actDao = new ActividadDAO();
	private Actividad act = new Actividad();
	private List<Actividad> listaActividades;
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

	public List<Actividad> getListaActividades() {
		this.listaActividades = actDao.recuperarActividades();
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
		actDao.modificarActividad(actSeleccionada);
		return "admin_opOk";
	}

	public Actividad getActSeleccionada() {
		return actSeleccionada;
	}

	public void setActSeleccionada(Actividad actSelec) {
		this.actSeleccionada = actSelec;
	}
	
//	public String eliminarActividad(Actividad Selec)
//	{
//		this.actDao.eliminarActividad(Selec);
//		return "admin_opOk";
//	}
	
	public String eliminarSelec()
	{
		this.actDao.eliminarActividad(this.actSeleccionada);
		return "admin_opOk";
	}
	
	public SelectItem[] getActividadValues()
	{
		SelectItem[] items = new SelectItem[listaActividades.size()];
	    int i = 0;
	    for(i=0;i<listaActividades.size();i++)
	    {
	      items[i] = new SelectItem(listaActividades.get(i).getId(), listaActividades.get(i).getNombre());
	    }
	    return items;
	}
	
	
}
