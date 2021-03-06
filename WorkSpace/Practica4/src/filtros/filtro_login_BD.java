package filtros;

import java.io.IOException;
import java.time.LocalDateTime;

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

import BD.Acceso;
import BD.AccesoDAO;
import BD.BD_JYAA;

/**
 * Servlet Filter implementation class login_BD
 */
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST, 
		DispatcherType.FORWARD
		},
		urlPatterns ={"/security/listaBD"})
public class filtro_login_BD implements Filter {

    /**
     * Default constructor. 
     */
    public filtro_login_BD() {
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
		
		boolean ok = false;
		
		HttpServletRequest rqst = (HttpServletRequest) request;
		HttpServletResponse rsp = (HttpServletResponse) response;
		
		// Creo la tabla si no existe
    	BD_JYAA bd = new BD_JYAA();
    	if(!bd.crearTabla())
    	{
//    		rsp.sendRedirect("http://localhost:8080/Practica4/login_JDBC.html");
    		ok = true;
    		RequestDispatcher disp = request.getRequestDispatcher("/login_JDBC.html");
			disp.forward(request, response);
    	}
    	
    	// Agrego acceso
    	AccesoDAO aD = new AccesoDAO();
		LocalDateTime fechaH = LocalDateTime.now();
		String ip = rqst.getRemoteAddr();
		String recurso = rqst.getParameter("recurso");
		
		String navegador = rqst.getHeader("User-Agent");	// Navegador??? System.out.println(navegador);
		
		Acceso access = new Acceso(fechaH, ip, recurso, "Chrome");
		if((!aD.guardarAcceso(access)) && !ok)
			{
//				rsp.sendRedirect("http://localhost:8080/Practica4/login_JDBC.html");
				RequestDispatcher disp = request.getRequestDispatcher("/login_JDBC.html");
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
