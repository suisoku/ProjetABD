package dataInterfaces;

public class PhotoImpression {
	
	public int idImpression;
	public Photo photo;
	public String  specPart;
	
	public PhotoImpression(int idImpression, Photo photo , String spec) {
		super();
		this.idImpression = idImpression;
		this.photo = photo;
		this.specPart = spec;
	}
	
	

}
