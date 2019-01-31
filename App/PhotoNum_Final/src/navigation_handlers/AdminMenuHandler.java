package navigation_handlers;

import java.sql.SQLException;
import java.util.ArrayList;

import bd_layer.Tuple;
import bd_layer.queryModel.QueryMethods;
import dataInterfaces.Image;
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
			StockHandler.update();
		});
		
		interactionTypeChoice.addMenuItem("4", "Modifier prix", () -> {
			StockHandler.update();
		});

		interactionTypeChoice.initMenu(false);
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
			e.printStackTrace();
		}

	}
	
	public static void updateImageStatus(String path) {
		GenericMenu interactionTypeChoice = new GenericMenu();

		// Adding a menu item using a Lambda expression.
		interactionTypeChoice.addMenuItem("0", "Partager", () -> {
			Tuple values = new Tuple("partager", "1");
			try {
				admin_queries.updateImage(path, values);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// Adding another menu item
		interactionTypeChoice.addMenuItem("1", "Privatiser", () -> {
			Tuple values = new Tuple("partager", "0");
			try {
				admin_queries.updateImage(path, values);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// Launching the app via the menu
		interactionTypeChoice.initMenu(false);
	}

	
}
