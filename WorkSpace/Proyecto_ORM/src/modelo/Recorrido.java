package modelo;

import java.util.ArrayList;
import javax.persistence.*;

import rest.Punto;

@Entity
public class Recorrido 
{
	@Id @GeneratedValue
	private long id;
	private ArrayList<Punto> puntos;
	
	public Recorrido(){}
  
	public Recorrido(ArrayList<Punto> puntos)
	{
		this.puntos = puntos;
	}
	
	public ArrayList<Punto> getPuntos() 
	{
		return this.puntos;
	}

	public void setPuntos(ArrayList<Punto> array) 
	{
		this.puntos = array;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}