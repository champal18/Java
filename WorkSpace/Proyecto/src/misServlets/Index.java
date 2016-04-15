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
 * Servlet implementation class Index
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession sesion = request.getSession(true);
		ServletContext context = getServletContext();
		String tipo = (String)sesion.getAttribute("tipo");
	if(sesion.getAttribute("tipo")!=null)
		{
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
			}
		}
	else
		response.sendRedirect("/Proyecto/index.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
