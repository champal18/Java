package modelo;

public class Punto 
{
	private long latitud;
	private long longitud;
	
	public Punto(long latitud, long longitud)
	{
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	public long getLatitud() {
		return latitud;
	}
	public void setLatitud(long latitud) {
		this.latitud = latitud;
	}
	public long getLongitud() {
		return longitud;
	}
	public void setLongitud(long longitud) {
		this.longitud = longitud;
	}
	
	

}