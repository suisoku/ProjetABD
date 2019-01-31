package scenario;

public class ScenarioMain {
	public static void main(String[]  args) throws InterruptedException {
		
		
		ClientBot client1 = new ClientBot();
		
		ClientBot2 client2 = new ClientBot2();
		
		
		client1.start(); // Il ajoute des images dont certaines sont partagees
		client1.join(); 
		
		client2.start(); //il utilise l'image pour creer ses photos et son impression
		
		
	}
}
