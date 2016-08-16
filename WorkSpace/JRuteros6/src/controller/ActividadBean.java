package controller;

import java.util.List;
import modelo.Actividad;
import modeloDAO.ActividadDAO;

public class ActividadBean 
{
	private ActividadDAO actDao = new ActividadDAO();
	private Actividad act = new Actividad();
	private List<Actividad> listaActividades;
	private Actividad actEditada = new Actividad();
	
	public ActividadBean(){}
	
	public String altaActividad()
	{
		act.setHabilitada(true);
		actDao.guardarActividad(act);
		return "admin_opOk";
	}
	
	public ActividadDAO getActDao() {
		return actDao;
	}

	public void setActDao(ActividadDAO actDao) {
		this.actDao = actDao;
	}

	public Actividad getAct() {
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
	
	public String selecActividad(Actividad selec)
	{
		this.actEditada = selec;
		return "editar_actividad";
	}
	
	public String editarActividad()
	{
		actDao.modificarActividad(actEditada);
		return "admin_opOk";
	}

	public Actividad getActEditada() {
		return actEditada;
	}

	public void setActEditada(Actividad actEditada) {
		this.actEditada = actEditada;
	}
	
}
