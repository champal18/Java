package interfazDAO;

import modelo.Ruta;
import rest.Punto;

public interface IPuntoDAO 
{
	public void guardarPuntos(Ruta ruta);
	public void modificarPunto(Punto p);
	public void eliminarPunto(Punto p);
	public Punto recuperarPunto(long id);

}
