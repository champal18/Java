package modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Punto 
{
	@Id @GeneratedValue
	private long id;
	private double lat;
	private double lon;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	private Ruta ruta;
	
	public Punto(){}
	
	public Punto(double latitud, double longitud)
	{
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
