package com.ftninformatika.bisis.circ.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class GeneratePins {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/bisis", "bisis","bisis"); 
			connection.setAutoCommit(false);
			
			Statement stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery("select max(sys_id) from users");
			rset.next();
			int maxsysid = rset.getInt(1);

			PreparedStatement pstmt = connection.prepareStatement("update users set pass =? where sys_id = ?");
			
			boolean exist= true;
			String pin = "";
			
			for(int i=1; i<=maxsysid; i++){
//				while (exist){
//					pin = Utils.generatePin();
//					rset = stmt.executeQuery("select sys_id from users u where u.pass = '" + pin + "'");
//					if (rset.next()){
//						exist = true;
//					}else{
//						exist = false;
//					}
//						
//				}
				pin = Utils.generatePin();
				pstmt.setString(1, pin);
				pstmt.setInt(2, i);
				pstmt.execute();
				exist = true;
				if (i%100 == 0)
			          System.out.print(i+ " ");
			}
			connection.commit();
			rset.close();
			pstmt.close();
			stmt.close();
			connection.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		

	}

}
