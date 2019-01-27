package dataInterfaces;

/** Attributes are public no need of getters and settters **/
public class CommandeImpression {
	public Impression impression;
	public int quantite;
	
	public CommandeImpression(Impression impression, int quantite) {
		super();
		this.impression = impression;
		this.quantite = quantite;
	}
	

}
