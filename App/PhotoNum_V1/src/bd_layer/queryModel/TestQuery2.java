package bd_layer.queryModel;

import java.sql.SQLException;

import dataInterfaces.Client;

public class TestQuery2 {
	public static void main(String[] args) throws SQLException {
		QueryMethods cq = new QueryMethods();
		
		System.out.println("--------ADD CLIENT------------");
			Client cAdd = cq.getClient(1);
			cAdd.setIdClient(12);
			cAdd.setMail("dddddddddddd@ooo.fr");
			//cq.addClient(cAdd);
		System.out.println("------------------------------");
		
		System.out.println("--------DELETE CLIENT------------");
			cq.deleteClient(12);
		System.out.println("------------------------------");
		
	}
}
