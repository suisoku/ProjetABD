package navigation_handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import application.LectureClavier;
import bd_layer.Tuple;
import bd_layer.queryModel.QueryMethods;
import dataInterfaces.Adresse;
import dataInterfaces.Client;
import dataInterfaces.CodePromo;
import dataInterfaces.Image;
import dataInterfaces.Photo;
import navigation_handlers.core.GenericMenu;

public class ClientHandler {

	private static QueryMethods client_queries = new QueryMethods();

	public static void addUser() {

		Client client = getUserInput();

		try {
			client_queries.addClient(client);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
	
	public static void updateImageStatus() {
		GenericMenu genericMenu = new GenericMenu();

		try {
			ArrayList<Image> myImages = client_queries.getClientImages(1);

			for (int i = 0; i < myImages.size(); i++) {
				String path = myImages.get(i).getChemin();
				genericMenu.addMenuItem(i + "", path, () -> {
				Tuple values = new Tuple("partager","1");
				try {
					client_queries.updateImage(path, values);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}});
			};

			genericMenu.initMenu(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void updatePhoto() {
		System.out.println("Opération de mise à jour réussie");
	}

	public static void createImpression() {
		GenericMenu interactionTypeChoice = new GenericMenu();

		interactionTypeChoice.addMenuItem("0", "Supprimer impression", () -> {
			ImpressionHandler.delete();
		});

		interactionTypeChoice.addMenuItem("1", "Modifier impression", () -> {
			ImpressionHandler.update();
		});

		interactionTypeChoice.initMenu(false);
	}

	public static void updateImpression() {
		GenericMenu interactionTypeChoice = new GenericMenu();

		interactionTypeChoice.addMenuItem("0", "Supprimer impression", () -> {
			ImpressionHandler.delete();
		});

		interactionTypeChoice.addMenuItem("1", "Modifier impression", () -> {
			ImpressionHandler.update();
		});

		// Launching the app via the menu
		interactionTypeChoice.initMenu(false);
	}

	public static void createCommand() {
	}

	public static void addImage() {

		Image image = getImageInput(ConnexionHandler.idUser);

		try {
			client_queries.addImage(image);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void addPhoto() {
		GenericMenu genericMenu = new GenericMenu();

		try {
			ArrayList<Image> myImages = client_queries.getClientImages(1);

			for (int i = 0; i < myImages.size(); i++) {
				String path = myImages.get(i).getChemin();
				genericMenu.addMenuItem(i + "", path, () -> createPhoto(path));
			}

			genericMenu.initMenu(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createPhoto(String chemin) {

		System.out.println("Type de retouche : ");
		String retouche = LectureClavier.lireChaine();

		System.out.println("Commentaire : ");
		String commentaire = LectureClavier.lireChaine();

		Photo photo = new Photo(chemin, commentaire, retouche);
		try {
			client_queries.addPhoto(photo);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void delete() {

		GenericMenu genericMenu = new GenericMenu();

		try {
			ArrayList<Image> myImages = client_queries.getClientImages(1);

			for (int i = 0; i < myImages.size(); i++) {
				String path = myImages.get(i).getChemin();
				genericMenu.addMenuItem(i + "", path, () -> {
					try {
						client_queries.deleteImage(path);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}

			genericMenu.initMenu(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void showDetails() {
		try {
			Client client = client_queries.getClient(ConnexionHandler.idUser);
			
			System.out.println("----------------------CLIENT--------------");
			System.out.println("Nom :" + client.getNom() + " | Prenom : " + client.getPrenom() + " | Telephone : " + client.getTelephone() + " | Mail : "
					+ client.getMail());
			System.out.println("-Liste d'adresse du client --- ");
			for (Adresse a : client.getAdressList()) {
				System.out.println(a.getNomAdresse() + " | " + a.getAdresse());
			}
			System.out.println("-Liste de code promo ---- ");
			for (CodePromo cp : client.getCodeList()) {
				System.out.println(cp.getCode() + " | " + cp.getReduction() + " | " + cp.isUsed());
			}
			System.out.println("----------------------------------------");
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

		System.out.println("Entrer le chemin de l'image : ");
		String chemin = LectureClavier.lireChaine();

		System.out.println("Entrer la résolution de l'image : ");
		String resolution = LectureClavier.lireChaine();

		System.out.println("Entrer mail user : ");
		boolean partage = LectureClavier.lireOuiNon("Image partagée: ");

		Date dateUtilisation = new Date();

		return new Image(chemin, idClient, resolution, partage, dateUtilisation, 0);

	}
}
