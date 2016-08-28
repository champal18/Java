package modelo;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

import rest.Punto;

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
	
	@Column(length = 1023)
	private String descripcion;
	
	private Privacidad privacidad;
	
	private Formato formato;
	
	private Integer distancia;
	
	private Dificultad dificultad;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	private Actividad actividad;
	
	private Integer tiempo;
	
	private Date fecha;
	
	private float promedio;
	
	private int cantRealizadas;
	
	@OneToMany(mappedBy="ruta", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private List<RutaRealizada> registroRealizadas;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	private Persona owner;
	
	@OneToMany(mappedBy="ruta", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private List<Foto> fotos;
	
	@OneToMany(mappedBy="ruta", cascade = {CascadeType.MERGE,CascadeType.REMOVE, CascadeType.REFRESH})
	private List<Punto> Puntos;
	
    public Ruta(){}
    
    public Ruta(String nombre, String descripcion, Privacidad privacidad, Formato formato,
    		Integer distancia, Dificultad dificultad, Actividad actividad, Integer tiempo, Date fecha, List<Foto> fotos, 
    		float promedio, int cantR, List<RutaRealizada> registroR, Persona owner)
    {
    	this.nombre = nombre;
    	this.descripcion = descripcion;
    	this.privacidad = privacidad;
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
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public List<Foto> getFotos() {
		return fotos;
	}
	
	public void setFotos(List<Foto> fotos) {
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