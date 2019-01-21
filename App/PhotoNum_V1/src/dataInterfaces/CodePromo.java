package dataInterfaces;

public class CodePromo {
	private int code, reduction, idClient;
	private boolean used;
	
	public CodePromo(int code, int reduction, int idClient) {
		this.code = code;
		this.reduction = reduction;
		this.idClient = idClient;
		this.used = false;
	}
	
	
	/******* Getters and Setters **********/
	/**************************************/
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getReduction() {
		return reduction;
	}

	public void setReduction(int reduction) {
		this.reduction = reduction;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
