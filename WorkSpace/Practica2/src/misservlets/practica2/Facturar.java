package misservlets.practica2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Facturar
 */
@WebServlet("/Facturar")
public class Facturar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Facturar() {
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
		out.println("<title> Golosinas </title>");
		out.println("<style>");
		out.println("table, th, td { border: 1px solid black;}");
		out.println("</style>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<table>");
			out.println("<tr>");
				out.println("<th>Golosina</th>");
				out.println("<th>Cantidad</th>");
				out.println("<th>Precio total</th>");
			out.println("</tr>");
			
			HttpSession sesion = request.getSession(true);
			int cantG = (int) sesion.getAttribute("cantGolosinas");
			int i;
			int total = 0;
			for(i=0;i<cantG;i++)
			{
				int aux = i+1;
				Golosina g = (Golosina) sesion.getAttribute("g"+aux);
				g.cantComprada = Integer.parseInt(request.getParameter("cant"+aux));
				sesion.setAttribute("g"+aux, g);
				out.println("<tr>");
					out.println("<td>"+g.nombre+"</td>");
					out.println("<td>"+g.cantComprada+"</td>");
					out.println("<td>"+(g.cantComprada*g.precio)+"</td>");
					total = total + (g.cantComprada*g.precio);
				out.println("</tr>");
			}
		out.println("</table>");
		out.println("Precio total de la compra = "+total+"</br>");
		out.println("<a href=http://localhost:8080/Practica2/Productos> Seguir comprando</a> </br>");
		out.println("<a href=http://localhost:8080/Practica2/TerminarSesion> Salir</a> </br>");

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
