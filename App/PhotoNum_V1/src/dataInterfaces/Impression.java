package dataInterfaces;

public class Impression {
	
	private int idImpression, idClient;
	private String nom;
	
	public Impression(int idImpression, int idClient, String nom) {
		this.idImpression = idImpression;
		this.idClient = idClient;
		this.nom = nom;
	}

	public int getIdImpression() {
		return idImpression;
	}

	public void setIdImpression(int idImpression) {
		this.idImpression = idImpression;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
}
