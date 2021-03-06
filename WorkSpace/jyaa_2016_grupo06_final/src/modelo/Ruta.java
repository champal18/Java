package modelo;

import java.util.Comparator;
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
	
	private Date fechaRegistro;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	private Persona owner;
	
	@OneToMany(mappedBy="ruta", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private List<RutaRealizada> registroRealizadas;
	
	@OneToMany(mappedBy="ruta", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private List<Punto> puntos;
	
	@OneToMany(mappedBy="ruta", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private List<Foto> fotos;
	
    public Ruta(){}
    
    public Ruta(String nombre, String descripcion, Privacidad privacidad, Formato formato,
    		Integer distancia, Dificultad dificultad, Actividad actividad, Integer tiempo, Date fecha, 
    		float promedio, int cantR, Persona owner)
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
    	
    	this.promedio = promedio;
    	this.cantRealizadas = cantR;
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
	
	public int compareTo(Ruta o)
	{
		return Comparators.NAME.compare(this, o);
	}
	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public static class Comparators {

        public static Comparator<Ruta> NAME = new Comparator<Ruta>() {
            @Override
            public int compare(Ruta o1, Ruta o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        };
        public static Comparator<Ruta> DISTANCIA = new Comparator<Ruta>() {
            @Override
            public int compare(Ruta o1, Ruta o2) {
            	 return o1.getDistancia() < o2.getDistancia() ? -1 : o1.getDistancia() == o2.getDistancia() ? 0 : 1;
            }
        };
        public static Comparator<Ruta> PUNTUACION = new Comparator<Ruta>() {
            @Override
            public int compare(Ruta o1, Ruta o2) {
            	 return o1.getPromedio() < o2.getPromedio() ? -1 : o1.getPromedio() == o2.getPromedio() ? 0 : 1;
            }
        };
        public static Comparator<Ruta> DIFICULTAD = new Comparator<Ruta>() {
            @Override
            public int compare(Ruta o1, Ruta o2) 
            {
            	if(o1.getDificultad().compareTo(o2.getDificultad()) < 0)
            		return -1;
            	else
            	{
            		if(o1.getDificultad() == o2.getDificultad())
            			return 0;
            		else
            			return 1;
            	}
            }
        };
        public static Comparator<Ruta> CANTREALIZACIONES = new Comparator<Ruta>() {
            @Override
            public int compare(Ruta o1, Ruta o2) {
            	 return o1.getCantRealizadas() < o2.getCantRealizadas() ? -1 : o1.getCantRealizadas() == o2.getCantRealizadas() ? 0 : 1;
            }
        };
    }

}