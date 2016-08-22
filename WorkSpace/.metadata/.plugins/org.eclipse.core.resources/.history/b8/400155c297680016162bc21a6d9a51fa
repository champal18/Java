package modelo;

import java.sql.Blob;
import javax.persistence.*;

@Entity
public class Foto 
{
	@Id @GeneratedValue
	private long id;
	
//	private Blob img;
	private String img;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	private Ruta ruta;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

//	public Blob getImg() {
//		return img;
//	}
//
//	public void setImg(Blob img) {
//		this.img = img;
//	}

}
