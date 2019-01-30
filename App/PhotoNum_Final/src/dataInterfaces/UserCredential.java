package dataInterfaces;

public class UserCredential {
	private int idClient;
	
	public void setCredentials(int id) {
		this.idClient = id;
	}
	
	public int getcredentials() { return this.idClient;}
}
