package controller;

import java.util.List;
import java.util.UUID;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import modelo.Persona;
import modelo.Sexo;
import modelo.Tipo_USER;
import modeloDAO.PersonaDAO;

public class PersonaBean 
{
	private PersonaDAO pDao = new PersonaDAO();
	private Persona usr = new Persona();
	private Persona usrLogin;
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

	public PersonaBean()
	{
		
	}
	
	
	public String altaPersona()
	{
		String pass = UUID.randomUUID().toString().substring(0, 6);
		usr.setPass(pass);
		usr.setHabilitado(true);
		pDao.guardarPersona(usr);
		return "exito";
	}
	
	public String editarPersona()
	{
		pDao.modificarPersona(usrLogin);
		return "editarExitoso";
	}
	
	public List<Persona> recuperarUsuarios()
	{
		this.listaUsuarios =  pDao.recuperarUsuarios();
		return listaUsuarios;
	}
	
	public String habilitarPersona(Persona selec)	// --> parametro enviado desde el xhtml
	{
		selec.setHabilitado(!selec.getHabilitado());
		pDao.modificarPersona(selec);
		return null;
	}

	public Persona getUsrLogin() {
		return usrLogin;
	}

	public void setUsrLogin(Persona usrLogin) {
		this.usrLogin = usrLogin;
	}
	
	public String setUsrLogin()
	{
		HttpSession session;
		FacesContext context = FacesContext.getCurrentInstance();
	    session = (HttpSession) context.getExternalContext().getSession(true);
	    
		    
		Long id = (Long) session.getAttribute("usrId");
		if(id != null)
		{
			this.usrLogin = pDao.recuperarPersona(id);
			System.out.println(this.usrLogin.getApellido());
		}
		return "login";
		
	}
	
	public String cambiarPass()
	{
		String pass = UUID.randomUUID().toString().substring(0, 6);
		usrLogin.setPass(pass);
		pDao.modificarPersona(usrLogin);
		return "cambioPass";
	}
	
	// Prueba para usar enumerativo en JSF
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
		
		public String actualizar()
		{
			if(this.usrLogin == null)
			{	
				HttpSession session;
				FacesContext context = FacesContext.getCurrentInstance();
			    session = (HttpSession) context.getExternalContext().getSession(true);
			       
				Long id = (Long) session.getAttribute("usrId");
				if(id != null)
				{
					this.usrLogin = pDao.recuperarPersona(id);
				}
			}
			return "usuarioPerfil";
		}
}