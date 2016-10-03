package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import controller.RutaBean.orden;
import modelo.Ruta;
import modelo.Actividad;
import modelo.Dificultad;
import modelo.Formato;
import modelo.Foto;
import modelo.Persona;
import modelo.Privacidad;
import modeloDAO.ActividadDAO;
import modeloDAO.FotoDAO;
import modeloDAO.PersonaDAO;
import modeloDAO.PuntoDao;
import modeloDAO.RutaDAO;
import modeloDAO.RutaRealizadaDAO;

public class RutaRealizadaBean {
	
	private RutaRealizadaDAO rDao = new RutaRealizadaDAO();
	private Ruta ruta = new Ruta();
	
	private boolean control = true;	// Una vez creada una ruta nueva, se vuelve a llamar al constructor
	private boolean buscar = false; // Flag que indica que se inicio una busqueda para no renovar la lista de rutas
	
	private Ruta rutaSeleccionada = new Ruta();
	private long idActividad;
	

	public RutaRealizadaBean()
	{
	
	}
	
	public Ruta getRuta() {
		if(!control)
		{
			this.ruta= new Ruta();
			this.control = true;
		}
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}
	
	public String altaRuta()
	{
		PersonaDAO pDAO= new PersonaDAO();
		Persona persona= new Persona();
		ActividadDAO aDAO= new ActividadDAO();
		Actividad actividad=new Actividad();
		
		HttpSession session;
		FacesContext context = FacesContext.getCurrentInstance();
	    session = (HttpSession) context.getExternalContext().getSession(true);
	       
		Long id = (Long) session.getAttribute("usrId");
		
		persona=pDAO.recuperarPersona(id);
		actividad=aDAO.recuperarActividad(idActividad);
		
		ruta.setPromedio(0);
		ruta.setCantRealizadas(0);
		ruta.setOwner(persona);
		ruta.setActividad(actividad);
		
		rDao.guardarRuta(ruta);
		
		// Subo la imagen a la BD con la ruta asociada
    	for(int i=0;i<pos;i++)
    	{
    		if(files[i] != null)
    		{
    			Foto f = new Foto();
    			f.setRuta(ruta);
    			try 
    			{
    				f.setImg(files[i].getBytes());
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			FotoDAO fDao = new FotoDAO();
    			fDao.guardarFoto(f);
    		}
    	}
    	
		// Guardo los puntos correspondientes a la ruta
		PuntoDao puntoDao = PuntoDao.instance;
		puntoDao.guardarPuntos(this.ruta);
		
		// Limpio los puntos del mapa
		puntoDao.limpiarMapa();
		
		// Limpio el vector de archivos
		this.pos = 0;
		this.files = new UploadedFile[5];
		
		this.control= false;
		return "usuario_opOk";
	}
	
}
