package test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.*;
import modeloDAO.*;

/**
 * Servlet implementation class Test_act
 */
@WebServlet("/Test_act")
public class Test_act extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Variables globales para el test
	private Actividad act;
	private Persona adm;
	private Persona usr;
	private Recorrido recorrido;
	private Ruta ruta;
	private RutaRealizada rutaRealizada;
	
	
    /**
     * Default constructor. 
     */
    public Test_act() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	
    	// Creo una actividad
    			act = new Actividad();
    			act.setHabilitada(true);
    			act.setNombre("Kayak");
    			
    			
    			// Creo una persona tipo admin y luego le agrego misRutas y RutasRealizadas
    			adm = new Persona();
    			adm.setNombreUser("Admin_0");
    			adm.setNombre("Juan");
    			adm.setApellido("Labrune");
    			adm.setDni(36770329);
    			adm.setDomicilio("Calle 56");
    			adm.setMail("jp@gmail.com");
    			adm.setSexo(Sexo.Masculino);
    			adm.setFechaNac(LocalDate.now());
    			adm.setPass("123456");
    			adm.setTipo(Tipo_USER.Admin);
    			
    			// Creo una persona tipo usuario
    			usr = new Persona();
    			usr.setNombreUser("User_0");
    			usr.setNombre("Facundo");
    			usr.setApellido("Fuentes");
    			usr.setDni(35123456);
    			usr.setDomicilio("Calle 119");
    			usr.setMail("facu@gmail.com");
    			usr.setSexo(Sexo.Masculino);
    			usr.setFechaNac(LocalDate.now());
    			usr.setPass("123456");
    			usr.setTipo(Tipo_USER.Usuario);
    			
    			// Creo un recorrido
    			recorrido = new Recorrido();
    			
    			
    			// Creo una ruta y luego le agrego el registro de veces realizadas
    			ruta = new Ruta("Bosque", "descripcion", Privacidad.Publico, recorrido, Formato.Circular, 100, Dificultad.Moderado,
    					act, 500, LocalDate.now(), "Foto", 0, 0, null, usr);
    			
    			
    			// Creo una RutaRealizada
    			rutaRealizada = new RutaRealizada(3, LocalDate.now(), ruta, usr);
    			
    			
    			// Creo una lista de rutas realizadas
    			List<RutaRealizada> listaRR = new ArrayList<RutaRealizada>();
    			listaRR.add(rutaRealizada);
    			
    			// Agrego la lista de rutaRealizada a la ruta y al usuario
    			adm.setRutasRealizadas(listaRR);
    			ruta.setRegistroRealizadas(listaRR);
    			
    			// Creo una lista de misRutas
    			List<Ruta> miRuta = new ArrayList<Ruta>();
    			miRuta.add(ruta);
    			
    			// Agrego la lista miRuta al usuario
    			adm.setMisRutas(miRuta);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String accion = request.getParameter("accion");
		switch(accion)
		{
		case "agregar":
		{
			test_Agregar();	// Agrega 1 Tupla de cada Entidad
			response.sendRedirect("Test_modificar.html");
			break;
		}
		case "modificar":
		{
			test_Modificar();
			response.sendRedirect("Test_recuperar.html");
			break;
		}
		case "recuperar":
		{
			test_Recuperar();
			response.sendRedirect("Test_eliminar.html");
			break;
		}
		case "eliminar":
		{
			test_Eliminar();
			response.sendRedirect("Fin_test.html");
			break;
		}
		case "reset":
		{
			this.destroy();
			response.sendRedirect("Inicio_test.html");
		}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void test_Agregar()
	{
		ActividadDAO actDao = new ActividadDAO();
		PersonaDAO personaDao = new PersonaDAO();
		RutaRealizadaDAO rutaRealizadaDao = new RutaRealizadaDAO();
		RutaDAO rutaDao = new RutaDAO();
		RecorridoDAO recDao = new RecorridoDAO();
		
		actDao.guardarActividad(act);
		personaDao.guardarPersona(adm);
		personaDao.guardarPersona(usr);
		recDao.guardarRecorrido(recorrido);
		rutaDao.guardarRuta(ruta);
		rutaRealizadaDao.guardarRuta(rutaRealizada);
	}
	
	private void test_Eliminar()
	{
		PersonaDAO pDao = new PersonaDAO();
		pDao.eliminarPersona(adm);
		pDao.eliminarPersona(usr);
		
		ActividadDAO actDao = new ActividadDAO();
		actDao.eliminarActividad(act);

	}
	
	private void test_Modificar()
	{
//		- Modificacion de actividad: Kayak --> Ciclismo <br>
		act.setNombre("Ciclismo");
		ActividadDAO actDao = new ActividadDAO();
		actDao.modificarActividad(act);
		
//		- Modificacion de administrador: Admin_0 - Juan - Labrune --> nombre: Carlos <br>
		adm.setNombre("Carlos");
		PersonaDAO persDao = new PersonaDAO();
		persDao.modificarPersona(adm);

//		- Modificacion de usuario: User_0 - Facundo - Fuentes --> nombre: Tomas <br>
		usr.setNombre("Tomas");
		persDao.modificarPersona(usr);

//		- Modificacion de ruta: Bosque --> Formato: SoloIda y Privacidad: Privado<br>
		ruta.setFormato(Formato.SoloIda);
		ruta.setPrivacidad(Privacidad.Privado);
		RutaDAO rutaDao = new RutaDAO();
		rutaDao.modificarRuta(ruta);
		
//		- Modificacion de ruta realizada: --> Valoracion: 5 <br>
		rutaRealizada.setValoracion(5);
		RutaRealizadaDAO rutaRDao = new RutaRealizadaDAO();
		rutaRDao.modificarRuta(rutaRealizada);
		
	}
	
	private void test_Recuperar()
	{
		Persona radm = new Persona();
		PersonaDAO pDao = new PersonaDAO();
		radm = pDao.recuperarPersona(1);
		System.out.println("Parametros: nombre: "+radm.getNombre()+" app");
		
		Persona rusr = new Persona();
		rusr = pDao.recuperarPersona(2);
		System.out.println("Parametros");
		
		Actividad ract = new Actividad();
		ActividadDAO actDao = new ActividadDAO();
		ract = actDao.recuperarActividad(1);
		System.out.println("Parametros");
		
		Ruta rruta = new Ruta();
		RutaDAO rutaDao = new RutaDAO();
		rruta = rutaDao.recuperarRuta(1);
		System.out.println("Parametros");
		
		RutaRealizada rutaRealizadaRecup = new RutaRealizada();
		RutaRealizadaDAO rutaRDao = new RutaRealizadaDAO();
		rutaRealizadaRecup = rutaRDao.recuperarRutaRealizada(1);
		System.out.println("Parametros");
		
	}

}
