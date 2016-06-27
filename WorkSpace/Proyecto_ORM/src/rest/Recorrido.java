package rest;

import java.util.ArrayList;

import modelo.Punto;

public class Recorrido 
{
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
	
	public void addPunto(Punto p)
	{
		this.puntos.add(p);
	}

}