package controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import modelo.Actividad;
import modelo.Persona;
import modelo.Privacidad;
import modelo.Ruta;
import modelo.Tipo_USER;
import modeloDAO.ActividadDAO;
import modeloDAO.PersonaDAO;
import modeloDAO.RutaDAO;

@SuppressWarnings("serial")
public class EstadisticaBean implements Serializable
{
	private PieChartModel pieModelUsrHabilitados;
	private PieChartModel pieModelRutasPublicas;
	private HorizontalBarChartModel barModelRutasPorActividad;
	private LineChartModel lineModelUsuariosPorMes;
	private LineChartModel lineModelRutasPorMes;
	
	public EstadisticaBean(){}

	@PostConstruct
    public void init() {
        createPieModels();
    }
	
	private void createPieModels()
	{
        createPieModelUsrHabilitados();
        createPieModelRutasPublicas();
        createbarModelRutasPorActividad();
        createLineModelUsuariosPorMes();
        createLineModelRutasPorMes();
    }
	
	// GRAFICO DE USUARIOS HABILITADOS/DESHABILITADOS
	
	public PieChartModel getPieModelUsrHabilitados()
	{
		createPieModelUsrHabilitados();
		return pieModelUsrHabilitados;
	}

	public void setPieModelUsrHabilitados(PieChartModel pieModel1)
	{
		this.pieModelUsrHabilitados = pieModel1;
	}
	
	private void createPieModelUsrHabilitados() {
        pieModelUsrHabilitados = new PieChartModel();
        
        int habilitado = 0;
        int deshabilitado = 0;
        
        PersonaDAO pDao = new PersonaDAO();
        List<Persona> allPersonas = pDao.recuperarUsuarios();
        if(allPersonas != null)
        {
	        for (Persona persona : allPersonas)
	        {
	        	if(persona.getTipo() == Tipo_USER.Usuario)
	        	{	
	        		if(persona.getHabilitado())
		        		habilitado++;
		        	else
		        		deshabilitado++;
	        	}
	        }
		}
        pieModelUsrHabilitados.set("Habilitados", habilitado);
        pieModelUsrHabilitados.set("Deshabilitado", deshabilitado);
        
        pieModelUsrHabilitados.setTitle("Usuarios habilitados/deshabilitados");
        pieModelUsrHabilitados.setLegendPosition("w");
    }

	// GRAFICO DE RUTAS PUBLICAS/PRIVADAS
	
	public PieChartModel getPieModelRutasPublicas() 
	{
		createPieModelRutasPublicas();
		return pieModelRutasPublicas;
	}

	public void setPieModelRutasPublicas(PieChartModel pieModelRutasPublicas) {
		this.pieModelRutasPublicas = pieModelRutasPublicas;
	}
	
	private void createPieModelRutasPublicas() 
	{
        pieModelRutasPublicas = new PieChartModel();
        
        int publicas = 0;
        int privadas = 0;
        
        RutaDAO rDao = new RutaDAO();
        List<Ruta> allRutas = rDao.recuperarAllRutas();
        if(allRutas != null)
        {
	        for (Ruta ruta : allRutas)
	        {
	        	if(ruta.getPrivacidad() == Privacidad.Publico)
	        		publicas++;
	        	else
	        		privadas++;
	        }
        }
        pieModelRutasPublicas.set("Publicas", publicas);
        pieModelRutasPublicas.set("Privadas", privadas);
        
        pieModelRutasPublicas.setTitle("Rutas Publicas/Privadas");
        pieModelRutasPublicas.setLegendPosition("w");
    }
	
	// GRAFICO DE BARRA PARA RUTAS POR ACTIVIDADES
	public HorizontalBarChartModel getBarModelRutasPorActividad()
	{
		createbarModelRutasPorActividad();
		return barModelRutasPorActividad;
	}

	public void setBarModelRutasPorActividad(HorizontalBarChartModel pieModelRutasPorActividad) {
		this.barModelRutasPorActividad = pieModelRutasPorActividad;
	}
	
