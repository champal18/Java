package rest;

import java.util.ArrayList;
import java.util.List;
import rest.Punto;

public class PuntoServiceRadio 
{
	private puntoBuscado punto;
	
	public PuntoServiceRadio()
	{
		punto = puntoBuscado.instance;
	}
	
	public void createPunto(Punto p)
	{
		p.setIndice(1);
		p.setId(1);
		punto.setPuntoBuscado(p);
	}
	
	public List<Punto> getPuntoAsList()
	{
		List<Punto> puntoList = new ArrayList<Punto>();
		puntoList.add(this.punto.getPuntoBuscado());
		return puntoList;
	}
	
	public int getPuntosCount()
	{
		return 1;
	}
	
	public void deleteAllPuntos() 
	{
		punto.clear();
	}
}
