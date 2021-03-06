package interfazDAO;

import modelo.Actividad;

public interface IActividadDAO 
{
	public void guardarActividad(Actividad act);
	public void modificarActividad(Actividad act);
	public void eliminarActividad(Actividad act);
	public Actividad recuperarActividad(long id);
}
