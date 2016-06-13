package modelo;

import java.awt.Point;
import java.util.ArrayList;

import javax.persistence.*;

@Entity
public class Recorrido 
{
	@Id @GeneratedValue
	long id;
	private ArrayList<Point> puntos;
	
	public Recorrido(){}
  
	public Recorrido(ArrayList<Point> puntos)
	{
		this.puntos = puntos;
	}
	
	public ArrayList<Point> getPuntos() 
	{
		return this.puntos;
	}

	public void setPuntos(ArrayList<Point> array) 
	{
		this.puntos = array;
	}

}