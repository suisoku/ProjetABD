package navigation_handlers;

import java.sql.SQLException;
import java.util.ArrayList;

import application.LectureClavier;
import bd_layer.Tuple;
import bd_layer.queryModel.QueryMethods;
import dataInterfaces.Commande;
import dataInterfaces.Image;
import dataInterfaces.ProduitInventaire;
import navigation_handlers.core.GenericMenu;

public class AdminMenuHandler {
	private static QueryMethods admin_queries = new QueryMethods();

	public static void start() {
		GenericMenu interactionTypeChoice = new GenericMenu();

		interactionTypeChoice.addMenuItem("0", "Ajouter utilisateur", () -> {
			ClientHandler.addUser();
		});

		interactionTypeChoice.addMenuItem("1", "Supprimer utilisateur", () -> {
			ClientHandler.deleteUser();
		});

		interactionTypeChoice.addMenuItem("2", "Changer statut image", () -> {
			changeImageStatus();
		});

		interactionTypeChoice.addMenuItem("3", "Modifier stock", () -> {
			updateStock();
		});

		interactionTypeChoice.addMenuItem("4", "Modifier prix", () -> {
			updateStockPrix();
		});
		
		interactionTypeChoice.addMenuItem("5", "Annuler commande", () -> {
			updateCommande();
		});

		interactionTypeChoice.initMenu(false);
	}

	private static void updateCommande() {
		GenericMenu genericMenu = new GenericMenu();

		try {
			ArrayList<Commande> commandes = admin_queries.getCommandes();
			for (int i = 0; i < commandes.size(); i++) {
				int idCmd = commandes.get(i).getIdCmd();
				String option = "| Id: "+ commandes.get(i).getIdCmd() + " | Mode livraison:  " + commandes.get(i).getModeLivraison();
				genericMenu.addMenuItem(i + "", option, () -> updateCommandeStatus(idCmd));
			}
			;

			genericMenu.initMenu(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error : " + e.getMessage());
		}
		
	}

	private static void updateCommandeStatus(int idCmd) {
		try {
			admin_queries.updateCommande(idCmd);
		} catch (SQLException e) {
			System.out.println("Error : " + e.getMessage());
		}
	}

	public static void changeImageStatus() {
		GenericMenu genericMenu = new GenericMenu();

		try {
			ArrayList<Image> images = admin_queries.getDBImages();
			for (int i = 0; i < images.size(); i++) {
				String path = images.get(i).getChemin();
				genericMenu.addMenuItem(i + "", path, () -> updateImageStatus(path));
			}
			;

			genericMenu.initMenu(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error : " + e.getMessage());
		}

	}

	public static void updateImageStatus(String path) {
		GenericMenu interactionTypeChoice = new GenericMenu();

		// Adding a menu item using a Lambda expression.
		interactionTypeChoice.addMenuItem("0", "Privatiser", () -> {
			Tuple values = new Tuple("partager", "0");
			try {
				admin_queries.updateImage(path, values);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error : " + e.getMessage());
			}
		});

		// Adding another menu item
		interactionTypeChoice.addMenuItem("1", "Partager", () -> {
			Tuple values = new Tuple("partager", "1");
			try {
				admin_queries.updateImage(path, values);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error : " + e.getMessage());
			}
		});

		// Launching the app via the menu
		interactionTypeChoice.initMenu(false);
	}

	/**
	 * ********************** STOCK HANDLER
	 */
	public static void updateStock() {
		GenericMenu genericMenu = new GenericMenu();

		try {
			ArrayList<ProduitInventaire> listProduit = admin_queries.getProduits();
			for (int i = 0; i < listProduit.size(); i++) {
				String option = listProduit.get(i).getNomCommercial() + "|" + listProduit.get(i).getCaracteristique();
				int idProduit =  listProduit.get(i).getIdProduit();
				genericMenu.addMenuItem(i + "", option , () -> updateProduit(idProduit));
			}
			genericMenu.initMenu(false);
		} catch (SQLException e) {
			System.out.println("Error : " + e.getMessage());
		}

	}
	
	public static void updateStockPrix() {
		GenericMenu genericMenu = new GenericMenu();

		try {
			ArrayList<ProduitInventaire> listProduit = admin_queries.getProduits();
			for (int i = 0; i < listProduit.size(); i++) {
				String option = listProduit.get(i).getNomCommercial() + "|" + listProduit.get(i).getCaracteristique();
				int idProduit =  listProduit.get(i).getIdProduit();
				genericMenu.addMenuItem(i + "", option , () -> updatePrixProduit(idProduit));
			}
			genericMenu.initMenu(false);
		} catch (SQLException e) {
			System.out.println("Error : " + e.getMessage());
		}

	}

	private static void updateProduit(int idProduit) {
		GenericMenu genericMenu = new GenericMenu();

		genericMenu.addMenuItem("1", "Ajouter du stock", () -> {
			try {
				calculate(idProduit, true);
			} catch (SQLException e) {
				System.out.println("Error : " + e.getMessage());
			}
		});

		genericMenu.addMenuItem("2", "Diminuer du stock", () -> {
			try {
				calculate(idProduit, false);
			} catch (SQLException e) {
				System.out.println("Error : " + e.getMessage());
			}
		});
		
		genericMenu.initMenu(false);
	}

	private static void calculate(int idProduit, boolean adding) throws SQLException {
		ProduitInventaire p = admin_queries.getProduitInventaire(idProduit);
		int new_value = p.getStock();
		int quantite = LectureClavier.lireEntier("Entrer la quantité: ");

		if (adding) {
			new_value += quantite;
		} else {
			new_value -= quantite;
		}
		admin_queries.updateInventaire(idProduit, new Tuple("stock", new_value + ""));

	}
	
	
	private static void updatePrixProduit(int idProduit) {
		GenericMenu genericMenu = new GenericMenu();

		genericMenu.addMenuItem("1", "Augmenter prix", () -> {
			try {
				calculatePrix(idProduit, true);
			} catch (SQLException e) {
				System.out.println("Error : " + e.getMessage());
			}
		});

		genericMenu.addMenuItem("2", "Baisser prix", () -> {
			try {
				calculatePrix(idProduit, false);
			} catch (SQLException e) {
				System.out.println("Error : " + e.getMessage());
			}
		});
		
		genericMenu.initMenu(false);
	}

	private static void calculatePrix(int idProduit, boolean adding) throws SQLException {
		ProduitInventaire p = admin_queries.getProduitInventaire(idProduit);
		float new_value = p.getPrix();
		float montant = LectureClavier.lireFloat("Entrer le montant: ");

		if (adding) {
			new_value += montant;
		} else {
			new_value -= montant;
		}
		
		admin_queries.updateInventaire(idProduit, new Tuple("prix", Math.round(new_value) + ""));

	}

}
