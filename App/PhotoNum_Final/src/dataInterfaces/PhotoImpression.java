package dataInterfaces;

public class PhotoImpression {
	
	public int idImpression;
	public Photo photo;
	public String  specPart;
	public int quantite;
	
	public PhotoImpression(int idImpression, Photo photo , String spec) {
		super();
		this.idImpression = idImpression;
		this.photo = photo;
		this.specPart = spec;
	}
	
	public PhotoImpression(int idImpression, Photo photo , String spec , int quantite) {
		super();
		this.idImpression = idImpression;
		this.photo = photo;
		this.specPart = spec;
		this.quantite = quantite;
	}
	
	
	

}
