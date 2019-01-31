package navigation_handlers;

import java.sql.SQLException;
import java.util.ArrayList;

import application.LectureClavier;
import bd_layer.Tuple;
import bd_layer.queryModel.QueryMethods;
import dataInterfaces.ProduitInventaire;
import navigation_handlers.core.GenericMenu;

public class StockHandler {

	private static QueryMethods stock_queries = new QueryMethods();

	public static void update() {
		GenericMenu genericMenu = new GenericMenu();
		ProduitInventaire p = new ProduitInventaire();
		
		try {
			ArrayList<ProduitInventaire> listProduit = stock_queries.getProduits();
			
			for (int i = 0; i < listProduit.size(); i++) {
				p = listProduit.get(i);
				String option = p.getNomCommercial() + "|" + p.getCaracteristique();
				int idProduit = p.getIdProduit();
				genericMenu.addMenuItem(i + "", option , () -> updateProduit(idProduit));
			}
			genericMenu.initMenu(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static void updateProduit(int idProduit) {
		GenericMenu genericMenu = new GenericMenu();
		
		genericMenu.addMenuItem("1", "Ajouter stock", () -> {
			try {
				calculate(idProduit,true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		
		genericMenu.addMenuItem("2", "Diminuer stock", () -> {
			try {
				calculate(idProduit,false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	private static void calculate(int idProduit, boolean adding) throws SQLException {
		ProduitInventaire p = stock_queries.getProduitInventaire(idProduit);
		int new_value = p.getStock();
		int quantite = LectureClavier.lireEntier("Entrer la quantité: ");
		
		if (adding) {
			new_value += quantite;
		}
		else {
			new_value -= quantite;
		}
		stock_queries.updateInventaire(idProduit, new Tuple("stock", new_value+""));
		
	}
}
