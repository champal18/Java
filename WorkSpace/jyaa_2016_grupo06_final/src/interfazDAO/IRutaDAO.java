package interfazDAO;

import java.util.List;

import modelo.Ruta;

public interface IRutaDAO 
{
	public void guardarRuta(Ruta ruta);
	public void modificarRuta(Ruta ruta);
	public void eliminarRuta(Ruta ruta);
	public Ruta recuperarRuta(long id);
	public List<Ruta> recuperarRutasUsuario(long id);
}
