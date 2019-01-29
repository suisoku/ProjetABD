package dataInterfaces;

import java.util.ArrayList;

import bd_layer.Tuple;

public class Admin {

	private int idAdmin;
	private String nom;
	private String prenom;
	private String mail;
	private String mdp;
	private ArrayList<AdminHist> admin_client;
	private ArrayList<AdminHist> admin_commande;
	private ArrayList<AdminHist> admin_inventaire;
	private ArrayList<AdminHist> admin_image;
	
	public Admin(int idAdmin, String mail, String nom, String prenom , String mdp) {
		super();
		this.idAdmin = idAdmin;
		this.nom = nom;
		this.mail = mail;
		this.mdp = mdp;
		this.prenom = prenom;
	}

	
	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public ArrayList<AdminHist> getAdmin_client() {
		return admin_client;
	}

	public void setAdmin_client(ArrayList<AdminHist> admin_client) {
		this.admin_client = admin_client;
	}

	public ArrayList<AdminHist> getAdmin_commande() {
		return admin_commande;
	}

	public void setAdmin_commande(ArrayList<AdminHist> admin_commande) {
		this.admin_commande = admin_commande;
	}

	public ArrayList<AdminHist> getAdmin_inventaire() {
		return admin_inventaire;
	}

	public void setAdmin_inventaire(ArrayList<AdminHist> admin_inventaire) {
		this.admin_inventaire = admin_inventaire;
	}

	public ArrayList<AdminHist> getAdmin_image() {
		return admin_image;
	}

	public void setAdmin_image(ArrayList<AdminHist> admin_image) {
		this.admin_image = admin_image;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	
	
	
	
	
	 
	
	
	
}
