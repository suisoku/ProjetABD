package dataInterfaces;

public class CodePromo {
	private int code, idClient;
	private float reduction;
	private boolean used;
	
	public CodePromo(int code, float reduction, int usedB , int idClient) {
		this.code = code;
		this.reduction = reduction;
		this.idClient = idClient;
		this.used = (usedB == 1) ? true : false;
	}
	
	
	/******* Getters and Setters **********/
	/**************************************/
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public float getReduction() {
		return reduction;
	}

	public void setReduction(float reduction) {
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
