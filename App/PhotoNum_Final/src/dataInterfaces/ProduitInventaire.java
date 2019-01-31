package dataInterfaces;

public class ProduitInventaire {
	
	private int idProduit , stock;
	private float prix;
	
	private String nomCommercial , caracteristique;
	
	public ProduitInventaire(int idProduit, String nomCommercial,  String caracteristique) {
		super();
		this.idProduit = idProduit;
		this.nomCommercial = nomCommercial;
		this.caracteristique = caracteristique;
	}

	public ProduitInventaire(int idProduit, String nomCommercial,  float prix) {
		super();
		this.idProduit = idProduit;
		this.nomCommercial = nomCommercial;
		this.prix = prix;
	}
	
	public ProduitInventaire(int idProduit, String nomCommercial,  String caracteristique, int stock, float prix) {
		super();
		this.idProduit = idProduit;
		this.stock = stock;
		this.prix = prix;
		this.nomCommercial = nomCommercial;
		this.caracteristique = caracteristique;
	}

	public ProduitInventaire() {}

	public int getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public String getNomCommercial() {
		return nomCommercial;
	}

	public void setNomCommercial(String nomCommercial) {
		this.nomCommercial = nomCommercial;
	}

	public String getCaracteristique() {
		return caracteristique;
	}

	public void setCaracteristique(String caracteristique) {
		this.caracteristique = caracteristique;
	}
	
	

}
