package misservlets.practica2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Productos
 */
@WebServlet("/Productos")
public class Productos extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	Hashtable<String,Integer> productos = new Hashtable<String, Integer>();
	int cantGolosinas=0;
	public boolean flag = false;
	
	// Consulta:
	// 1- Como soluciono lo del init?
	// 2- Pregunta 2 del tp, acerca de como evitar que alguien ejecute directamente el servlet producto sin hacer login??
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Productos() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	
    	ServletContext context = getServletContext();
    	cantGolosinas = Integer.parseInt(context.getInitParameter("cantidadTotal"));
    	String parametro;
    	int i=0;
    	String nombre;
    	Integer precio;
    	for(i=0;i<cantGolosinas;i++)
    	{
    		int aux = i+1;
    		parametro = "golo"+aux;
    		nombre = context.getInitParameter(parametro);
    		parametro = "pu"+aux;
    		precio = Integer.parseInt(context.getInitParameter(parametro));
    		productos.put(nombre, precio);
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(!flag) // Por unica vez cargo en la sesion cada golosina
		{
			HttpSession sesion = request.getSession(true);
			sesion.setAttribute("cantGolosinas", this.cantGolosinas);
			Enumeration<String> e = productos.keys();
			int i=1;
			while(e.hasMoreElements())
			{
				String nombre = (String) e.nextElement();
				Integer precio = productos.get(nombre);
				Golosina g = new Golosina(nombre,0,precio);
				sesion.setAttribute("g"+i, g);
				i++;
			}
			flag = true;
		}
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		
		out.println("<head>");
		out.println("<title> Golosinas </title>");
		out.println("<style>");
		out.println("table, th, td { border: 1px solid black;}");
		out.println("</style>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<form action=Facturar method=post>");
		out.println("<table>");
			out.println("<tr>");
				out.println("<th>Golosina</th>");
				out.println("<th>Precio</th>");
				out.println("<th>Cantidad</th>");
			out.println("</tr>");
			
			HttpSession sesion = request.getSession(true);
			int cantG = (int) sesion.getAttribute("cantGolosinas");
			int i;
			for(i=0;i<cantG;i++)
			{
				int aux = i+1;
				Golosina g = (Golosina) sesion.getAttribute("g"+aux);
				out.println("<tr>");
					out.println("<td>"+g.nombre+"</td>");
					out.println("<td>"+g.precio+"</td>");
					out.println("<td> <input name=cant"+aux+" type= text value="+g.cantComprada+" /> </td>");
				out.println("</tr>");
			}
		out.println("</table>");
		out.println("<input type=submit value=Facturar>");
		out.println("</form>");
		out.println("<a href=http://localhost:8080/Practica2/TerminarSesion> Salir</a>");

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
