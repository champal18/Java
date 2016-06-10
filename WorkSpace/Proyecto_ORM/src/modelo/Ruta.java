package modelo;

import java.time.LocalDate;

public class Ruta {

	long id;
	
	/*
	 * Puede ser de 1 actividad
	 * Ver que onda los enumerativos
	 * Puede tener 1 recorrido
	 * Puede ser rutaRealizada 0-N veces
	 * Puede pertenecer a 1 usuario
	 */
	
	private String nombre;
	
	private String descripcion;
	
	private Privacidad privacidad;
	
	private Recorrido recorrido;
	
	private Formato formato;
	
	private Integer distancia;
	
	private Dificultad dificultad;
	
	private Actividad actividad;
	
	private Integer tiempo;
	
	private LocalDate fecha;
	
	private String fotos;
	
	private float promedio;
	
	private int cantRealizadas;

    public Ruta(){}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Privacidad getPrivacidad() {
		return privacidad;
	}
	
	public void setPrivacidad(Privacidad privacidad) {
		this.privacidad = privacidad;
	}
	
	public Recorrido getRecorrido() {
		return recorrido;
	}
	
	public void setRecorrido(Recorrido recorrido) {
		this.recorrido = recorrido;
	}
	
	public Formato getFormato() {
		return formato;
	}
	
	public void setFormato(Formato formato) {
		this.formato = formato;
	}
	
	public Integer getDistancia() {
		return distancia;
	}
	
	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
	
	public Dificultad getDificultad() {
		return dificultad;
	}
	
	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}
	
	public Actividad getActividad() {
		return actividad;
	}
	
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	
	public Integer getTiempo() {
		return tiempo;
	}
	
	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public String getFotos() {
		return fotos;
	}
	
	public void setFotos(String fotos) {
		this.fotos = fotos;
	}

	public float getPromedio() {
		return promedio;
	}

	public void setPromedio(float promedio) {
		this.promedio = promedio;
	}

	public int getCantRealizadas() {
		return cantRealizadas;
	}

	public void setCantRealizadas(int cantRealizadas) {
		this.cantRealizadas = cantRealizadas;
	}

}