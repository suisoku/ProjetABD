package dataInterfaces;

import java.util.Date;

public class Image {
	private String chemin;
	private int idClient , fileAttente;
	private String resolution;
	private boolean partager;
	private Date dateUtilisation;
	
	public Image(String chemin, boolean partager ) {
		this.chemin = chemin;
		this.partager = partager;
	}

	public Image(String chemin, int idClient, boolean partager ) {
		this.chemin = chemin;
		this.idClient = idClient;
		this.partager = partager;
	}

	public Image(String chemin, int idClient, String resolution, boolean partager, Date dateUtilisation, int fileAttente ) {
		this.chemin = chemin;
		this.idClient = idClient;
		this.resolution = resolution;
		this.partager = partager;
		this.dateUtilisation = dateUtilisation;
		this.fileAttente = fileAttente;
	}
	
	
	
	public int getFileAttente() {
		return fileAttente;
	}



	public void setFileAttente(int fileAttente) {
		this.fileAttente = fileAttente;
	}



	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public boolean isPartager() {
		return partager;
	}

	public void setPartager(boolean partager) {
		this.partager = partager;
	}

	public Date getDateUtilisation() {
		return dateUtilisation;
	}

	public void setDateUtilisation(Date dateUtilisation) {
		this.dateUtilisation = dateUtilisation;
	}
}
