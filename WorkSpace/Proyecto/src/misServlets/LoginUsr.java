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

import modelo.Persona;
import modelo.Tipo_USER;
import modeloDAO.PersonaDAO;

/**
 * Servlet implementation class LoginUsr
 */

@WebServlet("/LoginUsr")
public class LoginUsr extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginUsr() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		// Probando
		
		ServletContext context = getServletContext();
		String name = request.getParameter("nombre");
		String pass = request.getParameter("clave");
		
		PersonaDAO pDao = new PersonaDAO();
		Persona p = pDao.recuperarPersona(name);
		if(p != null)
		{
			if(p.getHabilitado())
			{
				if(p.getPass().equals(pass))
				{
					HttpSession sesion = request.getSession(true);
					sesion.setAttribute("usrId", p.getId());
					sesion.setAttribute("usrName", p.getNombre());
					RequestDispatcher disp;
					if(p.getTipo() == Tipo_USER.Usuario)
						disp = context.getRequestDispatcher("/faces/usuario.xhtml");
					else
						disp = context.getRequestDispatcher("/admin.html");
					if(disp != null)
						disp.forward(request, response);
				}
				else
				{
					RequestDispatcher disp = context.getRequestDispatcher("/error.html");
					if(disp != null)
						disp.forward(request, response);
				}
			}
			else
			{
				RequestDispatcher disp = context.getRequestDispatcher("/errorDeshabilitado.html");
				if(disp != null)
					disp.forward(request, response);
			}
		}
		else
		{
			RequestDispatcher disp = context.getRequestDispatcher("/error.html");
			if(disp != null)
				disp.forward(request, response);
		}
		
		// Fin de prueba
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
