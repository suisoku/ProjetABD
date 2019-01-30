package scenario;

import java.sql.SQLException;

import bd_layer.queryModel.QueryMethods;
import dataInterfaces.Client;
import dataInterfaces.Image;

public class ClientBot extends Thread{
	

	public void run(){
		// Getting a connection to the DB
		QueryMethods q =  new QueryMethods();
		
		// credentials
		String mail =  "aduddle0@blogs.com";
		String mdp =  "Sélène";
		int idClient = 0;
		
		//authentification
		try {idClient = q.authentification("client", mail, mdp);}
		catch (NumberFormatException | SQLException e) {e.printStackTrace();}
		
		if(idClient != 0) {
			System.out.println("THREAD CLIENT " + mail);
			
			Client client = null;
			try {client = q.getClient(idClient);}  // Getting the client from db
			catch (NumberFormatException | SQLException e) {e.printStackTrace();}
			
			
			// add photo
			Image image1 = new Image("/skur/skur1.jpg", idClient , false );
			Image image2 = new Image("/skur/skur2.jpg", idClient , false );
			Image image3 = new Image("/skur/skur3.jpg", idClient , false );
			
			try {q.addImage(image1);  
				 q.addImage(image2);  
				 q.addImage(image3);} 
			catch (NumberFormatException | SQLException e) {e.printStackTrace();}
			
			
		}
	}
}
