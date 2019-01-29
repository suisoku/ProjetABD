package navigation_handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import navigation_handlers.core.GenericMenu;

public class MenuHandler {
	static HashMap<String, Runnable> map = new HashMap<>();

	/***
	 * Filling the menu options for the normal interaction mode
	 */
	private static void load() {
		map.put("Ajouter un client", ClientHandler::add);
		map.put("Créer une impression", ImpressionHandler::create);
		map.put("Modifier/Supprimer une impression", ImpressionHandler::update);
		map.put("Modifier/supprimer une photo", PhotoHandler::update);
		map.put("Supprimer une image", ClientHandler::delete);
		map.put("Voir les statistiques sur les images", ImageHandler::getStats);
		map.put("Consulter mes informations", ClientHandler::showDetails);
	}

	// Launching the main menu for user-based interaction
	public static void start() {
		GenericMenu genericMenu = new GenericMenu();
		load();
		int optionId = 0;

		Iterator<?> it = map.entrySet().iterator();

		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry) it.next();
			String menuTitle = pair.getKey().toString();

			genericMenu.addMenuItem(optionId + "", menuTitle, () -> map.get(menuTitle).run());
			optionId++;
		}

		// Initalizing the menu
		genericMenu.initMenu(false);
	}

}
