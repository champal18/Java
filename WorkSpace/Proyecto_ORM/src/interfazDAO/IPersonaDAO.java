package interfazDAO;

import modelo.Persona;

public interface IPersonaDAO 
{
	public void guardarPersona(Persona p);
	public void modificarPersona(Persona p);
	public void eliminarPersona(Persona p);
	public Persona recuperarPersona(long id);
}
