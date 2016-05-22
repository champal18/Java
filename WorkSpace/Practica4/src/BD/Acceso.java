package BD;

import java.time.LocalDateTime;

public class Acceso 
{
	public LocalDateTime fecha_hora;
	public String ip;
	public String recurso;
	public String navegador;
	
	public Acceso(LocalDateTime fh, String ip, String rec, String nav)
	{
		this.fecha_hora = fh;
		this.ip = ip;
		this.recurso = rec;
		this.navegador = nav;
	}

}
