package dataInterfaces;

import java.util.ArrayList;

public class Client {
	
	private int idClient;
	private String nom, prenom, mail, telephone, mdp;
	private ArrayList<Adresse> adressList;
	private ArrayList<CodePromo> codeList;
	
	public Client (int id, String mail, String nom, String prenom, String mdp, String telephone,
			ArrayList<Adresse> adressList ,  ArrayList<CodePromo> codeList) {
		this.setIdClient(id);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setMail(mail);
		this.setTelephone(telephone);
		this.setMdp(mdp);
		this.adressList = adressList;
		this.codeList = codeList;
		
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

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getMdp() {
		return mdp;
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

	public ArrayList<Adresse> getAdressList() {
		return adressList;
	}

	public void setAdressList(ArrayList<Adresse> adressList) {
		this.adressList = adressList;
	}

	public ArrayList<CodePromo> getCodeList() {
		return codeList;
	}

	public void setCodeList(ArrayList<CodePromo> codeList) {
		this.codeList = codeList;
	}
	
	
	

}
