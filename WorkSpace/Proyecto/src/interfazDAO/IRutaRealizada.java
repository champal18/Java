package interfazDAO;

import modelo.RutaRealizada;;

public interface IRutaRealizada
{
	public void guardarRuta(RutaRealizada rutaR);
	public void modificarRuta(RutaRealizada rutaR);
	public void eliminarRuta(RutaRealizada rutaR);
	public RutaRealizada recuperarRutaRealizada(long id);
}
