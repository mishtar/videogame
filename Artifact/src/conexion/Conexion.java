package conexion;

import java.sql.*;


public class Conexion {
	
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	public static void Conectar(){
		try {

			 Class.forName("com.mysql.jdbc.Driver");
	            String connectionUrl = "jdbc:mysql://gamedevelopment2.c7vje7m5rrvl.us-west-2.rds.amazonaws.com/Juego?" + 
	                                   "user=admin&password=abcd1234.";
	             conn = DriverManager.getConnection(connectionUrl);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
	
	public static void EjecutarSQL(String Sentencia){
		try {

			    stmt = null;
	            
	            //SQL query command
	            String SQL = "SELECT * FROM Products";
	            stmt = conn.createStatement();
	            rs = stmt.executeQuery(SQL);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
	
	public static ResultSet EjecutarSQLResultSet(String Sentencia){
		try {

			  stmt = null;
	            
	            //SQL query command
	            String SQL = "SELECT * FROM Products";
	            stmt = conn.createStatement();
	            rs = stmt.executeQuery(SQL);
	            
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		return rs;
	}
	
}
