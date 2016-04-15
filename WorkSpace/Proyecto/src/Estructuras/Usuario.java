package Estructuras;

public class Usuario 
{
	private String nombre_usuario;
	private String apellido;
	private String nombre;
	private String dni;
	private String domicilio;
	private String fecha_nacimiento;
	private String email;
	private Genero gen;
	private String clave;
	
	
	public Usuario(String nom_usuario, String apellido, String nombre, String dni, String domicilio, String fecha, String email, String genero, String clave)
	{
		this.nombre = nom_usuario;
		this.apellido = apellido;
		this.nombre = nombre;
		this.dni = dni;
		this.domicilio = domicilio;
		this.fecha_nacimiento = fecha;
		this.email = email;
		this.clave = clave;
		if(genero.equals("femenino"))
			this.setGen(Genero.FEMENINO);
		else
			this.setGen(Genero.MASCULINO);
	}
	
	public String getNombre_usuario()
	{
		return nombre_usuario;
	}
	
	public void setNombre_usuario(String nombre_usuario)
	{
		this.nombre_usuario = nombre_usuario;
	}
	
	public String getApellido()
	{
		return apellido;
	}
	
	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	
	public String getDni()
	{
		return dni;
	}
	
	public void setDni(String dni)
	{
		this.dni = dni;
	}
	
	public String getDomicilio()
	{
		return domicilio;
	}
	
	public void setDomicilio(String domicilio)
	{
		this.domicilio = domicilio;
	}
	
	public String getFecha_nacimiento()
	{
		return fecha_nacimiento;
	}
	
	public void setFecha_nacimiento(String fecha_nacimiento)
	{
		this.fecha_nacimiento = fecha_nacimiento;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}

	public Genero getGen()
	{
		return gen;
	}

	public void setGen(Genero gen)
	{
		this.gen = gen;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
}