package Modelo;

import java.time.LocalDate;

public class RutaRealizada 
{
	private int idRuta;
	private int valoracion;
	private LocalDate fechaRealizada;
	
	public RutaRealizada(int id, int valoracion, LocalDate fecha)
	{
		this.idRuta = id;
		this.valoracion = valoracion;
		this.fechaRealizada = fecha;
	}
	
	public int getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}
	public int getValoracion() {
		return valoracion;
	}
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}
	public LocalDate getFechaRealizada() {
		return fechaRealizada;
	}
	public void setFechaRealizada(LocalDate fechaRealizada) {
		this.fechaRealizada = fechaRealizada;
	}

}
