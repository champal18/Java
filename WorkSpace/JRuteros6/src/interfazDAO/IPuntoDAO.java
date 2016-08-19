package interfazDAO;

import rest.Punto;

public interface IPuntoDAO 
{
	public void guardarPuntos();
	public void modificarPunto(Punto p);
	public void eliminarPunto(Punto p);
	public Punto recuperarPunto(long id);

}
