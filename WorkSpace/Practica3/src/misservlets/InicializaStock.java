package misservlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class InicializaStock
 *
 */
@WebListener
public class InicializaStock implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public InicializaStock() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  
    { 
         // TODO Auto-generated method stub
    	ServletContext context = arg0.getServletContext();
//    	InputStream input = context.getResourceAsStream(context.getInitParameter("stock"));
//    	BufferedReader reader = input.
    			
    	InputStream in;
		try 
		{
			in = new FileInputStream(new File(context.getInitParameter("stock")));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        StringBuilder out = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null)
	        {
	            out.append(line);
	        }
	        System.out.println(out.toString());   //Prints the string content read from input stream
	        reader.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	
    	
//    	{
//    		int aux = i+1;
//    		Golosina g = new Golosina(nom, cant, p);
//    		HttpSession sesion = getSession
//    		sesion.setAttribute("g"+aux, g);
//    	}
    }
	
}