	private void createbarModelRutasPorActividad()
	{
		barModelRutasPorActividad = new HorizontalBarChartModel();
 
		ActividadDAO aDao = new ActividadDAO();
		List<Actividad> allActividades = aDao.recuperarActividades();
		RutaDAO rDao = new RutaDAO();
		List<Ruta> allRutas = rDao.recuperarAllRutas();
		
		ChartSeries actividades = new ChartSeries();
		actividades.setLabel("Rutas");
		
		if(allActividades != null)
		{
			int[] cantidadPorActividad = null;
			cantidadPorActividad = new int[allActividades.size()];
			for(int i=0;i<cantidadPorActividad.length;i++)
			{
				cantidadPorActividad[i] = 0;
			}
			int pos = 0;
			if(allRutas != null)
			{
				for(Actividad act : allActividades)
				{
					for(Ruta ruta : allRutas)
					{
						if(ruta.getActividad().getId() == act.getId())
							cantidadPorActividad[pos]++;
					}
					actividades.set(act.getNombre(), cantidadPorActividad[pos]);
					pos++;
				}
			}
			else
			{
				for(Actividad act : allActividades)
				{
					actividades.set(act.getNombre(), cantidadPorActividad[pos]);
					pos++;
				}
			}
			Axis xAxis = barModelRutasPorActividad.getAxis(AxisType.X);
	        xAxis.setLabel("Rutas");
	        xAxis.setMin(0);
	        xAxis.setTickFormat("%d");
	        xAxis.setTickInterval("1");
	         
	        Axis yAxis = barModelRutasPorActividad.getAxis(AxisType.Y);
	        yAxis.setLabel("Actividades");
	        barModelRutasPorActividad.addSeries(actividades);
	         
	        barModelRutasPorActividad.setTitle("Cantidad de Rutas por Actividad");
	        barModelRutasPorActividad.setLegendPosition("e");
	        barModelRutasPorActividad.setStacked(true);
		}
		else
		{
			actividades.set("Vacio", 0);
			Axis xAxis = barModelRutasPorActividad.getAxis(AxisType.X);
	        xAxis.setLabel("Rutas");
	        xAxis.setMin(0);
	        xAxis.setTickFormat("%d");
	        xAxis.setTickInterval("1");
	         
	        Axis yAxis = barModelRutasPorActividad.getAxis(AxisType.Y);
	        yAxis.setLabel("Actividades");
	        barModelRutasPorActividad.addSeries(actividades);
	         
	        barModelRutasPorActividad.setTitle("Cantidad de Rutas por Actividad");
	        barModelRutasPorActividad.setLegendPosition("e");
	        barModelRutasPorActividad.setStacked(true);
		}
		
		
    }
	
	//GRAFICO LINEAL DE USUARIOS REGISTRADOS POR MES
	
	public LineChartModel getLineModelUsuariosPorMes() {
		createLineModelUsuariosPorMes();
		return lineModelUsuariosPorMes;
	}

	public void setLineModelUsuariosPorMes(LineChartModel lineModelUsuariosPorMes) {
		this.lineModelUsuariosPorMes = lineModelUsuariosPorMes;
	}
	
	@SuppressWarnings("deprecation")
	private void createLineModelUsuariosPorMes() 
	{	 
		lineModelUsuariosPorMes = new LineChartModel();
		
        ChartSeries usuarios = new ChartSeries();
        int[] cantPorMes = {0,0,0,0,0,0,0,0,0,0,0,0};
        String[] nombreMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", 
        		"Octubre", "Noviembre", "Diciembre"};
        
        int thisYear = LocalDateTime.now().getYear();
        int thisMont = LocalDateTime.now().getMonthValue();		// Retorna entre 0-11
        
        PersonaDAO pDao = new PersonaDAO();
        List<Persona> allUsuarios = pDao.recuperarUsuarios();
        if(allUsuarios != null)
        {
	        for(Persona p : allUsuarios)
	        {
	        	if(p.getTipo() == Tipo_USER.Usuario)
	        	{
		        	if((p.getFechaRegistro().getYear()+1900) == thisYear)	// getYear retorna el a�o A�oActual-1900
		        	{
		        		cantPorMes[p.getFechaRegistro().getMonth()]++;	// getMonth retorna entre 1-12
		        	}
		        	else
		        	{
		        		if((p.getFechaRegistro().getYear()+1900) == (thisYear-1))	// Si pertenece al a�o anterior y al mes posterior al actual
		        		{
		        			if(p.getFechaRegistro().getMonth()>(thisMont-1))		
		        				cantPorMes[p.getFechaRegistro().getMonth()]++;
		        		}
		        	}
	        	}
	        }
        }
        int pos = thisMont;	// Comienzo desde el mes siguiente al actual	
        for(int i=0;i<12;i++)
        {
        	if(pos == 12)	// Si ya proceso diciembre reinicio la posicion
        		pos = 0;
        	usuarios.set(nombreMeses[pos], cantPorMes[pos]);
        	pos++;
        }
 
