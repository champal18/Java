package interfazDAO;

import java.util.List;
import modelo.Foto;

public interface IFotoDAO 
{
	public void guardarFoto(Foto f);
	public void modificarFoto(Foto f);
	public void eliminarFoto(Foto f);
	public List<Foto> recuperarFotos(long idRuta);
}