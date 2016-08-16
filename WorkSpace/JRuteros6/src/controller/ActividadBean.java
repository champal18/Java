package controller;

import java.util.List;
import modelo.Actividad;
import modeloDAO.ActividadDAO;

public class ActividadBean 
{
	private ActividadDAO actDao = new ActividadDAO();
	private Actividad act = new Actividad();
	private List<Actividad> listaActividades = actDao.recuperarActividades();
	
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
		return listaActividades;
	}

	public void setListaActividades(List<Actividad> listaActividades) {
		this.listaActividades = listaActividades;
	}
	
	public String habilitarPersona(Actividad selec)	// --> parametro enviado desde el xhtml
	{
		selec.setHabilitada(!selec.getHabilitada());
		actDao.modificarActividad(selec);
		return null;
	}
	
}
