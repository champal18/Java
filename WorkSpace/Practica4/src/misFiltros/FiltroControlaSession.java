package misFiltros;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FiltroControlaSession
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE
		}
					, urlPatterns = { "/Practica4/Security/*" })
public class FiltroControlaSession implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroControlaSession() {
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

		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if(sesion.getAttribute("nombre") != (null))
		{
//			((HttpServletResponse)response).sendRedirect("/Practica4/login.html");
			String url = (String) sesion.getAttribute("url");
			((HttpServletResponse)response).sendRedirect(url);
			System.out.println("Paso por el filtro y me mando a: "+ url);
		}
		else
		{
			// pass the request along the filter chain
			System.out.println("Se filtro!");
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
