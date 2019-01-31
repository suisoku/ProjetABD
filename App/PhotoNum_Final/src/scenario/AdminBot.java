package scenario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import bd_layer.ConnectionBD;
import bd_layer.Tuple;
import bd_layer.queryModel.QueryMethods;
import dataInterfaces.Client;
import dataInterfaces.Image;

public class AdminBot extends Thread {

	public void run(){
		// Getting a connection to the DB
		QueryMethods q =  new QueryMethods();
		
		// credentials
		String mail =  "toto";
		String mdp =  "aze";
		int idAdmin = 0;
		
		//authentification
		try {idAdmin = q.authentification("admin", mail, mdp);}
		catch (NumberFormatException | SQLException e) {e.printStackTrace();}
		
		if(idAdmin != 0) {
			System.out.println("THREAD ADMIN " + mail);
			try {
			/** TRANSACTION WITh CONCURRENCY **/
			ConnectionBD.conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			ConnectionBD.conn.setAutoCommit(false);
			
			int stockCourant = q.getProduitInventaire(4).getStock();
			q.updateInventaire(4, new Tuple("stock", stockCourant + 20  + ""));
			
			System.out.println("Stock courant " + stockCourant);
			System.out.println("Stock with update (+20)");
			
			ConnectionBD.conn.commit();
			}catch (SQLException e) {
				try {ConnectionBD.conn.rollback();}catch (SQLException e1) {e1.printStackTrace();}
				System.out.println("Error : " + e.getMessage());
			}
			
			try {
				ConnectionBD.conn.setAutoCommit(true);
				ConnectionBD.conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}catch (SQLException e2) {e2.printStackTrace();}
			
		}
		else {
			System.out.println("ERREUR Connection admin");
		}
	}
}
