package modelo;

import javax.persistence.*;

@Entity
//@Table(name="ACTIVIDADES")

public class Actividad
{
	@Id @GeneratedValue
	private long id;
	
	private String nombre;

	private Boolean habilitada;
	
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