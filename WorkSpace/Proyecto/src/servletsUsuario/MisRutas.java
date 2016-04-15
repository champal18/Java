package servletsUsuario;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MisRutas
 */
@WebServlet("/MisRutas")
public class MisRutas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MisRutas() {
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
		out.println("<title> Mis rutas </title>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("Lista de mis rutas!!! <br><br><br>");
		out.println("Ruta de \"Los 7 lagos\"<br>");
		out.println("Actividad: Ciclismo <br>");
		out.println("Dificultad: Moderado <br>");
		out.println("<a href=Ruta> Ver ruta</a><br><br><br>");
		
		out.println("Ruta del desierto <br>");
		out.println("Actividad: Mountain bike <br>");
		out.println("Dificultad: Moderado <br>");
		out.println("<a href=Ruta> Ver ruta</a><br><br><br>");
		
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
