package rest;

import java.util.*;

public enum PuntoDao 
{ 
	instance;

	private Map<Long, Punto> puntos = new HashMap<Long,Punto>();
	
	public Map<Long, Punto> getPuntos()
	{
		return puntos;
	}
}
