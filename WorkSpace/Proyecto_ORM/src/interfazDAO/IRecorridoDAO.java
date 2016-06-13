package interfazDAO;

import modelo.Recorrido;

public interface IRecorridoDAO 
{
	public void guardarRecorrido(Recorrido rec);
	public void modificarRecorrido(Recorrido rec);
	public void eliminarRecorrido(Recorrido rec);
	public Recorrido recuperarRecorrido(long id);
}
