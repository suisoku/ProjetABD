package navigation_handlers;

import bd_layer.queryModel.QueryMethods;

public class ImageHandler {
	
	private static QueryMethods image_queries = new QueryMethods();


	public static void delete() {
		System.out.println("Op�ration de suppresion r�ussie");
	}

	public static void getStats() {
		System.out.println("STATS: Man.United recent form 25GF / 5GA.");
	}

}
