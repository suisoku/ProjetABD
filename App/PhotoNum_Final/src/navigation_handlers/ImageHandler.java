package navigation_handlers;

import java.sql.SQLException;
import java.util.ArrayList;

import bd_layer.Tuple;
import bd_layer.queryModel.QueryMethods;

public class ImageHandler {
	
	private static QueryMethods image_queries = new QueryMethods();


	public static void getStats() {
		ArrayList<Tuple> statistiques = new ArrayList<Tuple>();
		try {
			statistiques = image_queries.getStatImages();
			for (Tuple stat : statistiques) {
				System.out.println("[" + stat.name + "] " + "[" + stat.value + " utilisations]");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
