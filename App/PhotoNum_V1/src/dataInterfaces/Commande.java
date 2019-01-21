package dataInterfaces;

import java.util.Date;

import enums.ModeLivraison;
import enums.Statut;

public class Commande {

	private int idCmd, idClient;
	private Date datePaiement;
	private float montant;
	private ModeLivraison modeLivraison;
	private Statut statut;
	private boolean historise;
	private String renduPdf;

	public Commande(int idCmd, int idClient, Date datePaiement, float montant, ModeLivraison modeLivraison,
			Statut statut) {
		this.idCmd = idCmd;
		this.idClient = idClient;
		this.datePaiement = datePaiement;
		this.modeLivraison = modeLivraison;
		this.montant = montant;
		this.statut = statut;
		this.historise = true;
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

	public ModeLivraison getModeLivraison() {
		return modeLivraison;
	}

	public void setModeLivraison(ModeLivraison modeLivraison) {
		this.modeLivraison = modeLivraison;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
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
