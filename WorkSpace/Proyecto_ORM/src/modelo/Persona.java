package modelo;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Entity
public class Persona {

	@Id @GeneratedValue
	private long id;
	
	private String nombre;

	private String apellido;
		
	private String domicilio;
		
	private Integer dni;
	
	private LocalDate fechaNac;
	
	private Sexo sexo;
	
	private String mail;
	
	private String pass;
	
	// Modificacion
	@Column(name = "nombreUser", unique=true)
	private String nombreUser;
	
	private Tipo_USER tipo;
	
	@OneToMany(mappedBy="owner", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private List<Ruta> misRutas;
//	
	@OneToMany(mappedBy="owner",cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private List<RutaRealizada> rutasRealizadas;
	
	// Lista de misRutas y rutasRealizadas
	/*
	   * Puede tener 0-N rutas
	   * Puede tener 0-N rutasRealizadas
	*/

	public Persona(){}
	
	public Persona(String nombre, String apellido, String domicilio, Integer dni, LocalDate fechaNac, Sexo sexo, String mail,
			String pass, String nombreUser, Tipo_USER tipo, List<Ruta> misRutas, List<RutaRealizada> rutasRealizadas)
	{
		this.nombre = nombre;
		this.apellido = apellido;
		this.domicilio = domicilio;
		this.dni = dni;
		this.fechaNac = fechaNac;
		this.sexo = sexo;
		this.mail = mail;
		this.pass = pass;
		this.nombreUser = nombreUser;
		this.tipo = tipo;
		this.misRutas = misRutas;
		this.rutasRealizadas = rutasRealizadas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	public Integer getDni() {
		return dni;
	}
	
	public void setDni(Integer dni) {
		this.dni = dni;
	}
	
	public LocalDate getFechaNac() {
		return fechaNac;
	}
	
	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}
	
	public Sexo getSexo() {
		return sexo;
	}
	
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNombreUser() {
		return nombreUser;
	}

	public void setNombreUser(String nombreUser) {
		this.nombreUser = nombreUser;
	}

	public Tipo_USER getTipo() {
		return tipo;
	}

	public void setTipo(Tipo_USER tipo) {
		this.tipo = tipo;
	}

	public List<Ruta> getMisRutas() {
		return misRutas;
	}

	public void setMisRutas(List<Ruta> misRutas) {
		this.misRutas = misRutas;
	}

	public List<RutaRealizada> getRutasRealizadas() {
		return rutasRealizadas;
	}

	public void setRutasRealizadas(List<RutaRealizada> rutasRealizadas) {
		this.rutasRealizadas = rutasRealizadas;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

  
 }