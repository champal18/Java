package controller;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.myfaces.custom.fileupload.UploadedFile;

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

public class RutaBean {


	private RutaDAO rDao = new RutaDAO();
	private Ruta ruta = new Ruta();
	private Ruta rutaSeleccionada = new Ruta();
	private long idActividad;
	
	private boolean control = true;

	public RutaBean(){}
	
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
		ruta.setRegistroRealizadas(null);
		ruta.setActividad(actividad);
		
		rDao.guardarRuta(ruta);
		
		
		if(file != null) {
	        
            Foto f = new Foto();
            f.setRuta(ruta);
            try {
				f.setImg(this.file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            FotoDAO fDao = new FotoDAO();
            fDao.guardarFoto(f);
        }
		
		PuntoDao puntoDao = PuntoDao.instance;
		puntoDao.guardarPuntos(this.ruta);
		
		puntoDao.limpiarMapa();
		
		this.control= false;
		return "usuario_opOk";
	}
	
	public String selecEditar(Ruta selec)
	{
		this.rutaSeleccionada = selec;
		
		this.idActividad = selec.getActividad().getId();
		
		PuntoDao pDao = PuntoDao.instance;
		pDao.recuperarPuntosRuta(selec.getId());
		return "editar_ruta";
	}
	
	public void selecEliminar(Ruta selec)
	{
		this.rutaSeleccionada = selec;
	}
	
	public String selecMostrar(Ruta selec)  // VER -- Se podria juntar con selecEliminar y hacer una sola funcion
	{
		this.rutaSeleccionada = selec;
		return "mostrar_ruta";
	}
	
	public Ruta getRutaSeleccionada() {
		return rutaSeleccionada;
	}

	public void setRutaSeleccionada(Ruta rutaSeleccionada) {
		this.rutaSeleccionada = rutaSeleccionada;
	}

	
	public String editarRuta()
	
	{	Actividad actividad=new Actividad();
		ActividadDAO aDAO= new ActividadDAO();
		
		actividad=aDAO.recuperarActividad(idActividad);
		
		rutaSeleccionada.setActividad(actividad);
		
		rDao.modificarRuta(rutaSeleccionada);
		
		PuntoDao puntoDao = PuntoDao.instance;
		puntoDao.eliminarPuntosRuta(rutaSeleccionada.getId());
		
		
		puntoDao.guardarPuntos(this.ruta);
		
		puntoDao.limpiarMapa();
		
		return "usuario_opOk";
	}
	
	public String eliminarRutaSeleccionada()
	{
		rDao.eliminarRuta(rutaSeleccionada);
		return "usuario_opOk";
	}
	
	
	
//	public void eliminarRuta(Ruta ruta)	// --> parametro enviado desde el xhtml
//	{
//		rDao.eliminarRuta(ruta);
//	}
	
	
	
	public SelectItem[] getPrivacidadValues()
	{
		SelectItem[] items = new SelectItem[Privacidad.values().length];
	    int i = 0;
	    for(Privacidad p: Privacidad.values())
	    {
	      items[i++] = new SelectItem(p, p.name());
	    }
	    return items;
	  }
	
	public SelectItem[] getFormatoValues()
	{
		SelectItem[] items = new SelectItem[Formato.values().length];
	    int i = 0;
	    for(Formato f: Formato.values())
	    {
	      items[i++] = new SelectItem(f, f.name());
	    }
	    return items;
	}
	
	public SelectItem[] getDificultadValues()
	{
		SelectItem[] items = new SelectItem[Dificultad.values().length];
	    int i = 0;
	    for(Dificultad d: Dificultad.values())
	    {
	      items[i++] = new SelectItem(d, d.name());
	    }
	    return items;
	}

	public long getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(long idActividad) {
		this.idActividad = idActividad;
	}
	
	// Prueba
	
    private UploadedFile file;
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
        
            Foto f = new Foto();
            f.setRuta(null);
            try {
				f.setImg(this.file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            FotoDAO fDao = new FotoDAO();
            fDao.guardarFoto(f);
        }
    }
	
}
