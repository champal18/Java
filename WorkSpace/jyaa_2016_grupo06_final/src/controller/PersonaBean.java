package controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import javax.faces.model.SelectItem;
import javax.mail.*;

import modelo.Emailer;
import modelo.Persona;
import modelo.Sexo;
import modelo.Tipo_USER;
import modeloDAO.PersonaDAO;

public class PersonaBean 
{
	private PersonaDAO pDao = new PersonaDAO();
	private Persona usr = new Persona();
	boolean control = false;
	private String msj = new String();
	
	public PersonaBean(){}
	
	public Persona getUsr()
	{
		if(!control)
		{
			usr = new Persona();
			control = true;
		}
		return usr;
	}

	public void setUsr(Persona usr) {
		this.usr = usr;
	}
	
	public String altaPersona()
	{
		this.msj = new String();
		if(pDao.recuperarPersona(this.usr.getNombreUser()) != null)
		{
			this.msj = "Ya existe usuario con este nombre";
			return null;
		}
		else
		{
			String pass = UUID.randomUUID().toString().substring(0, 6);
			usr.setPass(pass);
			usr.setHabilitado(true);
			String[] emailList = {usr.getMail()};
			Emailer smtpMailSender = new Emailer();
			try {
				smtpMailSender.postMail( emailList, pass,usr);
			} catch (AuthenticationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Ingreso la fecha de registro
			LocalDateTime now = LocalDateTime.now();
			usr.setFechaRegistro(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
			
			pDao.guardarPersona(usr);
			control = false;
			return "exito";
		}
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

	public String getMsj() {
		return msj;
	}

	public void setMsj(String msj) {
		this.msj = msj;
	}
	
}
