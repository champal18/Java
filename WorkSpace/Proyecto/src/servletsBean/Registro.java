package servletsBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Ruta;
import modelo.RutaRealizada;

/**
 * Servlet implementation class Registro
 */
@WebServlet("/Registro")
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static HashMap<String,String> usuariosRegistrados = new HashMap<String,String>();   
	
	//public static HashMap<String,String> usuariosRegistrados = new HashMap<String,String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String nombreUser = request.getParameter("txtNombreUser");
		String nombre = request.getParameter("txtNombre");
		String apellido = request.getParameter("txtApellido");
		String dni = request.getParameter("txtDni");
		String email = request.getParameter("txtEmail");
		String domicilio = request.getParameter("txtDomicilio");
		String fechaNacimiento = request.getParameter("txtFechaNacimiento");// pasarlo a localdate?
		String sexo = request.getParameter("txtSexo");// pasarlo a enumerativo
		String password = UUID.randomUUID().toString().substring(0, 6);
		
		response.setContentType("text/html"); 
		PrintWriter out=response.getWriter();
		
		// El usuario se registra sin rutas
		RutaRealizada rutaRealizada = new RutaRealizada();
		ArrayList<Ruta> lista = new ArrayList<>();
		
		//Persona p = new Persona(nombre, apellido, domicilio, dni, fechaNacimiento, sexo, email, password, nombreUser, Tipo_USER.Usuario, lista, rutaRealizada);
		//usuariosRegistrados.put(email, password);
		
		/*
		 * 
		 	// Ligo objeto "usr" y "usuariosRegistrados"al alcance de aplicacion 
			ServletContext sc=this.getServletConfig().getServletContext();
			sc.setAttribute(usr.getEmail(),usr);
			sc.setAttribute("usuariosRegistrados",usuariosRegistrados);
	
			response.sendRedirect("registroExitoso.html");
		 * 
		 */
		
		response.sendRedirect("registro2.xhtml");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}