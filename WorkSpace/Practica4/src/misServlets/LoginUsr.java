package misServlets;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginUsr
 */
@WebServlet("/LoginUsr")
public class LoginUsr extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Hashtable<String,String> logins = new Hashtable<String,String>();
	
    /**
     * Default constructor. 
     */
    public LoginUsr() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException
    {
    	// TODO Auto-generated method stub
    	super.init();
    	logins.put("juan","123456");

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
			String contraseņa = logins.get(request.getParameter("nombre"));
			if (request.getParameter("pass").equals(contraseņa))
			{
				// Se recupera la sesion del usuario
				HttpSession sesion = request.getSession(true);
				
				// Se almacenan todos los datos en la sesion
				sesion.setAttribute("nombre", request.getParameter("nombre"));
				sesion.setAttribute("pass", request.getParameter("pass"));
				sesion.setAttribute("apellido", request.getParameter("apellido"));
				sesion.setAttribute("direccion", request.getParameter("direccion"));
				
				// Redirecciona el requerimiento http al servlet Productos
				RequestDispatcher disp = request.getRequestDispatcher("/security/Productos");
				disp.forward(request, response);
				
			}
			else {
				// requerimiento http se redirec a login.html
				response.sendRedirect("login.html");
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
