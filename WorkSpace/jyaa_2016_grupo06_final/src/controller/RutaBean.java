package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import rest.Punto;
import rest.puntoBuscado;

public class RutaBean
{

	private RutaDAO rDao = new RutaDAO();
	private Ruta ruta = new Ruta();
	
	private boolean control = true;	// Una vez creada una ruta nueva, se vuelve a llamar al constructor
	private boolean buscar = false; // Flag que indica que se inicio una busqueda para no renovar la lista de rutas
	
	private Ruta rutaSeleccionada = new Ruta();
	private long idActividad;
	
	// Upload de imagen para la BD
	private UploadedFile file;
	private UploadedFile[] files = new UploadedFile[5];
	private int pos = 0;
	
	// Listado de rutas de la BD
	private List<Ruta> allRutas = rDao.recuperarAllRutasPublicas();
	private List<Ruta> backUpRutas = rDao.recuperarAllRutasPublicas();
	
	// Enumerativo para definir orden actual de la lista
	private enum orden {normal,distancia,dificultad,puntuacion,cantRealizaciones};
	private orden ordenActual = orden.normal;
	
	// Filtrado de Rutas
	private Actividad filtroActividad;
	private long filtroDistancia;
	private Dificultad filtroDificultad;
	private long filtroFormato;
//	private Punto puntoFiltro;
	private boolean filtroRadio;

