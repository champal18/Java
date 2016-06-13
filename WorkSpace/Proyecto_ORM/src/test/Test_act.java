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

    /**
     * Default constructor. 
     */
    public Test_act() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		test_Agregar();	// Agrega 1 Tupla de cada Entidad
//		test_eliminar();// Elimina 1 Persona y sus relaciones
		response.getWriter().append("Test Finalizado!");
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
		// Creo una actividad
		Actividad act = new Actividad();
		act.setHabilitada(true);
		act.setNombre("Kayak");
		ActividadDAO actDao = new ActividadDAO();
		
		// Creo una persona tipo admin y luego le agrego misRutas y RutasRealizadas
		Persona adm = new Persona();
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
		PersonaDAO admDao = new PersonaDAO();
		
		// Creo un recorrido
		Recorrido recorrido = new Recorrido();
		RecorridoDAO recDao = new RecorridoDAO();
		
		// Creo una ruta y luego le agrego el registro de veces realizadas
		Ruta ruta = new Ruta("bosque", "descripcion", Privacidad.Publico, recorrido, Formato.Circular, 100, Dificultad.Moderado,
				act, 500, LocalDate.now(), "Foto", 0, 1, null, adm);
		RutaDAO rutaDao = new RutaDAO();
		
		// Creo una RutaRealizada
		RutaRealizada rutaRealizada = new RutaRealizada(3, LocalDate.now(), ruta, adm);
		RutaRealizadaDAO rutaRealizadaDao = new RutaRealizadaDAO();
		
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
		
		actDao.guardarActividad(act);
		admDao.guardarPersona(adm);
		recDao.guardarRecorrido(recorrido);
		rutaDao.guardarRuta(ruta);
		rutaRealizadaDao.guardarRuta(rutaRealizada);
		
	}
	
	private void test_eliminar()
	{
		Persona adm = new Persona();
		adm.setId(1);
		
		PersonaDAO admDao = new PersonaDAO();
		admDao.eliminarPersona(adm);
		
	}

}
