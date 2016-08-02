package misServlets;

import java.io.IOException;
import java.io.PrintWriter;

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
		// TODO Auto-generated method stub
		/*
		ServletContext context = getServletContext();
		String name = context.getInitParameter("usu1");
		String pass = context.getInitParameter("pass1");
		if((name.equals(request.getParameter("nombre"))) && (pass.equals(request.getParameter("clave"))))
		{
			RequestDispatcher disp = context.getRequestDispatcher("/usuario.html");
			HttpSession sesion = request.getSession(true);
			sesion.setAttribute("tipo", (String)"usuario");
			sesion.setAttribute("nombre", name);
			if(disp != null)
				disp.forward(request, response);
		}
		else
		{
			name = context.getInitParameter("adm1");
			pass = context.getInitParameter("apass1");
			if((name.equals(request.getParameter("nombre"))) && (pass.equals(request.getParameter("clave"))))
			{
				RequestDispatcher disp = context.getRequestDispatcher("/admin.html");
				HttpSession sesion = request.getSession(true);
				sesion.setAttribute("tipo", (String)"admin");
				sesion.setAttribute("nombre", name);
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
		*/
		
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
					RequestDispatcher disp;
					if(p.getTipo() == Tipo_USER.Usuario)
						disp = context.getRequestDispatcher("/usuario.html");
					else
						disp = context.getRequestDispatcher("/admin.html");
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
