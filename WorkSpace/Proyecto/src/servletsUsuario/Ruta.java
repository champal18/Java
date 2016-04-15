package servletsUsuario;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Ruta
 */
@WebServlet("/Ruta")
public class Ruta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ruta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		
		out.println("<head>");
		out.println("<title> Ruta </title>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("Informacion de la ruta <br><br>");
		out.println("Nombre: Los 7 lagos<br>");
		out.println("Descripcion:  <br>");
		out.println("Privacidad:  Publica<br>");
		out.println("Recorrido:  Mapa<br>");
		out.println("Formato:  Circular<br>");
		out.println("Distancia: 200km<br>");
		out.println("Dificultad: Moderado<br>");
		out.println("Actividad: Ciclismo<br>");
		out.println("Tiempo: 5 dias<br>");
		out.println("Fecha de realizacion: 15/03/2015<br>");
		out.println("Fotos:  <br>");
		
		out.println("<a href=editarRuta.html> Editar</a><br>");
		out.println("<a href=EliminarRuta> Eliminar</a><br>");
		out.println("<a href=usuario.html> Volver al menu</a>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
