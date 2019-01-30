package application;

import navigation_handlers.ConnexionHandler;
import navigation_handlers.core.GenericMenu;

public class PhotoNum {

	public static void main(String[] args) {
		GenericMenu interactionTypeChoice = new GenericMenu();

		// Adding a menu item using a Lambda expression.
		interactionTypeChoice.addMenuItem("0", "Normal mode", () -> {
			ConnexionHandler.start();
		});

		// Adding another menu item 
		interactionTypeChoice.addMenuItem("1", "Script mode", () -> {
			System.out.println("C'est le démarrage en mode script");
		});
		
		// Launching the app via the menu
		interactionTypeChoice.initMenu(true);
	}

}
