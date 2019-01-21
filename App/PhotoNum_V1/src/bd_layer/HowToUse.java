package bd_layer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class HowToUse {

	/** Credentials are editable from BD.properties file **/
	public static void main(String[] args) throws SQLException {
		
		// To get a connection instance :
		Connection con = ConnectionBD.getConnection();
		
		// getData returns a 2D object list of all elements of a given query
		ArrayList<ArrayList<Object>> wow = ConnectionBD.getData(con, "select * from client2");
		System.out.println("size " + wow.size());
		
		// Example of iteration
		for(ArrayList<Object> r : wow) {
			for(Object elem : r ) {
				 System.out.print("|" + elem.toString());
			}
			System.out.println();
		}
	}
}
