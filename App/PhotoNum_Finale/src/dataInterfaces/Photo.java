package dataInterfaces;

public class Photo {
	
	private int idPhoto;
	private String chemin, commentaire, typeRetouche;
	
	public Photo(int idPhoto, String chemin, String commentaire, String typeRetouche) {
		this.idPhoto = idPhoto;
		this.chemin = chemin;
		this.commentaire = commentaire;
		this.typeRetouche = typeRetouche;
	}

	public int getIdPhoto() {
		return idPhoto;
	}

	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getTypeRetouche() {
		return typeRetouche;
	}

	public void setTypeRetouche(String typeRetouche) {
		this.typeRetouche = typeRetouche;
	}
	
	

}
