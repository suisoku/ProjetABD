package navigation_handlers;

import navigation_handlers.core.GenericMenu;

public class AdminMenuHandler {

	public static void start() {
		GenericMenu interactionTypeChoice = new GenericMenu();
		
		interactionTypeChoice.addMenuItem("0", "Ajouter utilisateur", () -> {
			ClientHandler.addUser();
		});
		
		interactionTypeChoice.addMenuItem("1", "Supprimer utilisateur", () -> {
			ImpressionHandler.delete();
		});
		
		interactionTypeChoice.addMenuItem("2", "Changer statut image", () -> {
			ImpressionHandler.delete();
		});
		
		interactionTypeChoice.addMenuItem("3", "Modifier stock", () -> {
			ImpressionHandler.update();
		});
		
		interactionTypeChoice.addMenuItem("3", "Modifier prix", () -> {
			ImpressionHandler.update();
		});

		interactionTypeChoice.initMenu(false);
	}

}
