package modelo;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

@Entity
public class Ruta 
{
	@Id @GeneratedValue
	private long id;
	
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
	
	@OneToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
	private Recorrido recorrido;
	
	private Formato formato;
	
	private Integer distancia;
	
	private Dificultad dificultad;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	private Actividad actividad;
	
	private Integer tiempo;
	
	private LocalDate fecha;
	
	private String fotos;
	
	private float promedio;
	
	private int cantRealizadas;
	
	@OneToMany(mappedBy="ruta", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private List<RutaRealizada> registroRealizadas;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private Persona owner;
	
    public Ruta(){}
    
    public Ruta(String nombre, String descripcion, Privacidad privacidad, Recorrido recorrido, Formato formato,
    		Integer distancia, Dificultad dificultad, Actividad actividad, Integer tiempo, LocalDate fecha, String fotos, 
    		float promedio, int cantR, List<RutaRealizada> registroR, Persona owner)
    {
    	this.nombre = nombre;
    	this.descripcion = descripcion;
    	this.privacidad = privacidad;
    	this.recorrido = recorrido;
    	this.formato = formato;
    	this.distancia = distancia;
    	this.dificultad = dificultad;
    	this.actividad = actividad;
    	this.tiempo = tiempo;
    	this.fecha = fecha;
    	this.fotos = fotos;
    	
    	this.promedio = promedio;
    	this.cantRealizadas = cantR;
    	this.registroRealizadas = registroR;
    	this.owner = owner;
    }
	
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

	public List<RutaRealizada> getRegistroRealizadas() {
		return registroRealizadas;
	}

	public void setRegistroRealizadas(List<RutaRealizada> registroRealizadas) {
		this.registroRealizadas = registroRealizadas;
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

}