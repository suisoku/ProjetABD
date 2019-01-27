package bd_layer.queryModel;

import java.sql.SQLException;
import java.util.ArrayList;

import bd_layer.Tuple;
import dataInterfaces.*;

public class TestQuery2 {
	public static void main(String[] args) throws SQLException {
		QueryMethods cq = new QueryMethods();
		
		System.out.println("--------ADD Impression------------");
			Impression im = cq.getImpression(5);
			im.setIdImpression(6);
			im.setNom("barkouk");
			//cq.addImpression(im);
		System.out.println("------------------------------");
		
		System.out.println("--------DELETE Impression------------");
			//cq.deleteImpression(6, "ALBUM");
		System.out.println("------------------------------");
		
		System.out.println("--------ADD photo impression------------");
			ArrayList<PhotoImpression> pis = cq.getPhotoImpression(5);
			PhotoImpression pi = pis.get(0);
			pi.idImpression = 2;
			//cq.addPhotoImpression(pi);
		System.out.println("------------------------------");
			//cq.deletePhotoImpression(pi);
		System.out.println("--------DELETE photo impression------------");
			//cq.deleteImage("/bro/tmp/2.jpg");
		System.out.println("------------------------------");
		
		
		System.out.println("--------ADD commande impression------------");
			ArrayList<CommandeImpression> cis = cq.getCommmandeImpression(1);
			CommandeImpression ci = cis.get(0);
			ci.impression.setIdImpression(3);
			//cq.addCommandeImpression(1, ci);
		System.out.println("------------------------------");

		System.out.println("--------DELETE commande impression------------");
			//cq.deleteCommandeImpression(1, ci);
		System.out.println("------------------------------");		
		
		System.out.println("--------ADD commande------------");
		ArrayList<Commande> cc = cq.getClientCommande(1);
		Commande ccQ = cc.get(0);
		ccQ.setIdCmd(10);
		//cq.addCommande(ccQ);
		System.out.println("------------------------------");
		
		System.out.println("--------DELETE commande-----------");
		//cq.deleteCommande(10);
		System.out.println("------------------------------");
		
		System.out.println("--------GET product----------");
			ProduitInventaire ppi = cq.getProduitInventaire(4);
			System.out.println(ppi.getIdProduit() +" | " + ppi.getCaracteristique() +" | " + ppi.getNomCommercial() 
			+" | " + ppi.getPrix() +" | " + ppi.getStock());
		System.out.println("------------------------------");
		
		System.out.println("--------Update Client----------");
			cq.updateClient(3, new Tuple("MDP","bayern38") , new Tuple("TELEPHONE","1231238889"));
		System.out.println("------------------------------");
	}
}
