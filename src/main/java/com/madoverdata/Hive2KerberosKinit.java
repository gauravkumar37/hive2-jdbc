package com.madoverdata;

import java.sql.*;

/**
 * This class will not deal with Kerberos tickets in any way.
 * To use this class, the client must have installed the kerberos client libraries on the system.
 * Use kinit to get a ticket. Then execute this class.
 */
public class Hive2KerberosKinit {
	// refer to Cloudera's documentation for more info for Kerberos URL 
	private static final String CONNECTION_URL = "jdbc:hive2://fqdn_of_hiveserver2_host:10000;AuthMech=1;" +
			"KrbRealm=REALM;KrbHostFQDN=fqdn_of_hiveserver2_host;KrbServiceName=hive";
	static String JDBCDriver = "com.cloudera.hive.jdbc41.HS2Driver";

	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String query = "SELECT code, salary FROM default.sample_07 limit 5";
		try {
			Class.forName(JDBCDriver);
			con = DriverManager.getConnection(CONNECTION_URL);
			System.out.println("Got connection.");
			stmt = con.createStatement();
			System.out.println("Executing query...");
			rs = stmt.executeQuery(query);
			System.out.println("Results:");
			while (rs.next()) {
				String code = rs.getString("code");
				int salary = rs.getInt("salary");
				System.out.printf("%s, %d%n", code, salary);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException se4) {
				se4.printStackTrace();
			}
		}
	}
}
