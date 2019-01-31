package scenario;

import java.sql.SQLException;
import java.util.ArrayList;

import bd_layer.ConnectionBD;
import bd_layer.ResQ;
import bd_layer.Tuple;
import bd_layer.queryModel.QueryMethods;

public class ScenarioMain {
	
	public static void reset() throws SQLException {
		int idimp = 0;
		QueryMethods q = new QueryMethods();
		
		
		
		ResQ lol = ConnectionBD.getData(ConnectionBD.conn, "select idimpression from impression where nom = 'Road Trip Tirage 2'");
		idimp = Integer.parseInt(lol.get(0).get(0).toString());
		
		ArrayList<Tuple> conds = new ArrayList<Tuple>();
		conds.add(new Tuple("IDIMPRESSION",idimp+ ""));
		ConnectionBD.deleteData(ConnectionBD.conn, "photo_tirage_impression", conds);
		ConnectionBD.deleteData(ConnectionBD.conn, "photo_impression", conds);
		
		//impression deletion
		conds.clear();
		conds.add(new Tuple("IDIMPRESSION" , idimp+""));
		ConnectionBD.deleteData(ConnectionBD.conn, "tirage", conds);
		ConnectionBD.deleteData(ConnectionBD.conn, "impression", conds);
		
		//supression photo
		conds.clear();
		conds.add(new Tuple("chemin" , "/skur/skur4.jpg"));
		ConnectionBD.deleteData(ConnectionBD.conn, "photo", conds);
		q.deleteImage("/cookie/lol1.jpg");
		q.deleteImage("/cookie/lol2.jpg");
		
		q.deleteImage("/skur/skur1.jpg");
		q.deleteImage("/skur/skur2.jpg");
		q.deleteImage("/skur/skur3.jpg");
		q.deleteImage("/skur/skur4.jpg");
		q.deleteImage("/skur/skur5.jpg");
	}
	public static void main(String[]  args) throws InterruptedException, SQLException {
		
		QueryMethods q = new QueryMethods();
		
		//Scenarios Manuel -> 
		//En Solo : Saisir Commande , En Concurrence : UpdateImageStatus (admin et client) [20min]
			// Reeffectuer la manip ,
			// Detecter le jeux de donnnes parfait 
			//sysout
			
		// Effectuer la maip updateStatus
				//Trouver le jeux de donnee
				//sysout
				//sql dev or get display
		
		//Scenario Script ->
		// En Concurrence : ClientBot , AdminBot a faire + Reset Function [speed code 1h]
			// Effectuer une methode reset
			// Tester retester
			//SYsout everything
			//sql dev or getDisplay
		
		
		ClientBot client1 = new ClientBot();
		ClientBot2 client2 = new ClientBot2();
		AdminBot admin = new AdminBot();
		
		/** SCRIPT  **/
		client1.start(); // Il ajoute des images dont certaines sont partagees
		client1.join(); 
		
		client2.start(); //il utilise l'image du client1 pour creer ses photos et son impression
		admin.start(); //admin en concurrence
		
		//reset();
		
	}
}
