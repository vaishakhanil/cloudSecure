package com.se.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.se.dao.SecretKeysDAO;
import com.se.pojo.SecretKeys;
import com.se.util.MySQLUtility;

public class SecretKeysDAOImpl implements SecretKeysDAO {

	@Override
	public void add(SecretKeys sk) {
		Connection con = null;

		try {
			con = MySQLUtility.connect();
			PreparedStatement ps = con.prepareStatement("insert into secretkeys values (?,?,?)");
			ps.setString(1, sk.getKid());
			ps.setString(2, sk.getKey_text());
			ps.setString(3, sk.getEmail());
			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<SecretKeys> getSecretKeyByEmail(String email) {
		Connection con = null;
		List<SecretKeys> result = new ArrayList<>();
		try {
			con = MySQLUtility.connect();
			ResultSet rs = con.createStatement().executeQuery("select * from secretkeys  where email='" + email + "' ");
			while (rs.next()) {
				SecretKeys sk = new SecretKeys();
				sk.setEmail(rs.getString("email"));
				sk.setKey_text(rs.getString("key_text"));
				sk.setKid(rs.getString("kid"));
				result.add(sk);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public SecretKeys getSecretKeyByID(String kid) {
		Connection con = null;
		SecretKeys result = new SecretKeys();
		try {
			con = MySQLUtility.connect();
			ResultSet rs = con.createStatement().executeQuery("select * from secretkeys  where kid='" + kid + "' ");
			rs.next();
			result.setEmail(rs.getString("email"));
			result.setKey_text(rs.getString("key_text"));
			result.setKid(rs.getString("kid"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void deleteSecretKey(String kid) {
		Connection con = null;

		try {
			con = MySQLUtility.connect();
			con.createStatement().execute("delete from secretkeys where kid='" + kid + "' ");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
