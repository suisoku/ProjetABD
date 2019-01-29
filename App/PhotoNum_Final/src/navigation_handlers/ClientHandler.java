package navigation_handlers;

import java.sql.SQLException;
import java.util.Date;

import application.LectureClavier;
import bd_layer.queryModel.QueryMethods;
import dataInterfaces.Adresse;
import dataInterfaces.Client;
import dataInterfaces.CodePromo;
import dataInterfaces.Image;

public class ClientHandler {

	private static QueryMethods client_queries = new QueryMethods();

	public static void add() {

		Client client = getUserInput();

		try {
			client_queries.addClient(client);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void addImage() {
		

		Image image = getImageInput(5);

		try {
			client_queries.addImage(image);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void delete() {
		
		System.out.println("Saisir le chemin de l'image");
		String chemin = LectureClavier.lireChaine();
		
		try {
			client_queries.deleteImage(chemin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public static void showDetails() {
		try {
			Client client = client_queries.getClient(10);

			System.out.println(client.getNom() + " | " + client.getPrenom() + " | " + client.getTelephone() + " | "
					+ client.getMail());

			for (Adresse a : client.getAdressList()) {
				System.out.println(a.getNomAdresse() + " | " + a.getAdresse());
			}
			for (CodePromo cp : client.getCodeList()) {
				System.out.println(cp.getCode() + " | " + cp.getReduction() + " | " + cp.isUsed());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Client getUserInput() {
		String nom, prenom, mail, mdp, telephone;

		System.out.println("Entrer nom user : ");
		nom = LectureClavier.lireChaine();

		System.out.println("Entrer prénom user : ");
		prenom = LectureClavier.lireChaine();

		System.out.println("Entrer mail user : ");
		mail = LectureClavier.lireChaine();

		System.out.println("Entrer mdp user : ");
		mdp = LectureClavier.lireChaine();

		System.out.println("Entrer téléphone user : ");
		telephone = LectureClavier.lireChaine();

		return new Client(mail, nom, prenom, mdp, telephone);
	}
	
	public static Image getImageInput(int idClient) {
		
		
		System.out.println("Entrer le chemin de l'image: ");
		String chemin = LectureClavier.lireChaine();

		System.out.println("Entrer la résolution de l'image: ");
		String resolution = LectureClavier.lireChaine();

		System.out.println("Entrer mail user : ");
		boolean partage = LectureClavier.lireOuiNon("Image partagée: " );

		Date dateUtilisation = new Date();
		
		return new Image(chemin, idClient, resolution, partage, dateUtilisation,0);
		
	}
}
