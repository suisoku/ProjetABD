package bd_layer.queryModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import bd_layer.ConnectionBD;
import bd_layer.ResQ;
import bd_layer.Tuple;
import dataInterfaces.Admin;
import dataInterfaces.AdminHist;
import dataInterfaces.Adresse;
import dataInterfaces.Client;
import dataInterfaces.CodePromo;
import dataInterfaces.Commande;
import dataInterfaces.CommandeImpression;
import dataInterfaces.Image;
import dataInterfaces.Impression;
import dataInterfaces.Photo;
import dataInterfaces.PhotoImpression;
import dataInterfaces.ProduitInventaire;
import dataInterfaces.TypeImpression;

/**
 * All Query Methods related to the client, Method Return Data in the
 * corresponding wrapper model IF No results , returns Null
 * 
 * @author Nord_38
 *
 */
public class QueryMethods {

	private final static Connection con = ConnectionBD.getConnection();

	public int authentification(String table, String mail, String mdp) throws NumberFormatException, SQLException {

		ResQ array = ConnectionBD.getData(con,
				"select idClient from " + table + " where mail='" + mail + "' and mdp='" + mdp + "'");
		
		System.out.println(array.get(0).toString());
		return array.isEmpty() ? 0 :(Integer.parseInt(array.get(0).get(0).toString()));

	}

