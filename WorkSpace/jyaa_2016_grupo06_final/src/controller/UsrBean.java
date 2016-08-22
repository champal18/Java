package controller;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import modelo.Persona;
import modelo.Ruta;
import modelo.Sexo;
import modeloDAO.PersonaDAO;
import modeloDAO.RutaDAO;

public class UsrBean 
{
	
	
	private Persona usrLogin;
	private PersonaDAO pDao = new PersonaDAO();
	private RutaDAO rDao=new RutaDAO();
	private List<Ruta> listaRutas; 
	
	public List<Ruta> getListaRutas()
	{
		this.listaRutas =  rDao.recuperarRutasUsuario(this.getUsrLogin().getId());
		return listaRutas;
	}

	public void setListaRutas(List<Ruta> listaRutas) {
		this.listaRutas = listaRutas;
	}
	
	
	
	public String editarPersona()
	{
		pDao.modificarPersona(usrLogin);
		return "editarUsr";
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
	
	public Persona getUsrLogin()
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
