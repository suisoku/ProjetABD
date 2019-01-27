package dataInterfaces;

import java.util.ArrayList;

public class Impression {
	

	
	private int idImpression, idClient;
	private String nom;
	private ArrayList<PhotoImpression> photoImpression;
	
	private TypeImpression type;
	
	public Impression(int idImpression, int idClient, String nom , ArrayList<PhotoImpression> photos ) {
		this.idImpression = idImpression;
		this.idClient = idClient;
		this.nom = nom;
		this.photoImpression = photos;
	}
	
	

	public TypeImpression getTypeImpression() {
		return type;
	}


	public void setTypeImpression(TypeImpression type) {
		this.type = type;
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

	public ArrayList<PhotoImpression> getPhotoImpression() {
		return photoImpression;
	}

	public void setPhotoImpression(ArrayList<PhotoImpression> photoImpression) {
		this.photoImpression = photoImpression;
	}
	
	
	
}
