package navigation_handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import navigation_handlers.core.GenericMenu;

public class ClientMenuHandler {
	static HashMap<String, Runnable> map = new HashMap<>();

	/***
	 * Filling the menu options for the normal interaction mode
	 */
	private static void load() {
		map.put("Ajouter une image", ClientHandler::addImage);
		map.put("Retoucher une image (créer photo)", ClientHandler::addPhoto);
		map.put("Créer une impression", ClientHandler::createImpression);
		map.put("Modifier/Supprimer une impression", ClientHandler::updateImpression);
		map.put("Modifier/supprimer une photo", ClientHandler::updatePhoto);
		map.put("Modifer une image", ClientHandler::changeImageStatus);
		map.put("Supprimer une image", ClientHandler::deleteImage);
		map.put("Voir les statistiques sur les images", ImageHandler::getStats);
		map.put("Consulter mes informations", ClientHandler::showDetails);
		map.put("Saisir une commande", ClientHandler::createCommand);
	}

	// Launching the main menu for user-based interaction
	@SuppressWarnings("rawtypes")
	public static void start() {
		System.out.println("Current user: " + ConnexionHandler.idUser);
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
