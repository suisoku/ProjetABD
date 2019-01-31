package bd_layer.queryModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dataInterfaces.*;

public class TestQuery1 {

	/** Testing the queries **/
	public static void main(String[] args) throws SQLException {
		QueryMethods cq = new QueryMethods();

		System.out.println("--------TEST CLIENT------------");
		Client c = cq.getClient(4);

		System.out.println(c.getNom() + " | " + c.getPrenom() + " | " + c.getTelephone() + " | ");

		for (Adresse a : c.getAdressList()) {
			System.out.println(a.getNomAdresse() + " | " + a.getAdresse());
		}
		for (CodePromo cp : c.getCodeList()) {
			System.out.println(cp.getCode() + " | " + cp.getReduction() + " | " + cp.isUsed());
		}
		System.out.println("------------------------------");

		/**
		 * System.out.println("--------TEST Commande------------"); Commande cm =
		 * cq.getClientCommande(4).get(0);
		 * 
		 * System.out.println(cm.getIdClient() +" | "+ cm.getMontant() +" | "+
		 * cm.getStatut() +" | " + cm.getModeLivraison());
		 * 
		 * for(CommandeImpression ci : cm.getCommandeImpressions() ) {
		 * System.out.println(ci.impression.getIdImpression() +" | "+
		 * ci.impression.getNom() +" | " + ci.quantite); }
		 * 
		 * System.out.println("------------------------------");
		 **/

		System.out.println("--------TEST Impression photos ------------");
		Impression i = cq.getClientImpression(4).get(0);
		System.out.println(i.getIdImpression() + " | " + i.getIdClient() + " | " + i.getNom() + " | ");
		System.out.println(i.getTypeImpression().type);

		for (Object elem : i.getTypeImpression().attributes.entrySet()) {
			System.out.println(elem.toString());
		}
		for (PhotoImpression pi : i.getPhotoImpression()) {
			System.out
					.println(pi.photo.getIdPhoto() + " | " + pi.photo.getChemin() + " | " + pi.photo.getCommentaire());
		}

		System.out.println("------------------------------");
<<<<<<< HEAD

		System.out.println("-------------ALL IMAGE-----------------");
		try {
			ArrayList<Image> images = cq.getDBImages();

			for (int i1 = 0; i1 < images.size(); i1++) {
				String path = images.get(i1).getChemin();
				System.out.println(path);
			}
			;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("------------------------------");

=======
		
		System.out.println("-------------GET PRICE-----------------");
		System.out.println(cq.prixImpression(cq.getImpression(1)));
		System.out.println("------------------------------");
		
		System.out.println("-------------Get photos image-----------------");
		cq.getPhotosImage("/usr/tmp/1.jpg").forEach(e -> System.out.println(e.getChemin() +" | "+ e.getTypeRetouche()));;
		System.out.println("------------------------------");
		
		System.out.println(TypeImpression.TypesI.AGENDA.name());
>>>>>>> ad9b2b85dbbabdd69ccd5f71d191b2b319fdb9a9
	}
}