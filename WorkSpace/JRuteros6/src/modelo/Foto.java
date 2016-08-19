package modelo;

import javax.persistence.*;

import com.mysql.jdbc.Blob;

@Entity
public class Foto 
{
	@Id @GeneratedValue
	private long id;
	
	private Blob img;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	private Ruta ruta;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Blob getImg() {
		return img;
	}

	public void setImg(Blob img) {
		this.img = img;
	}

}
