package controller;

import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;

import modelo.Foto;
import modeloDAO.FotoDAO;

public class FotoBean // alta agregarlo al FaceConfig y demas
{
	
	public void guardarFoto()
	{
		Foto foto = new Foto();
//		String path = "C:/Users/juanpablo/Desktop/1.jpg";
//		File file = new File(path);
//		
//		byte[] bytes = null;
//		try {
//			FileInputStream fileInputStream = new FileInputStream(file);
//			bytes = IOUtils.toByteArray(fileInputStream);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		foto.setImg(bytes);
//		foto.setRuta(null);
//		
//		FotoDAO fDao = new FotoDAO();
//		fDao.guardarFoto(foto);
		
		FotoDAO fDao = new FotoDAO();
		foto = fDao.recuperarFoto((long)1);
		byte[] byteLeidos = foto.getImg();
		try{
			IOUtils.write(byteLeidos, new FileOutputStream("C:/Users/juanpablo/Desktop/2.jpg")); 
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
	}

}
