package rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Punto 
{
	private static long idstatic;
	public long id;
	public double lat;
	public double lon;
	
	public Punto(double latitud, double longitud)
	{
		idstatic++;
		this.id = idstatic;
		this.lat = latitud;
		this.lon = longitud;
	}
	
	public double getLatitud() {
		return lat;
	}
	public void setLatitud(double latitud) {
		this.lat = latitud;
	}
	public double getLongitud() {
		return lon;
	}
	public void setLongitud(double longitud) {
		this.lon = longitud;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

}
