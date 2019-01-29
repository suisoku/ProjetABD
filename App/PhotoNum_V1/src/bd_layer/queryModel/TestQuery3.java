package bd_layer.queryModel;

import java.sql.SQLException;

import dataInterfaces.Admin;

public class TestQuery3 {
	public static void main(String[] args) throws SQLException {
		QueryMethods cq = new QueryMethods();
		
		System.out.println("------- TEST GET ADMIN ---------");
			Admin a = cq.getAdmin(4);
			
			System.out.println(a.getIdAdmin() + " | " + a.getNom() + " | " + a.getPrenom() );
			
			a.getAdmin_client().forEach(ah ->
					System.out.println(ah.idAdmin + " | " + ah.identifier.toString()  + " | " + ah.date.toString() )
			);
			
			a.getAdmin_commande().forEach(ah ->
				System.out.println(ah.idAdmin + " | " + ah.identifier.toString()  + " | " + ah.date.toString() )
			);
			
			a.getAdmin_image().forEach(ah ->
				System.out.println(ah.idAdmin + " | " + ah.identifier.toString()  + " | " + ah.date.toString() )
			);
			
			a.getAdmin_inventaire().forEach(ah ->
				System.out.println(ah.idAdmin + " | " + ah.identifier.toString()  + " | " + ah.date.toString() )
			);
		System.out.println("------------------------------");
		
		System.out.println("------- TEST STAT IMAGE ---------");
			cq.getStatImages().forEach(t -> System.out.println(t.toString()));
		System.out.println("------------------------------");
	}
	
	
		
}
