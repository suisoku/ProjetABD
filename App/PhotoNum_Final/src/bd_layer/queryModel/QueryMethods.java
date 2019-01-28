package bd_layer.queryModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import bd_layer.ConnectionBD;
import bd_layer.ResQ;
import bd_layer.Tuple;
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
public class QueryMethods {
	
	
	private static final Connection con = ConnectionBD.getConnection();
	
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
		ResQ array = ConnectionBD.getData(con, "select * from impression where idimpression="+idImpression+"");
		Impression codeList = null ;
		
		for(ArrayList<Object> row : array) {
			codeList = new Impression(
					Integer.parseInt(row.get(0).toString()), 
					Integer.parseInt(row.get(1).toString()),
					row.get(2).toString(),
					getPhotoImpression(idImpression)
					);
			//System.out.println(row.get(3).toString());
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
        	for(int i = 1 ; i < cols ; i++) {
        		attr.put(rs.getMetaData().getColumnName(i+1), rs.getObject(i+1));
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
							getPhoto(Integer.parseInt(row.get(0).toString())),
							row.get(2).toString()
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
			codeList.get(codeList.size()-1).setTypeImpression(getTypeImpression(Integer.parseInt(row.get(0).toString()), row.get(3).toString()));;
		}
		return codeList;
	}
	

	public Image getImage(String chemin) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from image where chemin='"+chemin+"'");
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

	/** Je veux affiche le tarif du produit NOT_DONE  **/
	public ResQ getProductPrice(){
		return null;
	}

	
	public void addClient(Client client) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		
		values.add(client.getIdClient()+"");
		values.add(client.getMail());
		values.add(client.getNom());
		values.add(client.getPrenom());
		values.add(client.getMdp());
		values.add(client.getTelephone());
		
		ConnectionBD.addData(con, "client", values);
	}
	
	
	public void deleteClient(int idClient) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();
		
		cond.add(new Tuple("idclient", idClient+""));
		
		ConnectionBD.deleteData(con, "client", cond);
	}
	
	public void addImage(Image image) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		
		values.add(image.getChemin());
		values.add(image.getIdClient() + "");
		values.add(image.getResolution());
		values.add(image.isPartager() ? "1" : "0");
		
		String dateS = new SimpleDateFormat("dd-MMM-yy").format(image.getDateUtilisation());
		
		//System.out.println(dateS);
		values.add(dateS);
		
		ConnectionBD.addData(con, "image", values);
	}
	
	public void deleteImage(String chemin) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();
		
		cond.add(new Tuple("chemin", chemin+""));
		
		ConnectionBD.deleteData(con, "image", cond);
	}
	
	public void addPhoto(Photo photo) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		
		values.add(photo.getIdPhoto()+ "");
		values.add(photo.getChemin() );
		values.add(photo.getCommentaire());
		values.add(photo.getTypeRetouche());
		
		//String dateS = new SimpleDateFormat("dd-MMM-yy").format(image.getDateUtilisation());
		ConnectionBD.addData(con, "photo", values);
	}
	
	public void addImpression(Impression impression) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		TypeImpression ti = impression.getTypeImpression();
		
		values.add(impression.getIdImpression() + "");
		values.add(impression.getIdClient() + "");
		values.add(impression.getNom());
		values.add(ti.type.name());
		
		//String dateS = new SimpleDateFormat("dd-MMM-yy").format(image.getDateUtilisation());
		//ConnectionBD.addData(con, "impression", values);
        Statement stmt = con.createStatement();
        String colPool = " (IDIMPRESSION ,";
        String valPool = " ('"+impression.getIdImpression()+ "' ,";
        for(Entry<String, Object> e : ti.attributes.entrySet()) {
        	colPool+= ""+e.getKey() +" ,";
        	valPool+="'"+e.getValue() +"' ,";
        }
        colPool = colPool.substring(0, colPool.length() - 1) + ")";
        valPool = valPool.substring(0, valPool.length() - 1) + ")";
        String query = "INSERT INTO " + ti.type.name() + colPool +" VALUES" + valPool;
        stmt.executeUpdate(query);
        // Close the result set, statement and the connection

        stmt.close();		
	}
	
	public void addPhotoImpression(PhotoImpression pi) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		
		values.add(pi.photo.getIdPhoto() + "");
		values.add(pi.idImpression + "");
		values.add(pi.specPart);
	
		//String dateS = new SimpleDateFormat("dd-MMM-yy").format(image.getDateUtilisation());
		ConnectionBD.addData(con, "photo_impression", values);
	}
	
	public void addCommandeImpression(int idCommande , CommandeImpression ci) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		
		values.add(idCommande + "");
		values.add(ci.impression.getIdImpression() + "");
		values.add(ci.quantite + "");
		
	
		//String dateS = new SimpleDateFormat("dd-MMM-yy").format(image.getDateUtilisation());
		ConnectionBD.addData(con, "commande_impression", values);
	}
	
	public void addCommande(Commande c) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		
		values.add(c.getIdCmd() + "");
		values.add(c.getIdClient() + "");
		String dateS = new SimpleDateFormat("dd-MMM-yy").format(c.getDatePaiement());
		values.add(dateS);
		values.add(c.getMontant() + "");
		values.add(c.isHistorise() ? "1" : "0");
		values.add(c.getRenduPdf());
		values.add(c.getStatut());
		values.add(c.getModeLivraison());
		
		ConnectionBD.addData(con, "commande", values);
	}
	
	public void addAdresse(Adresse s) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		
		values.add(s.getIdClient()+"");
		values.add(s.getNomAdresse());
		values.add(s.getAdresse());
		
		ConnectionBD.addData(con, "adresse", values);
	}
	
	public void addCodePromo(CodePromo cp) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		
		values.add(cp.getCode()+"");
		values.add(cp.getReduction()+"");
		values.add(cp.isUsed() ? "1" : "0" );
		values.add(cp.getIdClient()+"");
		
		ConnectionBD.addData(con, "codepromo", values);
	}
	
	
	public void deleteImpression(int idImpression , String type) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();
		
		cond.add(new Tuple("idImpression", idImpression+""));
		
		ConnectionBD.deleteData(con, type, cond);
		ConnectionBD.deleteData(con, "impression", cond);
	}
	
	public void deletePhotoImpression(PhotoImpression p) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();
		
		cond.add(new Tuple("idImpression", p.idImpression+""));
		cond.add(new Tuple("idPhoto", p.photo.getIdPhoto()+""));
		
		ConnectionBD.deleteData(con, "photo_impression", cond);
	}
	
	public void deleteCommandeImpression(int idCommande , CommandeImpression c) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();
		
		cond.add(new Tuple("idCommande", idCommande+""));
		cond.add(new Tuple("idImpression", c.impression.getIdImpression()+""));
		
		ConnectionBD.deleteData(con, "commande_impression", cond);
	}
	
	public void deleteCommande(int idCommande) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();
		cond.add(new Tuple("idCommande", idCommande+""));
		ConnectionBD.deleteData(con, "commande", cond);
	}
	
	
}
