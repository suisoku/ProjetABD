package navigation_handlers;

import java.sql.SQLException;

import application.LectureClavier;
import bd_layer.queryModel.QueryMethods;
import dataInterfaces.Client;
import navigation_handlers.core.GenericMenu;

public class ConnexionHandler {

	private static QueryMethods landing_queries = new QueryMethods();
	public static int idUser;
	public static Client userClient;

	// Launching the main menu for user-based interaction
	public static void start() {
		GenericMenu interactionTypeChoice = new GenericMenu();

		interactionTypeChoice.addMenuItem("0", "Connexion client", () -> {
			System.out.println("Saisir votre adresse mail : ");
			String mail = LectureClavier.lireChaine();

			System.out.println("Saisir votre mot de passe : ");
			String mdp = LectureClavier.lireChaine();
			try {
				idUser = landing_queries.authentification("client", mail, mdp);
				if (idUser != 0) {
					userClient = landing_queries.getClient(idUser);
					ClientMenuHandler.start();}
				else
					System.out.println("Informations saisies incorrectes. Veuillez réessayer!");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		interactionTypeChoice.addMenuItem("1", "Inscription client", () -> {
			ClientHandler.addUser();
		});

		interactionTypeChoice.addMenuItem("2", "Connexion administrateur", () -> {
			System.out.println("Saisir votre adresse mail : ");
			String mail = LectureClavier.lireChaine();

			System.out.println("Saisir votre adresse mot de passe : ");
			String mdp = LectureClavier.lireChaine();
			try {
				int result = landing_queries.authentification("client", mail, mdp);
				if (result != 0)
					AdminMenuHandler.start();
				else
					System.out.println("Informations saisies incorrectes. Veuillez réessayer!");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		interactionTypeChoice.initMenu(false);

	}

}
