package filtros;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class filtro
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD
		}
					, urlPatterns = { "/security/Productos" })
public class filtro implements Filter {

    /**
     * Default constructor. 
     */
    public filtro() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		// Casteo request y response
		HttpServletRequest rqst = (HttpServletRequest) request;
		HttpServletResponse rsp = (HttpServletResponse) response;
		
		// Obtengo sesion
		HttpSession sesion = rqst.getSession(true);
		
		// Si el atributo es igual a null o no es el usuario "juan" se redirecciona a login.html
		if(sesion.getAttribute("nombre") != null)
		{
			if(!sesion.getAttribute("nombre").equals("juan"))
			{
//				rsp.sendRedirect("http://localhost:8080/Practica4/login.html");
				RequestDispatcher disp = request.getRequestDispatcher("/login.html");
				disp.forward(request, response);
			}
		}
		else
		{
//			rsp.sendRedirect("http://localhost:8080/Practica4/login.html");
			RequestDispatcher disp = request.getRequestDispatcher("/login.html");
			disp.forward(request, response);
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
