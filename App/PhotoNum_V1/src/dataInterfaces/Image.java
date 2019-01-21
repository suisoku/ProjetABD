package dataInterfaces;

import java.util.Date;

import enums.Resolution;

public class Image {
	private String chemin;
	private int idClient;
	private Resolution resolution;
	private boolean partager;
	private Date dateUtilisation;

	public Image(String chemin, int idClient, Resolution resolution, boolean partager, Date dateUtilisation) {
		this.chemin = chemin;
		this.idClient = idClient;
		this.resolution = resolution;
		this.partager = partager;
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

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
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
