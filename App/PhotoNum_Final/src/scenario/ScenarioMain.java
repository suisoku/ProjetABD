package scenario;

import bd_layer.queryModel.QueryMethods;

public class ScenarioMain {
	public static void main(String[]  args) throws InterruptedException {
		
		QueryMethods q = new QueryMethods();
		
		//Scenarios Manuel -> 
		//En Solo : Saisir Impression , En Concurrence : UpdateImageStatus (admin et client)
		
		//Scenario Script ->
		// En Solo : xxxxxx , En Concurrence : ClientBot
		
		// Tout retester (Manuel et non )
		
		ClientBot client1 = new ClientBot();
		
		ClientBot2 client2 = new ClientBot2();
		
		
		client1.start(); // Il ajoute des images dont certaines sont partagees
		client1.join(); 
		
		client2.start(); //il utilise l'image pour creer ses photos et son impression
		
		
	}
}
