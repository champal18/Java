package rest;

import rest.Punto;

public enum puntoBuscado 
{
	instance;
	
	private Punto puntoBuscado;

	public Punto getPuntoBuscado() {
		return puntoBuscado;
	}

	public void setPuntoBuscado(Punto puntoBuscado) {
		this.puntoBuscado = puntoBuscado;
	}
	
	public void clear()
	{
		this.puntoBuscado = null;
	}
}
