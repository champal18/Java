package misservlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controla
 */
@WebServlet("/Controla")
public class Controla extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Controla() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = getServletContext();
		context.setAttribute("nombre", request.getParameter("nombre"));
		switch(request.getParameter("gender"))
		{
		case "Hola":
			response.sendRedirect("/Pruebas/holaServlet");
			break;
		case "Productos":
			response.sendRedirect("http://localhost:8080/Practica2/Productos");
			break;
		case "Google":
			response.sendRedirect("https://www.google.com.ar/");
			break;
		default:
			response.sendRedirect("/Pruebas/inicio.html");
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
