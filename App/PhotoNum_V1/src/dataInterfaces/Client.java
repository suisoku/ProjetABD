package dataInterfaces;

public class Client {
	
	private int idClient;
	private String nom, prenom, mail, telephone;
	
	public Client (int id, String nom, String prenom, String mail, String telephone ) {
		this.setIdClient(id);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setMail(mail);
		this.setTelephone(telephone);
	}
	
	/******* Getters and Setters **********/
	/**************************************/
	
	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
