package controller;

import java.util.List;
import java.util.UUID;
import modelo.Persona;
import modeloDAO.PersonaDAO;

//@ManagedBean(name="PersonaBean")
//@ApplicationScoped
public class PersonaBean 
{
	private PersonaDAO pDao = new PersonaDAO();
	private Persona usr = new Persona();
	private List<Persona> listaUsuarios = pDao.recuperarUsuarios();
	
	public List<Persona> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Persona> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Persona getUsr() {
		return usr;
	}

	public void setUsr(Persona usr) {
		this.usr = usr;
	}

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
	
	public List<Persona> recuperarUsuarios()
	{
		this.listaUsuarios =  pDao.recuperarUsuarios();
		return listaUsuarios;
	}
	
	

}