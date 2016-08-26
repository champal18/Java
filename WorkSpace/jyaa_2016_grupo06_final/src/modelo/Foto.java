package modelo;

import javax.persistence.*;

@Entity
public class Foto 
{
	@Id @GeneratedValue
	private long id;
	
	@Lob
	private byte[] imagen;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	private Ruta ruta;

	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getImg() {
		return imagen;
	}

	public void setImg(byte[] img) {
		this.imagen = img;
	}

}
