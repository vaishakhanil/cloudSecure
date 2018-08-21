package com.se.daoimpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.se.util.MySQLUtility;

public class UserLoginDAO {

	public void writecode(String email, String code) throws SQLException {
		Connection con = null;
		try {
			con = MySQLUtility.connect();
			con.createStatement().execute("insert into user_login values('" + email + "','" + code + "')");
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			con.close();
		}
	}

	public boolean verifycode(String email, String code) throws Exception {
		Connection con = null;
		boolean res = false;
		try {
			con = MySQLUtility.connect();
			ResultSet rs = con.createStatement().executeQuery(
					"select count(*) from user_login where email='" + email + "' and code='" + code + "' ");
			rs.next();
			int cnt = rs.getInt(1);
			if (cnt == 0) {
				res = false;
			} else {
				res = true;
				con.createStatement().execute("delete from user_login where email='" + email + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			con.close();
		}
		return res;

	}
}
