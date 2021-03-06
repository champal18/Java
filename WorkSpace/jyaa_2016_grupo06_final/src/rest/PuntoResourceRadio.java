package rest;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import rest.Punto;

@Path("/rutas/2")
public class PuntoResourceRadio 
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	long id;
	
	PuntoServiceRadio puntoService;
	
	public PuntoResourceRadio(UriInfo uriInfo, Request request, long id)
	{
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		puntoService = new PuntoServiceRadio();
	}
	
	public PuntoResourceRadio()
	{
		puntoService = new PuntoServiceRadio();
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
		
		Punto puntoNuevo = new Punto(latitud, longitud);
		puntoService.createPunto(puntoNuevo);
	}
	
	// Eliminar todos los puntos
	@DELETE
	public void deleteAllPuntos()
	{
		puntoService.deleteAllPuntos();
	}
	
}
