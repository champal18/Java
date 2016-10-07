package controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
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
import modeloDAO.ActividadDAO;
import modeloDAO.PersonaDAO;
import modeloDAO.RutaDAO;

@SuppressWarnings("serial")
public class EstadisticaBean implements Serializable
{
	private PieChartModel pieModelUsrHabilitados;
	private PieChartModel pieModelRutasPublicas;
	private HorizontalBarChartModel barModelRutasPorActividad;
	private LineChartModel lineModelRutasPorActividad;

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
        createLineModelRutasPorActividad();
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
        for (Persona persona : allPersonas)
        {
        	if(persona.getHabilitado())
        		habilitado++;
        	else
        		deshabilitado++;
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
        for (Ruta ruta : allRutas)
        {
        	if(ruta.getPrivacidad() == Privacidad.Publico)
        		publicas++;
        	else
        		privadas++;
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
	
	private void createbarModelRutasPorActividad() {
		barModelRutasPorActividad = new HorizontalBarChartModel();
 
		ActividadDAO aDao = new ActividadDAO();
		List<Actividad> allActividades = aDao.recuperarActividades();
		RutaDAO rDao = new RutaDAO();
		List<Ruta> allRutas = rDao.recuperarAllRutas();
		
		int[] cantidadPorActividad = new int[allActividades.size()];
		for(int i=0;i<cantidadPorActividad.length;i++)
		{
			cantidadPorActividad[i] = 0;
		}
		int pos = 0;
		
		ChartSeries actividades = new ChartSeries();
		actividades.setLabel("Rutas");
		
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
        
        barModelRutasPorActividad.addSeries(actividades);
         
        barModelRutasPorActividad.setTitle("Cantidad de Rutas por Actividad");
        barModelRutasPorActividad.setLegendPosition("e");
        barModelRutasPorActividad.setStacked(true);
        
        Axis xAxis = barModelRutasPorActividad.getAxis(AxisType.X);
        xAxis.setLabel("Rutas");
        xAxis.setMin(0);
        
        Arrays.sort(cantidadPorActividad);
        xAxis.setMax((cantidadPorActividad[cantidadPorActividad.length-1])*2);
        xAxis.setTickFormat("%d");
        xAxis.setTickInterval("1");
         
        Axis yAxis = barModelRutasPorActividad.getAxis(AxisType.Y);
        yAxis.setLabel("Actividades"); 
       
    }
	
	//GRAFICO LINEAL DE USUARIOS REGISTRADOS POR MES
	
	public LineChartModel getLineModelRutasPorActividad() {
		createLineModelRutasPorActividad();
		return lineModelRutasPorActividad;
	}

	public void setLineModelRutasPorActividad(LineChartModel lineModelRutasPorActividad) {
		this.lineModelRutasPorActividad = lineModelRutasPorActividad;
	}
	
	@SuppressWarnings("deprecation")
	private void createLineModelRutasPorActividad() 
	{	 
		lineModelRutasPorActividad = new LineChartModel();
		
        ChartSeries usuarios = new ChartSeries();
        int[] cantPorMes = {0,0,0,0,0,0,0,0,0,0,0,0};
        String[] nombreMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", 
        		"Octubre", "Noviembre", "Diciembre"};
        
        int thisYear = LocalDateTime.now().getYear();
        int thisMont = LocalDateTime.now().getMonthValue();		// Retorna entre 0-11
        
        PersonaDAO pDao = new PersonaDAO();
        List<Persona> allUsuarios = pDao.recuperarUsuarios();
        
        for(Persona p : allUsuarios)
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
        
        int pos = thisMont;	// Comienzo desde el mes siguiente al actual	
        for(int i=0;i<12;i++)
        {
        	if(pos == 12)	// Si ya proceso diciembre reinicio la posicion
        		pos = 0;
        	usuarios.set(nombreMeses[pos], cantPorMes[pos]);
        	pos++;
        }
 
        lineModelRutasPorActividad.addSeries(usuarios);
		
		lineModelRutasPorActividad.setTitle("Registro de Usuarios del ultimo a�o");
		lineModelRutasPorActividad.setShowPointLabels(true);
		lineModelRutasPorActividad.getAxes().put(AxisType.X, new CategoryAxis("Meses"));
		Axis yAxis = lineModelRutasPorActividad.getAxis(AxisType.Y);
        yAxis.setLabel("Usuarios");
        yAxis.setMin(0);
        
        Arrays.sort(cantPorMes);
        yAxis.setMax(cantPorMes[cantPorMes.length-1]*2);
        yAxis.setTickFormat("%d");	// Configuro el formato entero para el eje
        yAxis.setTickInterval("1");
    }
}
