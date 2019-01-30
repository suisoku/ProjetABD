package dataInterfaces;

import java.util.HashMap;


// Free Class Type to model custom attributes of a given Impression

public class TypeImpression{
	public enum TypesI {CADRE, AGENDA, CALENDRIER , ALBUM, TIRAGE}
	public TypesI type;
	public HashMap<String , Object> attributes;
	
	public TypeImpression(String type, HashMap<String, Object> attr) {
		this.type = TypesI.valueOf(type.toUpperCase());
		this.attributes = attr;
	}
		
}
