package misServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Inicio
 */
@WebServlet("/Inicio")
public class Inicio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Inicio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession sesion = request.getSession();
		ServletContext context = getServletContext();
		String tipo = (String)sesion.getAttribute("tipo");
		if(tipo.equals("admin"))
		{
			RequestDispatcher disp = context.getRequestDispatcher("/admin.html");
			if(disp!=null)
				disp.forward(request, response);
		}
		else
		{
			if(tipo.equals("usuario"))
			{
				RequestDispatcher disp = context.getRequestDispatcher("/usuario.html");
				if(disp!=null)
					disp.forward(request, response);
			}
			else
			{
				if(tipo.equals(null))
				{
					RequestDispatcher disp = context.getRequestDispatcher("/index.html");
					if(disp!=null)
						disp.forward(request, response);
			
				}
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

}