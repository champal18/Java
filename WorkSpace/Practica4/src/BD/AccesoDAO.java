package BD;

import java.sql.Connection;
import java.sql.Statement;

public class AccesoDAO 
{
	public boolean guardarAcceso(Acceso access)
	{
		BD_JYAA bd = new BD_JYAA();
		Connection con;
		try {
			con = bd.getConnection();
			String orden = "INSERT INTO ACCESOS(fechaH,ip,recurso,navegador) VALUES ('"+access.fecha_hora+"', '"+access.ip+"',"
																					+ " '"+access.recurso+"', '"+access.navegador+"'";
			Statement sentencia = con.createStatement();
			sentencia.executeUpdate(orden);
			sentencia.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}			
	}
}
