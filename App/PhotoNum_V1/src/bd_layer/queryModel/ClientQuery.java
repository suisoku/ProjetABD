package bd_layer.queryModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import bd_layer.ConnectionBD;
import bd_layer.ResQ;
import dataInterfaces.Adresse;
import dataInterfaces.Client;
import dataInterfaces.CodePromo;
import dataInterfaces.Commande;
import dataInterfaces.CommandeImpression;
import dataInterfaces.Image;
import dataInterfaces.Impression;
import dataInterfaces.Photo;
import dataInterfaces.PhotoImpression;
import dataInterfaces.TypeImpression;

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
	

	
	public Client getClient(int clientId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from client where idclient="+clientId +"");
		Client c = null;
		for(ArrayList<Object> row : array) {
				c = new Client(Integer.parseInt(row.get(0).toString()) , row.get(1).toString() , row.get(2).toString() , 
						row.get(3).toString() , row.get(4).toString(), row.get(5).toString(),
						getClientAdresses(clientId),
						getClientPromos(clientId));
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
	
	public ArrayList<Commande>  getClientCommande(int clientId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from commande where idclient="+clientId +"");
		ArrayList<Commande> codeList = new ArrayList<Commande>();
		
		for(ArrayList<Object> row : array) {
			codeList.add(new Commande(
					Integer.parseInt(row.get(0).toString()), 
					Integer.parseInt(row.get(1).toString()), 
					(Date)row.get(2),
					Float.parseFloat(row.get(3).toString()),
					Integer.parseInt(row.get(4).toString()),
					row.get(5).toString(),
					row.get(6).toString(),
					row.get(7).toString(),
					getCommmandeImpression(Integer.parseInt(row.get(0).toString()) )
			));
		}
		return codeList;
	}
	
	public ArrayList<CommandeImpression> getCommmandeImpression(int commandeId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from commande_impression where idcommande="+commandeId+"");
		ArrayList<CommandeImpression> codeList = new ArrayList<CommandeImpression>();
		
		for(ArrayList<Object> row : array) {
			codeList.add(new CommandeImpression(
							getImpression(Integer.parseInt(row.get(1).toString())),
							Integer.parseInt(row.get(2).toString())
			));
		}
		return codeList;
	}
	
	public Impression getImpression(int idImpression) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from photo where idimpression="+idImpression+"");
		Impression codeList = null ;
		
		for(ArrayList<Object> row : array) {
			codeList = new Impression(
					Integer.parseInt(row.get(0).toString()), 
					Integer.parseInt(row.get(1).toString()),
					row.get(2).toString(),
					getPhotoImpression(idImpression)
					);
			codeList.setTypeImpression(getTypeImpression(idImpression, row.get(3).toString()));
		}
		return codeList;
	}
	
	private TypeImpression getTypeImpression(int idImpression, String type) throws SQLException{
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from "+type+" where idimpression="+idImpression+"");
        
        TypeImpression ti = null;
        HashMap<String , Object> attr ;
        
        int cols = rs.getMetaData().getColumnCount();
      
        while (rs.next()) {
        	attr = new HashMap<String , Object>();
        	for(int i = 1 ; i <= cols ; i++) {
        		attr.put(rs.getMetaData().getColumnName(i+1), rs.getObject(i));
        	}
        	
        	ti  =  new TypeImpression(
        			type,
        			attr);
        }
        // Close the result set, statement and the connection
        rs.close();
        stmt.close();
        
        return ti;
	}
	
	/** Je veux afficher les photos d'une impression **/
	public ArrayList<PhotoImpression> getPhotoImpression(int idImpression) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from photo_impression where idImpression="+idImpression+"");
		ArrayList<PhotoImpression> codeList = new ArrayList<PhotoImpression>();
		
		for(ArrayList<Object> row : array) {
			codeList.add(new PhotoImpression(
							Integer.parseInt(row.get(1).toString()),
							getPhoto(Integer.parseInt(row.get(2).toString())),
							row.get(3).toString()
			));
		}
		return codeList;
	}
	
	public Photo getPhoto(int idPhoto) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from photo where idphoto="+idPhoto+"");
		Photo codeList = null ;
		
		for(ArrayList<Object> row : array) {
			codeList = new Photo(
					Integer.parseInt(row.get(0).toString()), 
					row.get(1).toString(),
					row.get(2).toString(),
					row.get(3).toString()
					);
		}
		return codeList;
	}
	
	
	/** Je veux afficher les impressions du client 
	 * @throws SQLException 
	 * @throws NumberFormatException **/
	public ArrayList<Impression> getClientImpression(int idclient) throws NumberFormatException, SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from impression where idclient="+idclient+"");
		ArrayList<Impression>  codeList = new ArrayList<Impression>()   ;
		
		for(ArrayList<Object> row : array) {
			codeList.add(new Impression(
					Integer.parseInt(row.get(0).toString()), 
					Integer.parseInt(row.get(1).toString()),
					row.get(2).toString(),
					getPhotoImpression(Integer.parseInt(row.get(0).toString()))
				));
		}
		return codeList;
	}
	

	public Image getImage(String chemin) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from image where chemin="+chemin+"");
		Image codeList = null ;
		
		for(ArrayList<Object> row : array) {
			codeList = new Image(
					row.get(0).toString(), 
					Integer.parseInt(row.get(1).toString()),
					row.get(2).toString(),
					Integer.parseInt(row.get(3).toString()),
					(Date)row.get(4)
				);
		}
		return codeList;
	}

	/** Je veux affiche le tarif du produit  **/
	
	public ResQ getProductPrice(){
		return null;
	}
	
	
	
}
