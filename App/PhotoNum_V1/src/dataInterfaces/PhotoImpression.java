package dataInterfaces;

public class PhotoImpression {
	
	private int idPhoto, idImpression;
	private String specificationParticuliere;
	
	
	public PhotoImpression(int idPhoto, int idImpression, String specificationParticuliere) {
		this.idPhoto = idPhoto;
		this.idImpression = idImpression;
		this.specificationParticuliere = specificationParticuliere;
	}

	
	public int getIdPhoto() {
		return idPhoto;
	}

	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}

	public int getIdImpression() {
		return idImpression;
	}

	public void setIdImpression(int idImpression) {
		this.idImpression = idImpression;
	}

	public String getSpecificationParticuliere() {
		return specificationParticuliere;
	}

	public void setSpecificationParticuliere(String specificationParticuliere) {
		this.specificationParticuliere = specificationParticuliere;
	}
	
	
}
