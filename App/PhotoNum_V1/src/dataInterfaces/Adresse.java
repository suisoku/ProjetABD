package dataInterfaces;

public class Adresse {
	
	private String nomAdresse, adresse;
	private int idClient;
	
	public Adresse (int idClient , String nomAdresse, String adresse ) {
		this.setAdresse(adresse);
		this.setNomAdresse(nomAdresse);
		this.setIdClient(idClient);
	}
	
	
	/******* Getters and Setters **********/
	/**************************************/
	
	public int getIdClient() {
		return idClient;
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
