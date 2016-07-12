package controller;

import javax.faces.context.FacesContext;
import java.util.UUID;
import modelo.Persona;
import modeloDAO.PersonaDAO;

public class PersonaBean 
{
	private PersonaDAO pDao = new PersonaDAO();
	private Persona usr = new Persona();
	
	public PersonaBean(){}
	
	public String altaPersona()
	{
		String pass = UUID.randomUUID().toString().substring(0, 6);
		usr.setPass(pass);
		pDao.guardarPersona(usr);
		return "exito";
	}
	
	public void modificarPersona()
	{
		pDao.modificarPersona(usr);
	}
	
	

}
