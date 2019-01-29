package bd_layer;

public class Tuple {
	public String name;
	public String value;
	
	public Tuple(String name , String value) {
		this.name = name;
		this.value = value;
	}
	
	public String toString() {
		return this.name+ " | " +this.value;
	}
}
