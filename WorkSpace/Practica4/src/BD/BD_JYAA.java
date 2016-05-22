package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BD_JYAA
{
	public Connection getConnection() throws Exception
	{
		try
		{
			String driver = "com.mysql.jdbc.Driver";
			String host = "jdbc:mysql://localhost/jyaa";	// Se creo manualmente desde el XAMPP
			String username = "root";
			String password = "";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(host, username, password);
			return conn;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}
	
	public boolean crearTabla()
	{
		Connection con;
		try {
			con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS accesos(fechaH date,"
					+ " ip varchar(15), recurso varchar(20), navegador varchar(20), PRIMARY KEY (FECHAH)");
			create.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
