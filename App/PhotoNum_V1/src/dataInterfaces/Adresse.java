package dataInterfaces;

public class Adresse {
	
	private String nomAdresse, adresse;
	private int idClient, idAdresse;
	
	public Adresse (int idAdresse, int idClient , String nomAdresse, String adresse ) {
		this.setAdresse(adresse);
		this.setNomAdresse(nomAdresse);
		this.setIdClient(idClient);
		this.idAdresse = idAdresse;
	}
	
	
	/******* Getters and Setters **********/
	/**************************************/
	
	public int getIdClient() {
		return idClient;
	}

	public int getIdAdresse() {
		return idAdresse;
	}

	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}


	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getNomAdresse() {
		return nomAdresse;
	}

	public void setNomAdresse(String nomAdresse) {
		this.nomAdresse = nomAdresse;
	}



}
