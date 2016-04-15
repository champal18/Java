package misServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Encuesta
 */
@WebServlet("/Encuesta")
public class Encuesta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int perro = 0;
	int gato = 0;
	int lobo = 0;
	int tortuga = 0;
	int pez = 0;
	int serpiente = 0;
	int porcentaje = 0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Encuesta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("mascota").equals("Perro"))
			perro++;
		if(request.getParameter("mascota").equals("Gato"))
			gato++;
		if(request.getParameter("mascota").equals("Lobo"))
			lobo++;
		if(request.getParameter("mascota").equals("Serpiente"))
			serpiente++;
		if(request.getParameter("mascota").equals("Pez"))
			pez++;
		if(request.getParameter("mascota").equals("Tortuga"))
			tortuga++;
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		
		out.println("<head>");
		out.println("<title> Encuesta de mascotas </title>");
		out.println("<style>");
		out.println("table, th, td { border: 1px solid black;}");
		out.println("</style>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<table>");
			out.println("<tr>");
				out.println("<th>Animal</th>");
				out.println("<th>Cantidad</th>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<td>Perro</td>");
				out.println("<td>"+perro+"</td>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<td>Gato</td>");
				out.println("<td>"+gato+"</td>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<td>Lobo</td>");
				out.println("<td>"+lobo+"</td>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<td>Pez</td>");
				out.println("<td>"+pez+"</td>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<td>Serpiente</td>");
				out.println("<td>"+serpiente+"</td>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<td>Tortuga</td>");
				out.println("<td>"+tortuga+"</td>");
			out.println("</tr>");
		out.println("</table>");
		
		String masVotado = mayor();
		out.println("El animal mas votado fue: '"+masVotado+"' con un '"+porcentaje+"'% de los votos!</br>");
		
		String aux = "http://localhost:8080/Practica1Web31/mascotas.html";
		out.println("<a href='"+aux+"'>Regresar a mascotas</a>");
		out.println("</body>");
		out.println("</html>");
	
	}
	
	private String mayor()
	{	
		int cant[] = {perro,gato,serpiente,pez,lobo,tortuga};
		String nombre[] = {"perro","gato","serpiente","pez","lobo","tortuga"};
		int cantMax = 0;
		int idMax = 0;
		int i;
		int total = 0;
		for(i=0;i<6;i++)
		{
			total = total + cant[i];
			if (cant[i]>cantMax)
			{
				idMax = i;
				cantMax = cant[i];
			}
		}
		porcentaje = (cant[idMax]*100)/total;
		return nombre[idMax];
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
