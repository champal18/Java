package modelo;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
public class RutaRealizada 
{
	@Id @GeneratedValue
	private long id;
	private int valoracion;
	private LocalDate fechaRealizada;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private Ruta ruta;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private Persona owner;
	/*
	 * Puede ser una sola ruta
	 * Fue realizada por un solo usuario
	 */
	
	public RutaRealizada(){}
	
	public RutaRealizada(int valoracion, LocalDate fecha, Ruta ruta, Persona owner)
	{
		this.valoracion = valoracion;
		this.fechaRealizada = fecha;
		this.setRuta(ruta);
		this.owner = owner;
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

	public Persona getOwner() {
		return owner;
	}

	public void setOwner(Persona owner) {
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

}
