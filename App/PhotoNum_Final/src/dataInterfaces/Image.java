package dataInterfaces;

import java.util.Date;

public class Image {
	private String chemin;
	private int idClient;
	private String resolution;
	private boolean partager;
	private Date dateUtilisation;

	public Image(String chemin, int idClient, String resolution, int partager, Date dateUtilisation) {
		this.chemin = chemin;
		this.idClient = idClient;
		this.resolution = resolution;
		this.partager = (partager == 1) ? true : false;
		this.dateUtilisation = dateUtilisation;
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
