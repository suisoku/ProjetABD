package bd_layer.queryModel;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import bd_layer.ConnectionBD;
import bd_layer.ResQ;
import dataInterfaces.*;

public class TestQuery1 {

	/** Testing the queries **/
	public static void main(String[] args) throws SQLException {
		ClientQuery cq = new ClientQuery();
		
		
		Client wow = cq.getClient(4);
		
		System.out.println(wow.getIdClient() + " | " +  wow.getMail() + " | " + wow.getMdp() + " | " 
							+ wow.getNom() + " | " + wow.getPrenom() + " | " +  wow.getTelephone() );
		
		ArrayList<Adresse> ad = cq.getClientAdresses(4);
		
		for(Adresse e : ad) {
			System.out.println(e.getAdresse() + " | " +  e.getIdClient() + " | " );
		}
	
		
	}
}