	public int getLastIndex(String table, String selector) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select max(" + selector + ") from " + table);
		int index = Integer.parseInt(array.get(0).get(0).toString()) + 1;
		return index;
	}

	public ProduitInventaire getProduitInventaire(int idProduit) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from inventaire where idproduit=" + idProduit + "");
		ProduitInventaire product = null;

		for (ArrayList<Object> row : array) {
			product = new ProduitInventaire(Integer.parseInt(row.get(0).toString()), row.get(1).toString(),
					row.get(2).toString(), Integer.parseInt(row.get(3).toString()),
					Float.parseFloat(row.get(4).toString()));
		}
		return product;
	}

	public Client getClient(int clientId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from client where idclient=" + clientId + "");
		Client c = null;
		for (ArrayList<Object> row : array) {
			c = new Client(Integer.parseInt(row.get(0).toString()), row.get(1).toString(), row.get(2).toString(),
					row.get(3).toString(), row.get(4).toString(), row.get(5).toString(),
					row.get(6).toString() == "1" ? true : false, getClientAdresses(clientId),
					getClientPromos(clientId));
		}
		return c;
	}

	public Admin getAdmin(int idAdmin) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from admin where idadmin=" + idAdmin + "");
		Admin c = null;
		for (ArrayList<Object> row : array) {
			c = new Admin(Integer.parseInt(row.get(0).toString()), row.get(1).toString(), row.get(2).toString(),
					row.get(3).toString(), row.get(4).toString());
		}
		ResQ adCl = ConnectionBD.getData(con, "select * from adminclient where idadmin=" + idAdmin + "");

		c.setAdmin_client(
				(ArrayList<AdminHist>) (adCl.stream()
						.map(s -> new AdminHist(Integer.parseInt(s.get(0).toString()),
								s.get(1) instanceof String ? s.get(1).toString()
										: Integer.parseInt(s.get(0).toString()),
								(Date) s.get(2)))
						.collect(Collectors.toList())));

		ResQ adCl2 = ConnectionBD.getData(con, "select * from admincommande where idadmin=" + idAdmin + "");

		c.setAdmin_commande(
				(ArrayList<AdminHist>) (adCl2.stream()
						.map(s -> new AdminHist(Integer.parseInt(s.get(0).toString()),
								s.get(1) instanceof String ? s.get(1).toString()
										: Integer.parseInt(s.get(0).toString()),
								(Date) s.get(2)))
						.collect(Collectors.toList())));

		ResQ adCl3 = ConnectionBD.getData(con, "select * from admininventaire where idadmin=" + idAdmin + "");

		c.setAdmin_inventaire(
				(ArrayList<AdminHist>) (adCl3.stream()
						.map(s -> new AdminHist(Integer.parseInt(s.get(0).toString()),
								s.get(1) instanceof String ? s.get(1).toString()
										: Integer.parseInt(s.get(0).toString()),
								(Date) s.get(2)))
						.collect(Collectors.toList())));

		ResQ adCl4 = ConnectionBD.getData(con, "select * from adminimage where idadmin=" + idAdmin + "");

		c.setAdmin_image(
				(ArrayList<AdminHist>) (adCl4.stream()
						.map(s -> new AdminHist(Integer.parseInt(s.get(0).toString()),
								s.get(1) instanceof String ? s.get(1).toString()
										: Integer.parseInt(s.get(0).toString()),
								(Date) s.get(2)))
						.collect(Collectors.toList())));

		// 2Darraylist --map-> Stream<<AdminHist>> -Collector.toList-> cast
		// ArrayList<AdminHist>->> ajout dans objet Admin

		return c;
	}

	public ArrayList<Adresse> getClientAdresses(int clientId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from adresse where idclient=" + clientId + "");
		ArrayList<Adresse> adressList = new ArrayList<Adresse>();

		for (ArrayList<Object> row : array) {
			adressList.add(new Adresse(Integer.parseInt(row.get(0).toString()), Integer.parseInt(row.get(1).toString()),
					row.get(2).toString(), row.get(3).toString()));
		}
		return adressList;
	}

	public ArrayList<CodePromo> getClientPromos(int clientId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from codepromo where idclient=" + clientId + "");
		ArrayList<CodePromo> codeList = new ArrayList<CodePromo>();

		for (ArrayList<Object> row : array) {
			codeList.add(new CodePromo(Integer.parseInt(row.get(0).toString()), row.get(1).toString(),
					Float.parseFloat(row.get(2).toString()), Integer.parseInt(row.get(3).toString()),
					Integer.parseInt(row.get(4).toString())));
		}
		return codeList;
	}

	public ArrayList<Commande> getClientCommande(int clientId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from commande where idclient=" + clientId + "");
		ArrayList<Commande> codeList = new ArrayList<Commande>();

		for (ArrayList<Object> row : array) {
			codeList.add(new Commande(Integer.parseInt(row.get(0).toString()), Integer.parseInt(row.get(1).toString()),
					Integer.parseInt(row.get(2).toString()), (Date) row.get(3), Float.parseFloat(row.get(4).toString()),
					Integer.parseInt(row.get(5).toString()), row.get(6).toString(), row.get(7).toString(),
					row.get(8).toString(), getCommmandeImpression(Integer.parseInt(row.get(9).toString()))));
		}
		return codeList;
	}

	public ArrayList<CommandeImpression> getCommmandeImpression(int commandeId) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from commande_impression where idcommande=" + commandeId + "");
		ArrayList<CommandeImpression> codeList = new ArrayList<CommandeImpression>();

		for (ArrayList<Object> row : array) {
			codeList.add(new CommandeImpression(getImpression(Integer.parseInt(row.get(1).toString())),
					Integer.parseInt(row.get(2).toString())));
		}
		return codeList;
	}

	public Impression getImpression(int idImpression) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from impression where idimpression=" + idImpression + "");
		Impression codeList = null;

		for (ArrayList<Object> row : array) {
			codeList = new Impression(Integer.parseInt(row.get(0).toString()), Integer.parseInt(row.get(1).toString()),
					row.get(2).toString(), getPhotoImpression(idImpression));
			// System.out.println(row.get(3).toString());
			codeList.setTypeImpression(getTypeImpression(idImpression, row.get(3).toString()));
		}
		return codeList;
	}

	private TypeImpression getTypeImpression(int idImpression, String type) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + type + " where idimpression=" + idImpression + "");

		TypeImpression ti = null;
		HashMap<String, Object> attr;

		int cols = rs.getMetaData().getColumnCount();

		while (rs.next()) {
			attr = new HashMap<String, Object>();
			for (int i = 1; i < cols; i++) {
				attr.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1));
			}

			ti = new TypeImpression(type, attr);
		}
		// Close the result set, statement and the connection
		rs.close();
		stmt.close();

		return ti;
	}

	public ArrayList<Image> getClientImages(int clientId) throws SQLException {
		ResQ array = ConnectionBD.getData(con,
				"select chemin,idClient,partager from Image where idclient=" + clientId + "");
		ArrayList<Image> imageList = new ArrayList<Image>();

		for (ArrayList<Object> row : array) {
			imageList.add(new Image(row.get(0).toString(), Integer.parseInt(row.get(1).toString()),
					row.get(2).toString() == "1" ? true : false));
		}
		return imageList;
	}

	/** Je veux afficher les photos d'une impression **/
	public ArrayList<PhotoImpression> getPhotoImpression(int idImpression) throws SQLException {
		ResQ array = ConnectionBD.getData(con,
				"select * from photo_impression where idImpression=" + idImpression + "");
		ArrayList<PhotoImpression> codeList = new ArrayList<PhotoImpression>();

		for (ArrayList<Object> row : array) {
			codeList.add(new PhotoImpression(Integer.parseInt(row.get(1).toString()),
					getPhoto(Integer.parseInt(row.get(0).toString())), row.get(2).toString()));
		}
		return codeList;
	}

	public Photo getPhoto(int idPhoto) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from photo where idphoto=" + idPhoto + "");
		Photo codeList = null;

		for (ArrayList<Object> row : array) {
			codeList = new Photo(Integer.parseInt(row.get(0).toString()), row.get(1).toString(), row.get(2).toString(),
					row.get(3).toString());
		}
		return codeList;
	}

	/**
	 * Je veux afficher les impressions du client
	 * 
	 * @throws SQLException
	 * @throws NumberFormatException
	 **/
	public ArrayList<Impression> getClientImpression(int idclient) throws NumberFormatException, SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from impression where idclient=" + idclient + "");
		ArrayList<Impression> codeList = new ArrayList<Impression>();

		for (ArrayList<Object> row : array) {
			codeList.add(
					new Impression(Integer.parseInt(row.get(0).toString()), Integer.parseInt(row.get(1).toString()),
							row.get(2).toString(), getPhotoImpression(Integer.parseInt(row.get(0).toString()))));
			codeList.get(codeList.size() - 1).setTypeImpression(
					getTypeImpression(Integer.parseInt(row.get(0).toString()), row.get(3).toString()));
			
		}
		return codeList;
	}

	public Image getImage(String chemin) throws SQLException {
		ResQ array = ConnectionBD.getData(con, "select * from image where chemin='" + chemin + "'");
		Image codeList = null;

		for (ArrayList<Object> row : array) {
			codeList = new Image(row.get(0).toString(), Integer.parseInt(row.get(1).toString()), row.get(2).toString(),
					row.get(3).toString() == "1" ? true : false, (Date) row.get(4),
					Integer.parseInt(row.get(5).toString()));
		}
		return codeList;
	}


	public void addClient(Client client) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();

		values.add(getLastIndex("client", "idclient") + "");
		values.add(client.getMail());
		values.add(client.getNom());
		values.add(client.getPrenom());
		values.add(client.getMdp());
		values.add(client.getTelephone());
		values.add(client.getActif() == true ? "1" : "0");

		ConnectionBD.addData(con, "client", values);
	}

	public void deleteClient(int idClient) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();

		cond.add(new Tuple("idclient", idClient + ""));

		ConnectionBD.deleteData(con, "client", cond);
	}

	public void addImage(Image image) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();

		values.add(image.getChemin());
		values.add(image.getIdClient() + "");
		values.add(image.getResolution());
		values.add(image.isPartager() ? "1" : "0");

		String dateS = new SimpleDateFormat("dd-MMM-yy").format(image.getDateUtilisation()).toUpperCase();

		 System.out.println(dateS);
		values.add(dateS);
		
		values.add(image.getFileAttente() + "");

		ConnectionBD.addData(con, "image", values);
	}

	public void deleteImage(String chemin) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();

		cond.add(new Tuple("chemin", chemin + ""));

		ConnectionBD.deleteData(con, "image", cond);
	}

	public void addPhoto(Photo photo) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();

		values.add(getLastIndex("photo", "idphoto") + "");
		values.add(photo.getChemin());
		values.add(photo.getCommentaire());
		values.add(photo.getTypeRetouche());

		// String dateS = new
		// SimpleDateFormat("dd-MMM-yy").format(image.getDateUtilisation());
		ConnectionBD.addData(con, "photo", values);
	}

	public void addImpression(Impression impression) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		TypeImpression ti = impression.getTypeImpression();

		values.add(getLastIndex("impression", "idimpression") + "");
		values.add(impression.getIdClient() + "");
		values.add(impression.getNom());
		values.add(ti.type.name());

		// String dateS = new
		// SimpleDateFormat("dd-MMM-yy").format(image.getDateUtilisation());
		// ConnectionBD.addData(con, "impression", values);
		Statement stmt = con.createStatement();
		String colPool = " (IDIMPRESSION ,";
		String valPool = " ('" + impression.getIdImpression() + "' ,";
		for (Entry<String, Object> e : ti.attributes.entrySet()) {
			colPool += "" + e.getKey() + " ,";
			valPool += "'" + e.getValue() + "' ,";
		}
		colPool = colPool.substring(0, colPool.length() - 1) + ")";
		valPool = valPool.substring(0, valPool.length() - 1) + ")";
		String query = "INSERT INTO " + ti.type.name() + colPool + " VALUES" + valPool;
		stmt.executeUpdate(query);
		// Close the result set, statement and the connection

		stmt.close();
	}

	public void addPhotoImpression(PhotoImpression pi) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();

		values.add(pi.photo.getIdPhoto() + "");
		values.add(pi.idImpression + "");
		values.add(pi.specPart);

		// String dateS = new
		// SimpleDateFormat("dd-MMM-yy").format(image.getDateUtilisation());
		ConnectionBD.addData(con, "photo_impression", values);
	}

	public void addCommandeImpression(int idCommande, CommandeImpression ci) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();

		values.add(idCommande + "");
		values.add(ci.impression.getIdImpression() + "");
		values.add(ci.quantite + "");

		// String dateS = new
		// SimpleDateFormat("dd-MMM-yy").format(image.getDateUtilisation());
		ConnectionBD.addData(con, "commande_impression", values);
	}

	public int addCommande(Commande c) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		int newindex = getLastIndex("commande", "idcommande");
		
		values.add(newindex + "");
		values.add(c.getIdClient() + "");
		values.add(c.getIdAdresse() +"");
		String dateS = new SimpleDateFormat("dd-MMM-yy").format(c.getDatePaiement());
		values.add(dateS);
		values.add(c.getMontant() + "");
		values.add(c.isHistorise() ? "1" : "0");
		values.add(c.getRenduPdf());
		values.add(c.getStatut());
		values.add(c.getModeLivraison());

		ConnectionBD.addData(con, "commande", values);
		
		return newindex;
	}

	public void addAdresse(Adresse s) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();

		values.add(getLastIndex("adresse", "idadresse") + "");
		values.add(s.getIdClient() + "");
		values.add(s.getNomAdresse());
		values.add(s.getAdresse());

		ConnectionBD.addData(con, "adresse", values);
	}



	/** DELETE STUFF -------------------------------- **/

	public void deletePhotoImpression(PhotoImpression p) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();

		cond.add(new Tuple("idImpression", p.idImpression + ""));
		cond.add(new Tuple("idPhoto", p.photo.getIdPhoto() + ""));

		ConnectionBD.deleteData(con, "photo_impression", cond);
	}

	public void deleteCommandeImpression(int idCommande, CommandeImpression c) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();

		cond.add(new Tuple("idCommande", idCommande + ""));
		cond.add(new Tuple("idImpression", c.impression.getIdImpression() + ""));

		ConnectionBD.deleteData(con, "commande_impression", cond);
	}

	public void deleteCommande(int idCommande) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();
		cond.add(new Tuple("idCommande", idCommande + ""));
		ConnectionBD.deleteData(con, "commande", cond);
	}

	public void deleteAdresse(Adresse a) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();
		cond.add(new Tuple("idclient", a.getIdClient() + ""));
		cond.add(new Tuple("nomadresse", a.getNomAdresse() + ""));
		ConnectionBD.deleteData(con, "adresse", cond);
	}

	public void deleteCodePromo(CodePromo c) throws SQLException {
		ArrayList<Tuple> cond = new ArrayList<Tuple>();
		cond.add(new Tuple("idCode", c.getCode() + ""));
		ConnectionBD.deleteData(con, "codepromo", cond);
	}

	/** UPDATE PART ---------------------------------------- **/

	public void updateClient(int idClient, Tuple... values) throws SQLException {
		ArrayList<Tuple> conds = new ArrayList<Tuple>();
		conds.add(new Tuple("idclient", idClient + ""));

		ConnectionBD.updateData(con, "client", conds, new ArrayList<Tuple>(Arrays.asList(values)));
	}

	public void updateInventaire(int idProduit, Tuple... values) throws SQLException {
		ArrayList<Tuple> conds = new ArrayList<Tuple>();
		conds.add(new Tuple("idProduit", idProduit + ""));
		ConnectionBD.updateData(con, "inventaire", conds, new ArrayList<Tuple>(Arrays.asList(values)));
	}

	public void updateCommandeImpression(int idCommande, int idImpression, Tuple... values) throws SQLException {
		ArrayList<Tuple> conds = new ArrayList<Tuple>();
		conds.add(new Tuple("idCommande", idCommande + ""));
		conds.add(new Tuple("idImpression", idImpression + ""));
		ConnectionBD.updateData(con, "commande_impression", conds, new ArrayList<Tuple>(Arrays.asList(values)));
	}

	public void updateImage(String chemin, Tuple... values) throws SQLException {
		ArrayList<Tuple> conds = new ArrayList<Tuple>();
		conds.add(new Tuple("chemin", chemin + ""));
		ConnectionBD.updateData(con, "image", conds, new ArrayList<Tuple>(Arrays.asList(values)));
	}

	// update commande

	public void updateCommande(int idCommande, Tuple... values) throws SQLException {
		ArrayList<Tuple> conds = new ArrayList<Tuple>();
		conds.add(new Tuple("idcommande", idCommande + ""));
		ConnectionBD.updateData(con, "commande", conds, new ArrayList<Tuple>(Arrays.asList(values)));
	}

	/** ------- MISC PART ----------- **/
	public float prixImpression(Impression im) throws SQLException {

		int idproduit = Integer.parseInt(im.getTypeImpression().attributes.get("IDPRODUIT") + "" );
		ProduitInventaire pi = getProduitInventaire(idproduit);
		return pi.getPrix();
	}	

	public ArrayList<Tuple> getStatImages() throws SQLException {
		ResQ a = ConnectionBD.getData(con,
				"select chemin, count(idimpression) as coutImp from CLIENT_USE_IMAGE group by chemin order by coutImp desc");
		return (ArrayList<Tuple>) a.stream().map(s -> new Tuple(s.get(0).toString(), s.get(1).toString()))
				.collect(Collectors.toList());
	}
}
