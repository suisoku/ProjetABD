package scenario;

import java.sql.SQLException;
import java.util.Date;

import bd_layer.queryModel.QueryMethods;
import dataInterfaces.Client;
import dataInterfaces.Image;

public class ClientBot extends Thread{
	

	public void run(){
		// Getting a connection to the DB
		QueryMethods q =  new QueryMethods();
		
		// credentials
		String mail =  "jgalsworthy1@wikia.com";
		String mdp =  "Mélodie";
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
			
			Image image1 = new Image("/skur/skur1.jpg", idClient, "2K", false, new Date(), 0);
			
			Image image2 = new Image("/skur/skur2.jpg", idClient, "4K", false, new Date(), 0);
			Image image3 = new Image("/skur/skur3.jpg", idClient, "4K", false, new Date(), 0);
			Image image4 = new Image("/skur/skur4.jpg", idClient, "8K", true, new Date(), 0);
			Image image5 = new Image("/skur/skur5.jpg", idClient, "2K", true, new Date(), 0);

			try {q.addImage(image1);  
				 q.addImage(image2);  
				 q.addImage(image3);
				 q.addImage(image4); 
				 q.addImage(image5);} 
			catch (NumberFormatException | SQLException e) {e.printStackTrace();}
			
			System.out.println("Creation image :" + image1.getChemin() + "Partage ? " + image1.isPartager());
			System.out.println("Creation image :" + image2.getChemin() + "Partage ? " + image2.isPartager());
			System.out.println("Creation image :" + image3.getChemin() + "Partage ? " + image3.isPartager());
			System.out.println("Creation image :" + image4.getChemin() + "Partage ? " + image4.isPartager());
			System.out.println("Creation image :" + image5.getChemin() + "Partage ? " + image5.isPartager());
			System.out.println("");
		}
		else {
			System.out.println("ERREUR Connection client");
		}
	}
}
