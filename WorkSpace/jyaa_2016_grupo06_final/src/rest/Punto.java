package rest;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.Ruta;

@Entity
@XmlRootElement
public class Punto 
{
	
	@Id @GeneratedValue
	private long id;
	
	private long indice;
	
	@Transient
	public static long idstatic;
	
	public double lat;
	public double lon;
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
	private Ruta ruta;
	
	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public Punto(){};
	
	public Punto(double latitud, double longitud)
	{
		idstatic++;
		this.indice = idstatic;
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
		return indice;
	}

	public void setId(long id) {
		this.indice = id;
	}

	public long getIndice() {
		return indice;
	}

	public void setIndice(long indice) {
		this.indice = indice;
	}
	

}
