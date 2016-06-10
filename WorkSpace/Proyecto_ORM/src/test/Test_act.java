package test;

import java.io.IOException;
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

import modelo.Actividad;

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
		response.getWriter().append("Served at: ").append(request.getContextPath());
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		
		// Creo una actividad y la persisto
		Actividad a = new Actividad();
		a.setHabilitada(true);
		a.setNombre("Kayak");
		em.persist(a);
		
		List<Actividad> actividades = (List<Actividad>)(em.createQuery("from modelo.Actividad a order by a.nombre asc")).getResultList();
		for(Actividad act:actividades)
		{System.out.println("Actividad: "+act.getNombre());}
		
		etx.commit();
		em.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
