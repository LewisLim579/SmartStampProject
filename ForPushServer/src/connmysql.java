import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

	public class connmysql {
	    private Statement stat;
	    private ArrayList<String> aList;
	    
	    
	    
	    
	    public final ArrayList<String> testMySql() {
	      
	        
	   	 String dbUrl = "jdbc:mysql://filey.mooo.com:3306/android_api";
	        String id = "cs";
	        String pwd = "hansung08";
	        
	        
	        
	        try
	           {
	            Class.forName("com.mysql.jdbc.Driver");
	           }
	           catch (ClassNotFoundException e)
	           {
	            System.err.println("드라이버 연결 에러.");
	           }
	         
	        
	        /*
	        try {
	                   
	          Connection con = DriverManager.getConnection(dbUrl, id, pwd);
	          Statement stat = con.createStatement();
	        	        
	            // ResultSet 에 쿼리 결과 저장
	            ResultSet rs = stat.executeQuery("SELECT id FROM gcm");
	             
	            // ResultSet 에 담은 데이터를 어레이리스트에 추가 
	            while (rs.next()) {
	            	System.out.println("key   :  "+rs.getString(1)+"<br>");
	            }
	     
	        } catch (SQLException sqex) {
	        	System.out.println("SQLException: " + sqex.getMessage());
	        	System.out.println("SQLState: " + sqex.getSQLState());
	        }
	        */
	         
	        try {
	            Connection con = null;
	            con = DriverManager.getConnection(dbUrl, id, pwd);
	            stat = con.createStatement();
	        
	        
	            // ResultSet 에 쿼리 결과 저장
	            ResultSet rs = stat.executeQuery("SELECT id FROM gcm");
	             
	            aList = new ArrayList<String>();
	            // ResultSet 에 담은 데이터를 어레이리스트에 추가 
	            while (rs.next()) {
	                 aList.add(rs.getString(1));
	                 	            }
	            /*
	            while (rs.next()) {
	            	System.out.println("key   :  "+rs.getString(1)+"<br>");
	            }*/
	     
	        } catch (SQLException sqex) {
	            System.out.println("SQLException: " + sqex.getMessage());
	            System.out.println("SQLState: " + sqex.getSQLState());
	        }
	        
	        // 등록 ID ArrayList를 반환
	        return aList;
	     
	    }
	
}