        lineModelUsuariosPorMes.addSeries(usuarios);
		
		lineModelUsuariosPorMes.setTitle("Registro de Usuarios del ultimo año");
		lineModelUsuariosPorMes.setShowPointLabels(true);
		lineModelUsuariosPorMes.getAxes().put(AxisType.X, new CategoryAxis("Meses"));
		Axis yAxis = lineModelUsuariosPorMes.getAxis(AxisType.Y);
        yAxis.setLabel("Usuarios");
        yAxis.setMin(0);
        
        Arrays.sort(cantPorMes);
        if(cantPorMes[cantPorMes.length-1]!=0)
        	yAxis.setMax(cantPorMes[cantPorMes.length-1]*2);
        else
        	yAxis.setMax(1);
        yAxis.setTickFormat("%d");	// Configuro el formato entero para el eje
        yAxis.setTickInterval("1");
    }
	
	
	//GRAFICO LINEAL DE RUTAS CREADAS POR MES
	
		public LineChartModel getLineModelRutasPorMes() {
			createLineModelRutasPorMes();
			return lineModelRutasPorMes;
		}

		public void setLineModelRutasPorMes(LineChartModel lineModelRutasPorMes) {
			this.lineModelRutasPorMes = lineModelRutasPorMes;
		}
		
		@SuppressWarnings("deprecation")
		private void createLineModelRutasPorMes() 
		{	 
			lineModelRutasPorMes = new LineChartModel();
			
	        ChartSeries rutas = new ChartSeries();
	        int[] cantPorMes = {0,0,0,0,0,0,0,0,0,0,0,0};
	        String[] nombreMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", 
	        		"Octubre", "Noviembre", "Diciembre"};
	        
	        int thisYear = LocalDateTime.now().getYear();
	        int thisMont = LocalDateTime.now().getMonthValue();		// Retorna entre 0-11
	        
	        RutaDAO rDao = new RutaDAO();
	       	List<Ruta> allRutas = rDao.recuperarAllRutas();
	        if(allRutas != null)
	        {
		        for(Ruta r : allRutas)
		        {
		        	if((r.getFechaRegistro().getYear()+1900) == thisYear)	// getYear retorna el a�o A�oActual-1900
		        	{
		        		cantPorMes[r.getFechaRegistro().getMonth()]++;	// getMonth retorna entre 1-12
		        	}
		        	else
		        	{
		        		if((r.getFechaRegistro().getYear()+1900) == (thisYear-1))	// Si pertenece al a�o anterior y al mes posterior al actual
		        		{
		        			if(r.getFechaRegistro().getMonth()>(thisMont-1))		
		        				cantPorMes[r.getFechaRegistro().getMonth()]++;
		        		}
		        	}
		        		
		        }
	        }
	        int pos = thisMont;	// Comienzo desde el mes siguiente al actual	
	        for(int i=0;i<12;i++)
	        {
	        	if(pos == 12)	// Si ya proceso diciembre reinicio la posicion
	        		pos = 0;
	        	rutas.set(nombreMeses[pos], cantPorMes[pos]);
	        	pos++;
	        }
	 
	        lineModelRutasPorMes.addSeries(rutas);
			
			lineModelRutasPorMes.setTitle("Rutas creadas en el ultimo año");
			lineModelRutasPorMes.setShowPointLabels(true);
			lineModelRutasPorMes.getAxes().put(AxisType.X, new CategoryAxis("Meses"));
			Axis yAxis = lineModelRutasPorMes.getAxis(AxisType.Y);
	        yAxis.setLabel("Rutas");
	        yAxis.setMin(0);
	        
	        Arrays.sort(cantPorMes);
	        if(cantPorMes[cantPorMes.length-1] != 0)
	        	yAxis.setMax(cantPorMes[cantPorMes.length-1]*2);
	        else
	        	yAxis.setMax(1);
	        yAxis.setTickFormat("%d");	// Configuro el formato entero para el eje
	        yAxis.setTickInterval("1");
	    }

}
