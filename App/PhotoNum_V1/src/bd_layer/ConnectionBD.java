package bd_layer;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectionBD {

    private static final String configurationFile = "src/bd_layer/BD.properties";
    private static Connection conn;
    
    public static Connection getConnection() {
        try {
            String jdbcDriver, dbUrl, username, password;
            DatabaseAccessProperties dap = new DatabaseAccessProperties(configurationFile);
            jdbcDriver = dap.getJdbcDriver();
            dbUrl = dap.getDatabaseUrl();
            username = dap.getUsername();
            password = dap.getPassword();

            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbUrl, username, password);
            SQLWarningsExceptions.printWarnings(conn);
            
            return conn;
            
        } catch (SQLException se) {
            SQLWarningsExceptions.printExceptions(se);
            return null;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
        
    public static ResQ getData(Connection con , String query) throws SQLException {
		ResQ array = new ResQ();
		ArrayList<Object> row =  null;
		
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        int cols = rs.getMetaData().getColumnCount();
        
        while (rs.next()) {
        	row = new ArrayList<Object>();
        	
        	for(int i = 1 ; i <= cols ; i++) {
        		row.add(rs.getObject(i));
        	}
        	array.add(row);
        }
        // Close the result set, statement and the connection
        rs.close();
        stmt.close();
        
        return array;
    }


}
