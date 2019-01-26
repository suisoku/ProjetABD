package enums;

public enum Resolution {
	TWO_K("2K"),
	FOUR_K("4K"),
	EIGHT_K("8K");
	
	private final String resolution;
	
	private Resolution(final String resolution) {
		this.resolution = resolution;
	}
	
	@Override
	public String toString() {
		return this.resolution;
	}
}
