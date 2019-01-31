package scenario;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import bd_layer.queryModel.QueryMethods;
import dataInterfaces.Client;
import dataInterfaces.Image;
import dataInterfaces.Impression;
import dataInterfaces.Photo;
import dataInterfaces.PhotoImpression;
import dataInterfaces.TypeImpression;

public class ClientBot2 extends Thread{
	

	public void run(){
		// Getting a connection to the DB
		QueryMethods q =  new QueryMethods();
		
		// credentials
		String mail =  "lbaddow2@si.edu";
		String mdp =  "Thérèse";
		int idClient = 0;
		
		//authentification
		try {idClient = q.authentification("client", mail, mdp);}
		catch (NumberFormatException | SQLException e) {e.printStackTrace();}
		
		if(idClient != 0) {
			System.out.println("THREAD CLIENT " + mail);
			int idImpression = 0;
			Client client = null;
			try {client = q.getClient(idClient);}  // Getting the client from db
			catch (NumberFormatException | SQLException e) {e.printStackTrace();}
			
			
			//STEP1  add images
			Image image1 = new Image("/cookie/lol1.jpg", idClient, "2K", false, new Date(), 0);
			Image image2 = new Image("/cookie/lol2.jpg", idClient, "4K", true, new Date(), 0);


			try {q.addImage(image1);  
				 q.addImage(image2);  
			}
			catch (NumberFormatException | SQLException e) {e.printStackTrace();}
			
			/** STEP 2  Create photos from image **/
			Photo photo1 = new Photo("/skur/skur4.jpg", "Photo avec un chapeau", "photoshop");
			Photo photo2 = new Photo("/skur/skur4.jpg", "YeuxRouge corrige", "FB filter");


			try {photo1.setIdPhoto( q.addPhoto(photo1));  
				 photo2.setIdPhoto( q.addPhoto(photo2));
			}
			catch (NumberFormatException | SQLException e) {e.printStackTrace();}
			
	
			
			/** STEP 2bis Il faut Nom de l'impression **/
			String nomImpression = "Road Trip Tirage 2";
			
			
			/** STEP 3 Il faut Nom de l'impression **/
			HashMap<String, Object> attrs =  new HashMap<String , Object>();
			attrs.put("IDPRODUIT", "4");
			attrs.put("FORMATIMPRESSION", "A5");
			
			TypeImpression typeImp = new TypeImpression("TIRAGE", attrs);
			Impression impression = new Impression(0, idClient, nomImpression, null);
			impression.setTypeImpression(typeImp);
			//now we have a complete Impression
			
			/** STEP 4 Push to DB the impression**/
			try {idImpression = q.addImpression(impression);}
			catch (NumberFormatException | SQLException e) {e.printStackTrace();}			

			/** STEP 5 Wrap PhotoImpressions**/
			
			try {
				q.addPhotoImpressionTirage(new PhotoImpression(idImpression, photo1, "SansBord", 12));
				q.addPhotoImpressionTirage(new PhotoImpression(idImpression, photo2, "ContourBlanc, solid:3px" , 5));
			}
			catch (NumberFormatException | SQLException e) {e.printStackTrace();}			
			
			System.out.println("Impression a ete ajoutee avec succees");
		}
		else {
			System.out.println("ERREUR Connection client");
		}
	}
}
