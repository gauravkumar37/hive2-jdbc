package com.madoverdata;

import java.sql.*;

/**
 * This class will use the driver to get a Kerberos ticket. You do not need ther kerberos client utilities
 * installed on the system. Change the environment variables in the code accordingly.
 */
public class Hive2KerberosTGT {
	// refer to Cloudera's documentation for more info for Kerberos URL 
	private static final String CONNECTION_URL = "jdbc:hive2://fqdn_of_hiveserver2_host:10000;AuthMech=1;" +
			"KrbRealm=REALM;KrbHostFQDN=fqdn_of_hiveserver2_host;KrbServiceName=hive";
	static String JDBCDriver = "com.cloudera.hive.jdbc41.HS2Driver";

	public static void main(String[] args) throws Exception {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		System.setProperty("sun.security.krb5.debug", "true");
		System.setProperty("java.security.auth.login.config", "conf/kerberos_login_config.ini");
		System.setProperty("java.security.krb5.conf", "conf/krb5.ini");

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
