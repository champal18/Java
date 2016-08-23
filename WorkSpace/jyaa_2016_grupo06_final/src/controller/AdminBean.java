package controller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import modelo.Persona;
import modelo.Sexo;
import modeloDAO.PersonaDAO;

public class AdminBean 
{
	private PersonaDAO pDao = new PersonaDAO();
	private Persona adminLogin;
	private List<Persona> listaUsuarios; 

	public List<Persona> getListaUsuarios()
	{
		this.listaUsuarios =  pDao.recuperarUsuarios();
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Persona> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public AdminBean(){}
	
	public String habilitarPersona(Persona selec)	// --> parametro enviado desde el xhtml
	{
		selec.setHabilitado(!selec.getHabilitado());
		pDao.modificarPersona(selec);
		return null;
	}
	
	public Persona getAdminLogin()
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
		return adminLogin;
	}

	public void setAdminLogin(Persona adminLogin) {
		this.adminLogin = adminLogin;
	}
	
	public String editarPersona()
	{
		pDao.modificarPersona(adminLogin);
		return "editarAdmin";
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
	
}