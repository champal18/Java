package test;

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
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS actividades(id int NOT NULL,"
					+ " nombre varchar(20), habilitada varchar(20), dni int, tel_particular varchar(15),"
					+ " tel_laboral varchar(15), direccion varchar(50), ciudad varchar(50), mail varchar(50),"
					+ " dependencia varchar(50), fecha date, tipo varchar(10), ioma varchar(15), cp int,"
					+ " categoria int(4), PRIMARY KEY (LEGAJO), UNIQUE (LEGAJO))");
			create.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void borrarTodo()
	{
		Connection con;
		try {
			con = getConnection();
			PreparedStatement borrar = con.prepareStatement("DROP TABLE `actividad`, `persona`, `recorrido`, `ruta`, `rutarealizada`;");
			borrar.executeUpdate();
			System.out.println("Se borraron todas las tablas");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
