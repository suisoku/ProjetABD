package enums;

public enum Statut {
	
	EnCoursPreparation("EnCoursPreparation"),
	EnCoursLivraison("EnCoursLivraison"),
	Livre("Livre"),
	Annule("Annule");
	
	private final String statut;
	
	private Statut(final String statut) {
		this.statut = statut;
	}
	
	@Override
	public String toString() {
		return this.statut;
	}
}
