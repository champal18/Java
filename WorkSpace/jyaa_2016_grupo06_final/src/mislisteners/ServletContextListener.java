package mislisteners;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import servletsBean.Registro;

/**
 * Application Lifecycle Listener implementation class ServletContextListener
 *
 */
@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

    /**
     * Default constructor. 
     */
    public ServletContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
		// Recupero de los parametros de contexto una cuenta de administrador y usuario default.
    	String emailAdmin = arg0.getServletContext().getInitParameter("emailAdmin");
		String passAdmin = arg0.getServletContext().getInitParameter("passwordAdmin");
		String emailUser = arg0.getServletContext().getInitParameter("emailUserDefault");
		String passUser = arg0.getServletContext().getInitParameter("passwordUserDefault");

		// Cargo en el hashmap una cuenta de administrador y de usuario
		Registro.usuariosRegistrados.put(emailAdmin, passAdmin);
		Registro.usuariosRegistrados.put(emailUser, passUser);

    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
