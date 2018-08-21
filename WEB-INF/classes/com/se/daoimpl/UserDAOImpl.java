package com.se.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.se.dao.UserDAO;
import com.se.pojo.User;
import com.se.util.MySQLUtility;

public class UserDAOImpl implements UserDAO {
	// String encoded = new String(Base64.getEncoder().encode(s.getBytes()));
	// String decoded = new String(Base64.getDecoder().decode(encoded));

	public List<String> getAllUsers() throws Exception {
		Connection con = null;
		List<String> result = new ArrayList<>();
		try {
			con = MySQLUtility.connect();
			ResultSet rs = con.createStatement().executeQuery("select * from user");
			while (rs.next()) {
				result.add(rs.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			con.close();
		}
		return result;

	}

	@Override
	public void register(User user) throws Exception {

		Connection con = null;
		try {
			con = MySQLUtility.connect();
			PreparedStatement ps = con.prepareStatement("insert into USER values (?,?,?,?,?,?) ");
			ps.setString(1, user.getEmail());

			ps.setString(2, new String(Base64.getEncoder().encode(user.getPassword().getBytes())));
			ps.setString(3, user.getRole());
			ps.setString(4, user.getFname());
			ps.setString(5, user.getLname());
			ps.setString(6, user.getGender());
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			con.close();
		}
	}

	@Override
	public User getUserDetails(String email, String password) throws Exception {
		Connection con = null;
		try {
			con = MySQLUtility.connect();
			PreparedStatement ps = con.prepareStatement("select * from USER where email=? and password=?");
			ps.setString(1, email);
			ps.setString(2, new String(Base64.getEncoder().encode(password.getBytes())));
			ResultSet rs = ps.executeQuery();
			rs.next();
			User user = new User();
			user.setEmail(rs.getString("email"));
			user.setFname(rs.getString("fname"));
			user.setLname(rs.getString("lname"));
			user.setGender(rs.getString("gender"));
			user.setRole(rs.getString("role"));
			return user;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}

	}

	@Override
	public void updateProfile(User user) throws Exception {
		Connection con = null;
		try {
			con = MySQLUtility.connect();
			PreparedStatement ps = con.prepareStatement(
					" update USER set role=?, fname=?, lname=?, gender=? where email=?");
			ps.setString(1, user.getRole());
			ps.setString(2, user.getFname());
			ps.setString(3, user.getLname());
			ps.setString(4, user.getGender());
			ps.setString(5, user.getEmail());
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			con.close();
		}

	}

	@Override
	public void deleteProfile(String email) throws Exception {
		Connection con = null;
		try {
			con = MySQLUtility.connect();
			PreparedStatement ps = con.prepareStatement("delete from USER where email=?");
			ps.setString(1, email);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			con.close();
		}

	}

	@Override
	public boolean changePassword(String email, String oldpassword, String newpassword) throws Exception {
		Connection con = null;
		oldpassword = new String(Base64.getEncoder().encode(oldpassword.getBytes()));
		try {
			con = MySQLUtility.connect();
			PreparedStatement ps = con.prepareStatement("select password from USER where email=?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if (rs.getString(1).equals(oldpassword)) {
				PreparedStatement ps2 = con.prepareStatement("update USER set password=? where email=?");
				ps2.setString(1, new String(Base64.getEncoder().encode(newpassword.getBytes())));
				ps2.setString(2, email);
				ps2.execute();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			con.close();
		}

	}

	@Override
	public String forgotPassword(String email) throws Exception {
		Connection con = null;
		try {
			con = MySQLUtility.connect();
			PreparedStatement ps = con.prepareStatement("select password from user where email=?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}

	}

	@Override
	public User getUserDetails(String email) throws Exception {
		Connection con = null;
		try {
			con = MySQLUtility.connect();
			PreparedStatement ps = con.prepareStatement("select * from USER where email=? ");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			User user = new User();
			user.setEmail(rs.getString("email"));
			user.setFname(rs.getString("fname"));
			user.setLname(rs.getString("lname"));
			user.setGender(rs.getString("gender"));
			user.setRole(rs.getString("role"));
			return user;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}

	}

}
