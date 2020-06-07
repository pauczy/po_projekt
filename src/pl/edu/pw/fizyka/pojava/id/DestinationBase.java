package pl.edu.pw.fizyka.pojava.id;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DestinationBase {

	public static void main(String[] args) throws SQLException {
		
		Connection conn = null; 
		try {
			conn = DriverManager.getConnection("jdbc:mysql://db4free.net/destinations", "poprojekt", "haslojava");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS `destinations`;");
			stmt.executeUpdate("CREATE TABLE `destinations` ("+
					  "`Id` int(6) unsigned NOT NULL auto_increment,"+
					  "`name` varchar(50),"+
					  "`distance` float,"+ //distance to earth in light years
					  "PRIMARY KEY  (`Id`)"+
					") ;"); 
			
			stmt.executeUpdate("INSERT INTO `destinations` (`name`,`distance`) VALUES ('Księżyc', (4.063*POWER(10, -8)));");
			stmt.executeUpdate("INSERT INTO `destinations` (`name`,`distance`) VALUES ('Proxima Centauri', 4.243);");
			stmt.executeUpdate("INSERT INTO `destinations` (`name`,`distance`) VALUES ('Mars', 1.886*POWER(10, -5));");
			stmt.executeUpdate("INSERT INTO `destinations` (`name`,`distance`) VALUES ('Polaris', 430);");
			stmt.executeUpdate("INSERT INTO `destinations` (`name`,`distance`) VALUES ('Słońce', 1.58*POWER(10, -5));");
			stmt.executeUpdate("INSERT INTO `destinations` (`name`,`distance`) VALUES ('Galaktyka Andromedy', 2.537*POWER(10, 6));");
			
			
			
		}finally {
			if (conn!= null){
				conn.close();
			}
		}
		System.out.println("Utworzono tabele danych" );
	}
		
	
	

}
