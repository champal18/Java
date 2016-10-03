package misServlets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Foto;
import modeloDAO.FotoDAO;

/**
 * Servlet implementation class DownloadImage
 */
@WebServlet("/DownloadImage/*")
public class DownloadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FotoDAO fDao = new FotoDAO();
		byte[] img;
		
		HttpSession sesion = request.getSession(true);
		Long idRuta = (Long) sesion.getAttribute("idRuta");
		int imageId = Integer.parseInt(String.valueOf(request.getPathInfo().substring(1)));	// Gets string that goes after "/images/".
		
		List<Foto> f = fDao.recuperarFotos(idRuta);
		if(f != null)	// Si posee fotos
		{
			if(f.size() < imageId)	// Si no posee imagen para el id indicado en el path envio una imagen por defecto
			{
				response.setContentType("image/png");
				ServletContext context = request.getServletContext();
				InputStream resourceContent = context.getResourceAsStream("/WEB-INF/no_image.png");
				BufferedImage bi = ImageIO.read(resourceContent);
				OutputStream out = response.getOutputStream();
				ImageIO.write(bi, "png", out);
				out.close();
			}
			else	// La imagen existe y es enviada
			{
				img = f.get(imageId-1).getImg();
				response.setContentType("image/png");
				response.getOutputStream().write(img);
			}
		}
		else
		{
			response.setContentType("image/png");
			ServletContext context = request.getServletContext();
			InputStream resourceContent = context.getResourceAsStream("/WEB-INF/no_image.png");
			BufferedImage bi = ImageIO.read(resourceContent);
			OutputStream out = response.getOutputStream();
			ImageIO.write(bi, "png", out);
			out.close();
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