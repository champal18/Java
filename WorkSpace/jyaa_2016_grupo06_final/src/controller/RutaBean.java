package controller;

import java.io.IOException;
import java.io.InputStream;
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
import org.apache.myfaces.shared_tomahawk.util.Assert;

import de.micromata.opengis.kml.v_2_2_0.*;
import jersey.repackaged.com.google.common.collect.Lists;
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
	
	// Flags
	private boolean control = false;	// Una vez creada una ruta nueva, se vuelve a llamar al constructor
	private boolean buscar = false; // Flag que indica que se inicio una busqueda para no renovar la lista de rutas

	private Ruta rutaSeleccionada = new Ruta();
	private long idActividad;
	
	// Upload de imagen para la BD
	private UploadedFile file;
	private UploadedFile[] files = new UploadedFile[5];
	private int pos = 0;
	
	// CARGA DE ARCHIVO KML
	private UploadedFile fileKML;
	private boolean upKML = false;
	
	// Listado de rutas de la BD
	private List<Ruta> allRutas = rDao.recuperarAllRutasPublicas();
	private List<Ruta> backUpRutas = rDao.recuperarAllRutasPublicas();
	
	// Enumerativo para definir orden actual de la lista
	private enum orden {distancia,dificultad,puntuacion,cantRealizaciones};
	private orden ordenActual = orden.distancia;	// Por defecto la lista se ordena por distancia
	private boolean criterioDesc = true;			// Flag que indica el criterio Ascendente o Descendente
	
	// Filtrado de Rutas
	private Actividad filtroActividad;
	private long filtroDistancia;
	private Dificultad filtroDificultad;
	private long filtroFormato;
	private boolean filtroRadio;
	
	private int distancia = 25;		// Distancia MAXIMA para el filtrado de rutas en un determinado radio
	
	// Cadena para la busqueda por texto
	private String cadenaBuscada = new String();
	
	
	public RutaBean()
	{
		
	}
	
	public Ruta getRuta()
	{
		if(!control)
		{
			PuntoDao puntoDao=PuntoDao.instance;
			// Limpio los puntos del mapa
			puntoDao.limpiarMapa();
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
    			
    	if(upKML)	// Si se cargo un archivo KML cargo los puntos
    	{
    		try {
    			puntoDao.limpiarMapa();
				parseKml();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		upKML = false;
    	}
    	this.fileKML = null;
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
		
		// Guardo el id de la ruta seleccionada en la sesion
		HttpSession session;
		FacesContext context = FacesContext.getCurrentInstance();
	    session = (HttpSession) context.getExternalContext().getSession(true);
	    session.setAttribute("idRuta", selec.getId());
		
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
	
	public String editarRuta() throws IOException
	{	
		Actividad actividad=new Actividad();
		ActividadDAO aDAO= new ActividadDAO();
		
		actividad=aDAO.recuperarActividad(idActividad);
		
		rutaSeleccionada.setActividad(actividad);
		
		rDao.modificarRuta(rutaSeleccionada);
		
		PuntoDao puntoDao = PuntoDao.instance;
		puntoDao.eliminarPuntosRuta(rutaSeleccionada.getId());
		
		if(upKML)	// Si se cargo un archivo KML cargo los puntos
    	{
    		try {
    			puntoDao.limpiarMapa();
				parseKml();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		upKML = false;
    	}
    	this.fileKML = null;
		
		puntoDao.guardarPuntos(this.rutaSeleccionada);
		
		puntoDao.limpiarMapa();
		
		FotoDAO fDao = new FotoDAO();
		List<Foto> fotos = fDao.recuperarFotos(rutaSeleccionada.getId());
		
		for(int i=0;i<5;i++)
    	{
    		if(files[i] != null)
    		{
    			if(i<fotos.size())
    			{	
    				Foto f = fotos.get(i);
	    			f.setImg(files[i].getBytes());
	    			fotos.set(i, f);
    			}
    			else
    			{
					Foto f = new Foto();
					f.setImg(files[i].getBytes());
					f.setRuta(rutaSeleccionada);
					fotos.add(f);
    			}
    		}
    	}
		for(Foto f : fotos)
		{
			fDao.modificarFoto(f);
		}
		
		// Limpio el vector de archivos
		this.files = new UploadedFile[5];
		pos = 0;
		
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
    	if(pos<5)
    	{
    		if(file != null)
	    	{
	    		this.files[pos] = file;
	    	}
	    	else
	    	{
	    		this.files[pos] = null;
	    	}
    	}
    	pos++;
    }

	// Lista de todas las rutas
	public List<Ruta> getAllRutas()
	{
		this.backUpRutas = rDao.recuperarAllRutasPublicas();	// Obtengo las rutas publicas de la BD
		if(backUpRutas != null)
		{
			this.allRutas.clear();
			this.allRutas.addAll(backUpRutas);				// Comienzo con la lista sin ordenar ni filtrar
			// Primero filtro
			filtrar();
			// Segundo ordeno
			ordenar();
		}
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
		}
		if(criterioDesc)
		{
			this.allRutas = Lists.reverse(allRutas);
		}
	}
	
	public void setCriterio(int id)
	{
		switch(id)
		{
		case 0: 
			criterioDesc = true;
			break;
		case 1:
			criterioDesc = false;
			break;
		}
	}
	
	public String getCriterioStyle(int id)
	{
		if(id==0)	// Si esta en orden Descendente
		{
			if(criterioDesc)
				return "btn btn-info";
			else
				return "btn btn-primary";
		}
		else	// Si esta en orden Ascendente
		{
			if(!criterioDesc)
				return "btn btn-info";
			else
				return "btn btn-primary";
		}
	}
	
	public String getOrdenStyle(int opcion)
	{
		switch(opcion)
		{
		case 1:
			if(this.ordenActual == orden.distancia)
				return "btn btn-info";
			break;
		case 2:
			if(this.ordenActual == orden.dificultad)
				return "btn btn-info";
			break;
		case 3:
			if(this.ordenActual == orden.puntuacion)
				return "btn btn-info";
			break;
		case 4:
			if(this.ordenActual == orden.cantRealizaciones)
				return "btn btn-info";
			break;
		}
		return "btn btn-primary";
	}
	
	// FILTRADO
	
	public void filtrar()
	{
		buscarRuta();
		if(filtroRadio)
			busquedaRadial();
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
	
	public String getMarcadorActividad(long id) 
	{
		if((id==0)&&(this.filtroActividad == null))
				return "font-weight:bold; color:black";
		if(this.filtroActividad != null)
		{
			if(id == this.filtroActividad.getId())
				return "font-weight:bold; color:black";
			else
				return new String();
		}
		return new String();
	}

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
			compararDistancias(100, 10000);
			break;
		}
	}

	public long getFiltroDistancia() {
		return filtroDistancia;
	}

	public void setFiltroDistancia(long filtroDistancia)
	{
		this.filtroDistancia = filtroDistancia;
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
	
	public String getStyleDistancia(long num)
	{
		if(this.filtroDistancia == num)
		{
			return "font-weight:bold; color:black";
		}
		else
			return "";
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
	
	public String getStyleDificultad(int filtroD)
	{
		switch((int)filtroD)
		{
		case 0:
			if(this.filtroDificultad == null)
			return "font-weight:bold; color:black";
			break;
		case 1:
			if(this.filtroDificultad == Dificultad.Facil)
				return "font-weight:bold; color:black";
			break;
		case 2:
			if(this.filtroDificultad == Dificultad.Moderado)
				return "font-weight:bold; color:black";
			break;
		case 3:
			if(this.filtroDificultad == Dificultad.Dificil)
				return "font-weight:bold; color:black";
			break;
		case 4:
			if(this.filtroDificultad == Dificultad.MuyDificil)
				return "font-weight:bold; color:black";
			break;
		case 5:
			if(this.filtroDificultad == Dificultad.SoloExpertos)
				return "font-weight:bold; color:black";
			break;
		}
		return "";
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
		this.filtroFormato = filtroFormato;
	}
	
	public String getStyleFormato(long filtroF)
	{
		if(filtroF == this.filtroFormato)
		{
			return "font-weight:bold; color:black";
		}
		else
			return "";
	}
    
	// Busqueda por texto
	
	public boolean isBuscar() {
		return buscar;
	}

	public void setBuscar(boolean buscar) {
		this.buscar = buscar;
	}
	
	public void buscarRuta()
	{
		for (Iterator<Ruta> iterator = this.allRutas.iterator(); iterator.hasNext();) 
    	{
		    Ruta ruta = iterator.next();
		    String lower= ruta.getNombre().toLowerCase();
		    if (!lower.contains(cadenaBuscada.toLowerCase())) 
		    {
		        // Remove the current element from the iterator and the list.
		        iterator.remove();
		    }
    	}
	}

	public String getCadenaBuscada() {
		return cadenaBuscada;
	}

	public void setCadenaBuscada(String cadenaBuscada) {
		this.cadenaBuscada = cadenaBuscada;
	}
	
	// Busqueda radial a un punto seleccionado
	
	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	
	public void busquedaRadial()
	{	
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
		    
		    if (d < (distancia*1000))	// Si la distancia entre puntos es menor a la buscada
		    {
		    	if((!rutasDentroDelRadio.contains(p2.getRuta())) && (p2.getRuta().getPrivacidad() == Privacidad.Publico))
		    		{
		    			rutasDentroDelRadio.add(p2.getRuta());
		    		}
		    }
    	}
		this.allRutas.clear();
		this.allRutas.addAll(rutasDentroDelRadio);
    }
	
	public String filtroRadio(boolean bool)
	{
		if(bool)
		{
			if(puntoBuscado.instance.getPuntoBuscado() != null)
				this.filtroRadio = true;
		}
		else
			this.filtroRadio = bool;
		return null;
	}
	
	public String filtroRadioStyle(int id)
	{	
		if(id==0)
		{
			if(filtroRadio)
				return "btn btn-info";
		}
		else
		{
			if(!filtroRadio)
				return "btn btn-info";
		}
		return "btn btn-primary";
	}
	
	// CARGA DE ARCHIVO KML
	
	public UploadedFile getFileKML() {
		return fileKML;
	}

	public void setFileKML(UploadedFile fileKML) {
		this.fileKML = fileKML;
		if(fileKML != null)
			upKML = true;
	}
	
	public void parseKml() throws IOException {
	    InputStream is = fileKML.getInputStream();
	    Assert.notNull(is);
	    Kml kml = Kml.unmarshal(is);
	    Feature feature = kml.getFeature();
	    parseFeature(feature);
	}

	private void parseFeature(Feature feature) {
	    if(feature != null) {
	        if(feature instanceof Document) 
	        {
	            Document document = (Document) feature;
	            List<Feature> featureList = document.getFeature();
	            for(Feature documentFeature : featureList) {
	                if(documentFeature instanceof Placemark)
	                {
	                    Placemark placemark = (Placemark) documentFeature;
	                    Geometry geometry = placemark.getGeometry();
	                    parseGeometry(geometry);
	                }
	            }
	        }
	    }
	}

	private void parseGeometry(Geometry geometry) 
	{
	    if(geometry != null) 
	    {
	        if(geometry instanceof LineString) 
	        {
	        	LineString polygon = (LineString) geometry;
	        	List<Coordinate> coordinates =polygon.getCoordinates();
	        	if(coordinates != null) 
	        	{
                    for(Coordinate coordinate : coordinates) 
                    {
                        parseCoordinate(coordinate);
                    }
              	}
	        }
	    }
	}

	private void parseCoordinate(Coordinate coordinate) {
		PuntoDao puntoDao = PuntoDao.instance;
	    if(coordinate != null)
	    {
	        Punto p = new Punto(coordinate.getLatitude(), coordinate.getLongitude());
	        puntoDao.getPuntos().put(p.getIndice(), p);
	    }
	}
	
	public String refreshBusqueda()
	{
		this.filtroActividad = null;
		this.filtroDificultad = null;
		this.filtroDistancia = 0;
		this.filtroFormato = 0;
		this.filtroRadio = false;
		this.cadenaBuscada = new String();
		PuntoDao.instance.limpiarMapa();
		buscar = false;
		
		return "busquedaRutas";
	}
	
	public String getTitulo(String cadena)
	{
		int tamanoMaximo = 40;
		if(cadena.length()>tamanoMaximo)
		{
			String result = cadena.substring(0,39);
			result = result + "...";
			return result;
		}
		return cadena;
	}
	
	public void eliminarFoto(int id)
	{
		FotoDAO fDao = new FotoDAO();
		List<Foto> lista = fDao.recuperarFotos(rutaSeleccionada.getId());
		if(lista!=null)
		{
			if(lista.size()>id)
			{
				Foto f = lista.get(id);
				fDao.eliminarFoto(f);
			}
		}	
	}

}
