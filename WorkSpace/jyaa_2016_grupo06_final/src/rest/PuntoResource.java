package rest;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/rutas/1")
public class PuntoResource 
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	long id;
	
	PuntoService puntoService;
	
	public PuntoResource(UriInfo uriInfo, Request request, long id)
	{
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		puntoService = new PuntoService();
	}
	
	public PuntoResource()
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
	
	// Agregar un punto al recorrido
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postPunto(@FormParam("lon") String lon, @FormParam("lat") String lat)
	throws IOException {
		double latitud = Double.parseDouble(lat);
		double longitud = Double.parseDouble(lon);
		
		Punto puntoNuevo = new Punto(latitud, longitud); //new Punto(punto.getLongitud(),punto.getLatitud());
		puntoService.createPunto(puntoNuevo);
	}

//	// Eliminar un punto
	@Path("{id}")
	@DELETE
	public void deletePunto(@FormParam("id")long id)
	{
		puntoService.deletePunto(id);
	}
	
	// Eliminar todos los puntos
	@DELETE
	public void deleteAllPuntos()
	{
		puntoService.deleteAllPuntos();
	}
	
}
