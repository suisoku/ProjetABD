package bd_layer.queryModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import bd_layer.ConnectionBD;
import bd_layer.ResQ;
import dataInterfaces.Adresse;
import dataInterfaces.Client;
import dataInterfaces.CodePromo;

/** All Query Methods related to the client, 
 * Method Return Data in the corresponding wrapper model
 * IF No results , returns Null
 * @author Nord_38
 *
 */
public class ClientQuery {
	
	
	private  Connection con;
	
	public  ClientQuery() {
		 con = ConnectionBD.getConnection();
	}
	
	public ResQ getAllClients() throws SQLException {
		ResQ r = ConnectionBD.getData(con, "select * from client");
		
		return r;
	}
	
	public Client getClient(int clientId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from client where idclient="+clientId +"");
		Client c = null;
		for(ArrayList<Object> row : array) {
				c = new Client(Integer.parseInt(row.get(0).toString()) , row.get(1).toString() , row.get(2).toString() , 
						row.get(3).toString() , row.get(4).toString(), row.get(5).toString());
		}
		return c;
	}
	
	
	
	public ArrayList<Adresse> getClientAdresses(int clientId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from adresse where idclient="+clientId +"");
		ArrayList<Adresse> adressList = new ArrayList<Adresse>();
		
		for(ArrayList<Object> row : array) {
			adressList.add(new Adresse(Integer.parseInt(row.get(0).toString()), row.get(1).toString() , row.get(2).toString()));
		}
		return adressList;
	}
	
	public ArrayList<CodePromo> getClientPromos(int clientId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from codepromo where idclient="+clientId +"");
		ArrayList<CodePromo> codeList = new ArrayList<CodePromo>();
		
		for(ArrayList<Object> row : array) {
			codeList.add(new CodePromo(Integer.parseInt(row.get(0).toString()), 
					Float.parseFloat(row.get(1).toString()) , Integer.parseInt(row.get(2).toString()),
					Integer.parseInt(row.get(3).toString()) ));
		}
		return codeList;
	}
	
	public ResQ getClientImages(int clientId) {
		return null;
		
	}
	
	public ResQ getCommande() {
		return null;
		
	}
	
	/** Je veux afficher les impressions du client **/
	public ResQ getClientImpression() {
		return null;
		
	}
	
	/** Je veux afficher les photos d'une impression **/
	public ResQ getPhotosOfImpression() {
		return null;
		
	}
	
	/** Je veux affiche le tarif du produit  **/
	
	public ResQ getProductPrice(){
		return null;
	}
	
	
	
}
