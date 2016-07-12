package interfazDAO;

import modelo.Ruta;

public interface IRutaDAO 
{
	public void guardarRuta(Ruta ruta);
	public void modificarRuta(Ruta ruta);
	public void eliminarRuta(Ruta ruta);
	public Ruta recuperarRuta(long id);
}
