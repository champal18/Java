package controller;

import java.util.UUID;

import javax.faces.model.SelectItem;
import modelo.Persona;
import modelo.Sexo;
import modelo.Tipo_USER;
import modeloDAO.PersonaDAO;

public class PersonaBean 
{
	
	
	private PersonaDAO pDao = new PersonaDAO();
	private Persona usr = new Persona();
	boolean control = false;

	
	public PersonaBean(){}
	
	public Persona getUsr()
	{
		if(control)
		{
			usr = new Persona();
			control = false;
		}
		return usr;
	}

	public void setUsr(Persona usr) {
		this.usr = usr;
	}
	
	public String altaPersona()
	{
		String pass = UUID.randomUUID().toString().substring(0, 6);
		usr.setPass(pass);
		usr.setHabilitado(true);
		pDao.guardarPersona(usr);
		control = true;
		return "exito";
	}
	
	public SelectItem[] getGenderValues()
	{
		SelectItem[] items = new SelectItem[Sexo.values().length];
	    int i = 0;
	    for(Sexo g: Sexo.values())
	    {
	      items[i++] = new SelectItem(g, g.name());
	    }
	    return items;
	  }
	
	public SelectItem[] getTipoValues()
	{
		SelectItem[] items = new SelectItem[Tipo_USER.values().length];
	    int i = 0;
	    for(Tipo_USER g: Tipo_USER.values())
	    {
	      items[i++] = new SelectItem(g, g.name());
	    }
	    return items;
	}
	
}
