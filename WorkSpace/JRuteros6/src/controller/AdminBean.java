package controller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.Persona;
import modeloDAO.PersonaDAO;

public class AdminBean 
{
	private PersonaDAO pDao = new PersonaDAO();
	private Persona adminLogin;
	private List<Persona> listaUsuarios = pDao.recuperarUsuarios();

	public AdminBean(){}
	
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
	
	public String actualizar()
	{
		if(this.adminLogin == null)
		{	
			HttpSession session;
			FacesContext context = FacesContext.getCurrentInstance();
		    session = (HttpSession) context.getExternalContext().getSession(true);
		       
			Long id = (Long) session.getAttribute("usrId");
			if(id != null)
			{
				this.adminLogin = pDao.recuperarPersona(id);
			}
		}
		return "adminPerfil";
	}
	
	
}