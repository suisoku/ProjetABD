package dataInterfaces;

public class CodePromo {
	private int  idcode, idClient;
	private float reduction;
	private boolean used;
	private String code;
	
	public CodePromo(int idcode, String code, float reduction, int usedB , int idClient) {
		this.code = code;
		this.reduction = reduction;
		this.idClient = idClient;
		this.used = (usedB == 1) ? true : false;
		this.idcode = idcode;
	}
	
	
	/******* Getters and Setters **********/
	/**************************************/
	
	
	public String getCode() {
		return code;
	}

	public int getIdcode() {
		return idcode;
	}


	public void setIdcode(int idcode) {
		this.idcode = idcode;
	}


	public void setCode(String code) {
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
