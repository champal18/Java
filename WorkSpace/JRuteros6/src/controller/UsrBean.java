package controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import modelo.Persona;
import modeloDAO.PersonaDAO;

public class UsrBean 
{
	private Persona usrLogin;
	private PersonaDAO pDao = new PersonaDAO();
	
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
		return "usrPerfil";
	}
	
	public String editarPersona()
	{
		pDao.modificarPersona(usrLogin);
		return "editarUsr";
	}
	
	public Persona getUsrLogin() {
		return usrLogin;
	}

	public void setUsrLogin(Persona usrLogin) {
		this.usrLogin = usrLogin;
	}

	public PersonaDAO getpDao() {
		return pDao;
	}

	public void setpDao(PersonaDAO pDao) {
		this.pDao = pDao;
	}

}