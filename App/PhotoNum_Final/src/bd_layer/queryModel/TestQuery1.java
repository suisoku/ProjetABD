package bd_layer.queryModel;

import java.sql.SQLException;
import java.util.ArrayList;

import dataInterfaces.*;
public class TestQuery1 {

	/** Testing the queries **/
	public static void main(String[] args) throws SQLException {
		ClientQuery cq = new ClientQuery();
		
		
		Client wow = cq.getClient(4);
		
		System.out.println(wow.getIdClient() + " | " +  wow.getMail() + " | " + wow.getMdp() + " | " 
							+ wow.getNom() + " | " + wow.getPrenom() + " | " +  wow.getTelephone() );
		
		ArrayList<CodePromo> ad = cq.getClientPromos(4);
		
		for(CodePromo e : ad) {
			System.out.println(e.getCode() + " | " +  e.getIdClient() + " | " +  e.getReduction() + " | " +  e.isUsed()+ " | ");
		}
		
		
	
		
	}
}