	public RutaBean()
	{
		marcadorDistancia[0] = "font-weight:bold; color:black";
		marcadorDificultad[0] = "font-weight:bold; color:black";
		marcadorFormato[0] = "font-weight:bold; color:black";
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
		
		// Ingreso la fecha de registro en la BD
		LocalDateTime now = LocalDateTime.now();
		ruta.setFechaRegistro(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
		
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
		
		PuntoDao pDao = PuntoDao.instance;
		pDao.recuperarPuntosRuta(selec.getId());
		
		// Guardo el id de la ruta seleccionada en la sesion
		HttpSession session;
		FacesContext context = FacesContext.getCurrentInstance();
	    session = (HttpSession) context.getExternalContext().getSession(true);
	    session.setAttribute("idRuta", selec.getId());
	    
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
	
	// Manejo de carga de las imagenes
	
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file)
    {
    	if(file != null)
    	{
    		this.files[pos] = file;
    		this.pos++;
    	}
    }

	// Lista de todas las rutas
	public List<Ruta> getAllRutas()
	{
		PuntoDao.instance.limpiarMapa();
		if(!buscar)
		{
			this.backUpRutas = rDao.recuperarAllRutasPublicas();	// Obtengo las rutas publicas de la BD
			this.allRutas.clear();
			this.allRutas.addAll(backUpRutas);				// Comienzo con la lista sin ordenar ni filtrar
			if(filtroRadio)
			{
				busquedaRadial();
				filtroRadio = false;
			}
			// Primero filtro
			filtrar();
			// Segundo ordeno
			ordenar();
		}
		buscar = false;
		this.cadenaBuscada = new String();
		return allRutas;
	}

	public void setAllRutas(List<Ruta> allRutas) {
		this.allRutas = allRutas;
	}
	
	// Funciones de FILTRADO y ORDENAMIENTO de la lista de rutas
	
	public String orden(int opcion)
	{
		switch(opcion)
		{
		case 1:
			this.ordenActual = orden.distancia;
			break;
		case 2:
			this.ordenActual = orden.dificultad;
			break;
		case 3:
			this.ordenActual = orden.puntuacion;
			break;
		case 4:
			this.ordenActual = orden.cantRealizaciones;
			break;
		}
		
		return null;
	}
	
	private void ordenar()
	{
		switch(this.ordenActual)
		{
		case normal:
			break;
		case cantRealizaciones:
			Collections.sort(this.allRutas, Ruta.Comparators.CANTREALIZACIONES);
			break;
		case dificultad:
			Collections.sort(this.allRutas, Ruta.Comparators.DIFICULTAD);
			break;
		case distancia:
			Collections.sort(this.allRutas, Ruta.Comparators.DISTANCIA);
			break;
		case puntuacion:
			Collections.sort(this.allRutas, Ruta.Comparators.PUNTUACION);
			break;
		default:
			break;
		}
	}
	
	public void filtrar()
	{
		filtroActividad();
		filtroDistancia();
		filtroDificultad();
		filtroFormato();
	}
	
	// FILTRO ACTIVIDAD
	
	private void filtroActividad()
	{	
		if(filtroActividad != null)
		{
			for (Iterator<Ruta> iterator = this.allRutas.iterator(); iterator.hasNext();) {
			    Ruta ruta = iterator.next();
			    if (ruta.getActividad().getId() != filtroActividad.getId()) {
			        // Remove the current element from the iterator and the list.
			        iterator.remove();
			    }
			}
		}
	}

	public Actividad getFiltroActividad() {
		return filtroActividad;
	}

	public void setFiltroActividad(Actividad filtro)
	{
		this.filtroActividad = filtro;
	}
	
//	private String[] marcadorActividad = new String[];
	
	// FILTRO DISTANCIA
	
	private void filtroDistancia()
	{
		switch((int)filtroDistancia)
		{
		case 0:
			break;
		case 1:
			compararDistancias(0, 10);
			break;
		case 2:
			compararDistancias(10, 25);
			break;
		case 3:
			compararDistancias(25, 50);
			break;
		case 4:
			compararDistancias(50, 100);
			break;
		case 5:
			compararDistancias(100, 100);
			break;
		}
	}

	public long getFiltroDistancia() {
		return filtroDistancia;
	}

	public void setFiltroDistancia(long filtroDistancia)
	{
		this.filtroDistancia = filtroDistancia;
		cleanMarcadorDistancia();
		marcadorDistancia[(int) filtroDistancia] = "font-weight:bold; color:black";
	}
	
	private void compararDistancias(int d1, int d2)
	{
		for (Iterator<Ruta> iterator = this.allRutas.iterator(); iterator.hasNext();) 
    	{
		    Ruta ruta = iterator.next();
		    if(d1 != d2)
		    {    
		    	if ((ruta.getDistancia() < d1) || (ruta.getDistancia() > d2))
			    {
			        // Remove the current element from the iterator and the list.
			        iterator.remove();
			    }
		    }
		    else
		    {
		    	if (ruta.getDistancia() < d1)
			    {
			        // Remove the current element from the iterator and the list.
			        iterator.remove();
			    }
		    }
	 	}
	}
	
	private String[] marcadorDistancia = new String[6];
	
	public String[] getMarcadorDistancia() {
		return marcadorDistancia;
	}

	public void setMarcadorDistancia(String[] marcadorDistancia) {
		this.marcadorDistancia = marcadorDistancia;
	}
	
	private void cleanMarcadorDistancia()
	{
		for(int i=0;i<6;i++)
		{
			marcadorDistancia[i] = new String();
		}
	}
	
	// FILTRO DIFICULTAD
	
	private void filtroDificultad()
	{
		if(this.filtroDificultad != null)
		{
			switch(this.filtroDificultad)
			{
			case Facil:
				compararDificultad(Dificultad.Facil);
				break;
			case Moderado:
				compararDificultad(Dificultad.Moderado);
				break;
			case Dificil:
				compararDificultad(Dificultad.Dificil);
				break;
			case MuyDificil:
				compararDificultad(Dificultad.MuyDificil);
				break;
			case SoloExpertos:
				compararDificultad(Dificultad.SoloExpertos);
				break;
			}
		}
	}

	public Dificultad getFiltroDificultad() {
		return filtroDificultad;
	}
	
	public void setFiltroDificultad(int filtroDificultad)
	{
		cleanMarcadorDificultad();
		marcadorDificultad[filtroDificultad] = "font-weight:bold; color:black";
		switch((int)filtroDificultad)
		{
		case 0:
			this.filtroDificultad = null;
			break;
		case 1:
			this.filtroDificultad = Dificultad.Facil;
			break;
		case 2:
			this.filtroDificultad = Dificultad.Moderado;
			break;
		case 3:
			this.filtroDificultad = Dificultad.Dificil;
			break;
		case 4:
			this.filtroDificultad = Dificultad.MuyDificil;
			break;
		case 5:
			this.filtroDificultad = Dificultad.SoloExpertos;
			break;
		}
	}
	
	private void compararDificultad(Dificultad dif)
	{
		for (Iterator<Ruta> iterator = this.allRutas.iterator(); iterator.hasNext();) 
    	{
		    Ruta ruta = iterator.next();
		    if (ruta.getDificultad() != dif) 
		    {
		        // Remove the current element from the iterator and the list.
		        iterator.remove();
		    }
    	}
	}
		
	private String[] marcadorDificultad = new String[6];

	public String[] getMarcadorDificultad() {
		return marcadorDificultad;
	}

	public void setMarcadorDificultad(String[] marcadorDificultad) {
		this.marcadorDificultad = marcadorDificultad;
	}

	private void cleanMarcadorDificultad()
	{
		for(int i=0;i<6;i++)
		{
			marcadorDificultad[i] = new String();
		}
	}

	// FILTRO FORMATO
	
	private void filtroFormato()
	{
		switch((int)filtroFormato)
	    {
		case 0:
			break;
	    case 1:
	    	for (Iterator<Ruta> iterator = this.allRutas.iterator(); iterator.hasNext();) 
	    	{
			    Ruta ruta = iterator.next();
			    if (ruta.getFormato()!=Formato.SoloIda) 
			    {
			        // Remove the current element from the iterator and the list.
			        iterator.remove();
			    }
	    	}
	    	break;
	    case 2:
	    	for (Iterator<Ruta> iterator = this.allRutas.iterator(); iterator.hasNext();) 
	    	{
			    Ruta ruta = iterator.next();
			    if (ruta.getFormato()!=Formato.Circular) 
			    {
			        // Remove the current element from the iterator and the list.
			        iterator.remove();
			    }
	    	}
	    	break;   
		}
	}
	
	public long getFiltroFormato() {
		return filtroFormato;
	}

	public void setFiltroFormato(long filtroFormato)
	{
		cleanMarcadorFormato();
		marcadorFormato[(int) filtroFormato] = "font-weight:bold; color:black";
		this.filtroFormato = filtroFormato;
	}
    
	private String[] marcadorFormato = new String[3];

	public String[] getMarcadorFormato() {
		return marcadorFormato;
	}
	
	public void setMarcadorFormato(String[] marcadorFormato) {
		this.marcadorFormato = marcadorFormato;
	}

	private void cleanMarcadorFormato()
	{
		for(int i=0;i<3;i++)
		{
			marcadorFormato[i] = new String();
		}
	}
	
	// Busqueda por texto
	
	private String cadenaBuscada = new String();
	
	public void buscarRuta()
	{
		for (Iterator<Ruta> iterator = this.allRutas.iterator(); iterator.hasNext();) 
    	{
		    Ruta ruta = iterator.next();
		    if (!ruta.getNombre().contains(cadenaBuscada)) 
		    {
		        // Remove the current element from the iterator and the list.
		        iterator.remove();
		    }
    	}
		this.buscar = true;
	}

	public String getCadenaBuscada() {
		return cadenaBuscada;
	}

	public void setCadenaBuscada(String cadenaBuscada) {
		this.cadenaBuscada = cadenaBuscada;
	}
	
	// Busqueda radial a un punto seleccionado
	
	public void busquedaRadial()
	{
		int distanciaMax = 100000;	// 100 KM
		
		puntoBuscado pBuscado = puntoBuscado.instance;
		Punto p1 = pBuscado.getPuntoBuscado();
		
		PuntoDao pDao = PuntoDao.instance;
		List<Punto> allPuntos = pDao.recuperarAllPuntos();
		
		List<Ruta> rutasDentroDelRadio = new ArrayList<>();
		
		for (Iterator<Punto> iterator = allPuntos.iterator(); iterator.hasNext();)
    	{
			// Se calcula la distancia en metros entre el punto seleccionado y los de la BD
			Punto p2 = iterator.next();
		    long R = 6378137; // Earth�s mean radius in meter
		    double dLat = (p2.lat - p1.lat)*Math.PI/180;
		    double dLong = (p2.lon - p1.lon)*Math.PI/180;
		    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
		      Math.cos(p1.lat*Math.PI/180) * Math.cos(p2.lat*Math.PI/180) *
		      Math.sin(dLong / 2) * Math.sin(dLong / 2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    double d = R * c; // the distance in meter
		    
		    if (d < distanciaMax)	// Si la distancia entre puntos es menor a la buscada
		    {
		    	if(!rutasDentroDelRadio.contains(p2.getRuta()))
		    		{
		    			rutasDentroDelRadio.add(p2.getRuta());
		    		}
		    }
    	}
		this.allRutas.clear();
		this.allRutas.addAll(rutasDentroDelRadio);
    }
	
	public String filtroRadio()
	{
		if(puntoBuscado.instance.getPuntoBuscado() != null)
			this.filtroRadio = true;
		return null;
	}
	
}
