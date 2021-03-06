package rest;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/rutas/3")
public class MostrarPuntoResource 
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	long id;
	
	PuntoService puntoService;
	
	public MostrarPuntoResource(UriInfo uriInfo, Request request, long id)
	{
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		puntoService = new PuntoService();
	}
	
	public MostrarPuntoResource()
	{
		puntoService = new PuntoService();
	}
	
	// Recuperar todos los puntos del recorrido
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Punto> getPuntos()
	{
		return puntoService.getPuntoAsList();
	}
}
