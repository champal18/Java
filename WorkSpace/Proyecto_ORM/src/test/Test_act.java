package test;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import modelo.Actividad;
import modeloDAO.ActividadDAO;
import modeloDAO.SingletonEMF;

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
		
		// Creo una actividad y la persisto
		/*
		Actividad act = new Actividad();
		act.setHabilitada(true);
		act.setNombre("Kayak");
		ActividadDAO actDao = new ActividadDAO();
		actDao.guardarActividad(act);
		*/
		
		// Busco actividad por ID
		ActividadDAO actDao = new ActividadDAO();
		Actividad act = actDao.recuperarActividad(3);
		response.getWriter().append("Actividad buscada: "+act.getNombre());
		
		/*
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		List<Actividad> actividades = (List<Actividad>)(em.createQuery("from modelo.Actividad a order by a.nombre asc")).getResultList();				
				
		for(Actividad a:actividades)
		{
			System.out.println("Actividad: "+ act.getNombre());
		}
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}