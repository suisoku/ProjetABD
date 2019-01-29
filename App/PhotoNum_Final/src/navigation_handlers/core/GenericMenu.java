package navigation_handlers.core;

import java.util.ArrayList;
import java.util.Scanner;

public class GenericMenu {
	private ArrayList<MenuItem> menuItems;
	private Scanner scanner;


	public GenericMenu() {
		this.menuItems = new ArrayList<MenuItem>();
		this.scanner = new Scanner(System.in);
	}

	private void addMenuItem(String key, String name) {
		MenuItem menuItem = new MenuItem(key, name);
		menuItems.add(menuItem);
	}

	/**
	 * Adds a MenuItem with key, name and runnable to the menuItems ArrayList
	 */
	public void addMenuItem(String key, String name, Runnable runnable) {
		MenuItem menuItem = new MenuItem(key, name, runnable);
		menuItems.add(menuItem);
	}

	/**
	 * Prints all the existing menu items in the console in the format [key]:
	 * name
	 */
	private void printMenuItems() {
		for (MenuItem menuItem : menuItems) {
			System.out.println("[" + menuItem.getKey() + "]: " + menuItem.getName());
		}
	}

	/**
	 * Runs the runnable of the menu item that matches the given key
	 */
	private void runCommand(String key) throws Exception {
		ArrayList<MenuItem> filteredMenuItems = new ArrayList<MenuItem>();

		/**
		 * checking if the given key corresponds to a MenuItem
		 */
		
		for (MenuItem i : menuItems) {
			if (i.getKey().toLowerCase().equals(key))
				filteredMenuItems.add(i);
		}

		if (filteredMenuItems.size() > 0) {
			/**
			 * if there are any menu items with the given key, run their runnables
			 */
			for (MenuItem i : filteredMenuItems) {
				i.getRunnable().run();
			}
		} else
			throw new Exception("Choix invalide! Pouvez-vous réessayer?");
	}

	/**
	 * Makes the scanner wait for a line of input
	 */
	private String scanLine() {
		System.out.print("> ");
		return scanner.nextLine();
	}

	/**
	 * Adds the default menu items, for example the quit menu item should always
	 * be in there.
	 */
	private void addDefaultMenuItems(boolean isFirst) {
		if (isFirst) addMenuItem("Q", "Quitter");
		else addMenuItem("R", "Retour");
	}

	public void initMenu(boolean isFirst) {
		
		addDefaultMenuItems(isFirst);

		Boolean quit = false;

		while (!quit) {
			/* print instructions */
			System.out.println("--- Menu ---");

			/* print the menu items every time */
			printMenuItems();

			/* allow for input */
			String option = scanLine();

			/* show the input */
			System.out.println("\nOption choisie " + option);

			option = option.toLowerCase();

			/* act depending on the input */
			try {
				switch (option) {
				case "r":
					quit = true;
					break;
				case "q":
					System.out.println("Application arrêtée avec succès!");
					System.exit(0);
					break;
				default:
					try {
						runCommand(option);
					} catch (Exception ex) {
						System.out.println("Oops!: " + ex.getMessage());
					}
					break;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			System.out.println();
		}
	}
}
