package dataInterfaces;

import java.util.ArrayList;
import java.util.Date;

import enums.ModeLivraison;
import enums.Statut;

public class Commande {
	// statut and modelivraison
	private int idCmd, idClient;
	private Date datePaiement;
	private float montant;
	private String modeLivraison;
	private String statut;
	private boolean historise;
	private String renduPdf;
	private ArrayList<CommandeImpression> commandeImpressions;
	
	public Commande(int idCmd, int idClient, Date datePaiement, float montant,int historise, String rendu , 
			String statut, String modeLivraison, ArrayList<CommandeImpression> ci) 
	{
		this.idCmd = idCmd;
		this.idClient = idClient;
		this.datePaiement = datePaiement;
		this.modeLivraison = modeLivraison;
		this.montant = montant;
		this.statut = statut;
		this.historise = (historise == 1) ? true : false;
		this.commandeImpressions= ci ;
		this.renduPdf = rendu;
	}

	public ArrayList<CommandeImpression> getCommandeImpressions() {
		return commandeImpressions;
	}

	public void setCommandeImpressions(ArrayList<CommandeImpression> commandeImpressions) {
		this.commandeImpressions = commandeImpressions;
	}

	public int getIdCmd() {
		return idCmd;
	}

	public void setIdCmd(int idCmd) {
		this.idCmd = idCmd;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public Date getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public String getModeLivraison() {
		return modeLivraison;
	}

	public void setModeLivraison(String modeLivraison) {
		this.modeLivraison = modeLivraison;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public boolean isHistorise() {
		return historise;
	}

	public void setHistorise(boolean historise) {
		this.historise = historise;
	}

	public String getRenduPdf() {
		return renduPdf;
	}

	public void setRenduPdf(String renduPdf) {
		this.renduPdf = renduPdf;
	}
}
