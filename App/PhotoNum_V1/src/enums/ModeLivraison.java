package enums;

public enum ModeLivraison {
	PointRelais("PointRelais"),
	Domicile("Domicile");
	
	private final String mode;
	
	private ModeLivraison(final String mode) {
		this.mode = mode;
	}
	
	@Override
	public String toString() {
		return this.mode;
	}
}
