package modelo;

import javax.persistence.*;

@Entity

public class Actividad
{
	@Id @GeneratedValue
	private long id;
	
	@Column(name = "nombre", unique=true)
	private String nombre;

	private Boolean habilitada;
	
	// La actividad puede pertenecer a 0 o n rutas
	
	public Actividad(){}
    
	public String getNombre()
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Boolean getHabilitada() 
	{
		return this.habilitada;
	}

	public void setHabilitada(Boolean habilitada) 
	{
		this.habilitada = habilitada;
	}
}