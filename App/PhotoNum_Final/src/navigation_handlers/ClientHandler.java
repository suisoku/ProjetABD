package navigation_handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import application.LectureClavier;
import bd_layer.Tuple;
import bd_layer.queryModel.QueryMethods;
import dataInterfaces.Adresse;
import dataInterfaces.Client;
import dataInterfaces.CodePromo;
import dataInterfaces.Commande;
import dataInterfaces.CommandeImpression;
import dataInterfaces.Image;
import dataInterfaces.Impression;
import dataInterfaces.Photo;
import dataInterfaces.PhotoImpression;
import dataInterfaces.TypeImpression;
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

	public static void updateImageStatus(String path) {
		GenericMenu interactionTypeChoice = new GenericMenu();

		// Adding a menu item using a Lambda expression.
		interactionTypeChoice.addMenuItem("0", "Partager", () -> {
			Tuple values = new Tuple("partager", "1");
			try {
				client_queries.updateImage(path, values);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// Adding another menu item
		interactionTypeChoice.addMenuItem("1", "Privatiser", () -> {
			Tuple values = new Tuple("partager", "0");
			try {
				client_queries.updateImage(path, values);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// Launching the app via the menu
		interactionTypeChoice.initMenu(false);
	}

	public static void changeImageStatus() {
		GenericMenu genericMenu = new GenericMenu();

		try {
			ArrayList<Image> myImages = client_queries.getClientImages(ConnexionHandler.idUser);

			for (int i = 0; i < myImages.size(); i++) {
				String path = myImages.get(i).getChemin();
				genericMenu.addMenuItem(i + "", path, () -> updateImageStatus(path));
			}
			;

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
		Client cl = ConnexionHandler.userClient;
		AtomicReference<TypeImpression.TypesI> typeImp= new AtomicReference<TypeImpression.TypesI>();
		AtomicReference<String> nomImp= new AtomicReference<String>();
		AtomicInteger lindex= new AtomicInteger(0);
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		ArrayList<PhotoImpression> pImp = new ArrayList<PhotoImpression>();
		ArrayList<Image> images = new ArrayList<Image>();
		GenericMenu enumType = new GenericMenu();
		GenericMenu photoMenu = new GenericMenu();
		//int lindex = 0;
		
		//STEP 1 : choisir le nom de l'impression
		System.out.println("Tapez le nom de l'impression");
	    nomImp.set(LectureClavier.lireChaine());
	    
	    
	    // STEP2choisir le type de l'impression
		enumType.addMenuItem("1", "CADRE", () -> {typeImp.set(TypeImpression.TypesI.CADRE);});
		enumType.addMenuItem("2", "AGENDA", () -> {typeImp.set(TypeImpression.TypesI.AGENDA);});
		enumType.addMenuItem("3", "CALENDRIER", () -> {typeImp.set(TypeImpression.TypesI.CALENDRIER);});
		enumType.addMenuItem("4", "ALBUM", () -> {typeImp.set(TypeImpression.TypesI.ALBUM);});
		enumType.addMenuItem("5", "TIRAGE", () -> {typeImp.set(TypeImpression.TypesI.TIRAGE);});
		
		enumType.initMenu(false);
		
		//STEP 2 BIS parametrer les attributs de l'impression A faire
		HashMap<String, Object> params =  new HashMap<String, Object>();
		
		switch (typeImp.get().name()) {
		
		   case "CADRE" : 
			   System.out.println("Choisir Produit : ");
			   System.out.println("Produit 1");
			   params.put("IDPRODUIT", "1");
			   System.out.println("Mise en page souhaite : ");
			   params.put("MISEENPAGE", LectureClavier.lireChaine());
			   break;
			   
		   case "AGENDA" : 
			   System.out.println("Choisir Produit : ");
			   System.out.println("Produit 2");
			   params.put("IDPRODUIT", "2");
			   System.out.println("Type Agenda souhaite : ");
			   params.put("TYPEAGENDA", LectureClavier.lireChaine());
			   System.out.println("Modele souhaite : ");
			   params.put("MODELE", LectureClavier.lireChaine());
			   break;
			   
		   case "CALENDRIER" : 
			   System.out.println("Choisir Produit : ");
			   System.out.println("Produit 3");
			   params.put("IDPRODUIT", "3");
			   System.out.println("Type Calendrier souhaite : ");
			   params.put("TYPECALENDRIER", LectureClavier.lireChaine());
			   break;
			   
		   case "ALBUM" : 
			   System.out.println("Choisir Produit : ");
			   System.out.println("Produit 5");
			   params.put("IDPRODUIT", "5");
			   System.out.println("Titre souhaite : ");
			   params.put("TITRE", LectureClavier.lireChaine());
			   System.out.println("Mise en page souhaite : ");
			   params.put("MISEENPAGE", LectureClavier.lireChaine());
			   break;
			   
		   case "TIRAGE" : 
			   System.out.println("Choisir Produit : ");
			   System.out.println("Produit 4");
			   params.put("IDPRODUIT", "4");
			   System.out.println("FORMAT IMPRESSION  souhaite : ");
			   params.put("FORMATIMPRESSION", LectureClavier.lireChaine());
			   System.out.println("Nb exemplaire  souhaite : ");
			   params.put("NBEXEMPLAIRE", LectureClavier.lireChaine());
			   break;
		}
		
		TypeImpression ti = new TypeImpression(typeImp.get().name(), params);
		Impression finalImpression = new Impression(0, cl.getIdClient(), nomImp.get(), null);
		finalImpression.setTypeImpression(ti);
		
		//STEP 3: Add impression
		
		try {lindex.set(client_queries.addImpression(finalImpression));}
		catch (SQLException e) {e.printStackTrace();}
		
		//STEP 4 : choisir les photos a integrer ( tous les photos de tous les images que le client possede) 1/2
		
		try {images = client_queries.getClientImages(cl.getIdClient());}
		catch (SQLException e) {e.printStackTrace();}
		
		for(Image im : images) {
			try {photos.addAll(client_queries.getPhotosImage(im.getChemin()));} 
			catch (SQLException e) {e.printStackTrace();}
		}
		
		for(Photo p : photos) {
		photoMenu.addMenuItem(p.getIdPhoto() + "", p.getTypeRetouche(),
				()->{
					 //System.out.println("Saisssez les specifications particulieres");
					 pImp.add(new PhotoImpression(lindex.get(), p, nomImp.get()));}
			);
		}
		
		System.out.println("Choisissez les photos a integrer dans l'impression : ");
		photoMenu.initMenu(false);
		
		// STEP 4 bis + photo des images partagees 2/2 A Faire
		
		
		// STEP 5 final insert the photos in the newly created impression
		try {for(PhotoImpression pii : pImp) {client_queries.addPhotoImpression(pii);}}
		catch (SQLException e) {e.printStackTrace();}
		
		System.out.println("Photos ajoutes avec succees");
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
		GenericMenu menuImpression = new GenericMenu();
		GenericMenu menuAdresse = new GenericMenu();
		Client c = ConnexionHandler.userClient;
		ArrayList<CommandeImpression> commImps = new ArrayList<CommandeImpression>();
		AtomicReference<Adresse> adresse = new AtomicReference<Adresse>();
		
		// step one choose the inmpressions that you want to put in the command + the quantities
		try {client_queries.getClientImpression(c.getIdClient()).
			forEach(e -> menuImpression.addMenuItem(
					e.getIdImpression() + "", 
					e.getTypeImpression().type.name()+" : "+e.getNom(), 
					()->{
						int q = LectureClavier.lireEntier("Quantite Impression:");
						commImps.add(new CommandeImpression(e,q));
					}
			));} 
		catch (NumberFormatException | SQLException e) {e.printStackTrace();}

		System.out.println("Choisissez les impressions a commander [pour finir taper F]");

		menuImpression.initMenu(false);
		
		// then demand the adress
		System.out.println("Choisissez l'adresse de livraison");
		
			c.getAdressList().forEach(e -> menuAdresse.addMenuItem(
					e.getIdAdresse()+"",
					e.getAdresse(),
					()->{adresse.set(e);}
			));
			
			menuAdresse.initMenu(false);
			
		//  calculate the price  ( we need a method but put something arbitrary
			float price =  0;
			
			System.out.println("Recapitulatif des impressions commandes" + System.lineSeparator());
			for(CommandeImpression ci : commImps) {
				float subprice = 0;
				
				try{subprice = client_queries.prixImpression(ci.impression);}
				catch (SQLException e) {e.printStackTrace();}
			
				System.out.println("Commande :" + ci.impression.getNom() + " P :" + subprice + " P x Q :" + subprice*ci.quantite);
				
				price += subprice*ci.quantite;
			}
			
			System.out.println(System.lineSeparator() + "Prix total commande : " + price);
			
			// press Validate to validate
			System.out.println(System.lineSeparator() + "Tapez V pour valider ou A pour aborter");
			String validation = LectureClavier.lireChaine();
			validation.toLowerCase();
			if(validation.equals("v")) {
				int newindex;
				Commande com = new Commande(
						0, 
						c.getIdClient(), 
						adresse.get().getIdAdresse(), 
						new Date(), 
						price, 
						0, 
						"non", 
						"EnCoursPreparation", 
						"Domicile", commImps);
				
				try { 
					newindex = client_queries.addCommande(com);  
					for(CommandeImpression ci : commImps) {client_queries.addCommandeImpression(newindex, ci);}
				}
				catch (SQLException e1) {e1.printStackTrace();}
				
				System.out.println(System.lineSeparator()+ "Commande envoyee avec statut EnCoursDePreparation");
			}
			else {
				System.out.println("Commande ANNNULE");
			}
			
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
			ArrayList<Image> myImages = client_queries.getClientImages(ConnexionHandler.idUser);

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

	public static void deleteUser() {
		GenericMenu genericMenu = new GenericMenu();

		try {
			ArrayList<Client> clients = client_queries.getClients();

			for (int i = 0; i < clients.size(); i++) {
				int id = clients.get(i).getIdClient();
				String mail = clients.get(i).getMail();
				genericMenu.addMenuItem(i + "", mail, () -> {
					try {
						client_queries.deleteClient(id);
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

	public static void deleteImage() {

		GenericMenu genericMenu = new GenericMenu();

		try {
			ArrayList<Image> myImages = client_queries.getClientImages(ConnexionHandler.idUser);

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
			System.out.println("Nom :" + client.getNom() + " | Prenom : " + client.getPrenom() + " | Telephone : "
					+ client.getTelephone() + " | Mail : " + client.getMail());
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

		boolean partage = LectureClavier.lireOuiNon("Image partagée(o : Oui/ n: Non): ");

		Date dateUtilisation = new Date();

		return new Image(chemin, idClient, resolution, partage, dateUtilisation, 0);

	}

}
