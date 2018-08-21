package com.se.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.se.dao.DataDAO;
import com.se.pojo.Data;
import com.se.util.MySQLUtility;

public class DataDAOImpl implements DataDAO {

	@Override
	public void write(Data d) {
		Connection con = null;

		try {
			con = MySQLUtility.connect();
			PreparedStatement ps = con.prepareStatement("insert into data values (?,?,?,?)");
			ps.setString(1, d.getDid());
			ps.setString(2, d.getFilename());
			ps.setString(3, d.getOwner());
			ps.setTimestamp(4, d.getEntry_time());
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
	public List<Data> getDataByEmail(String email) {
		Connection con = null;
		List<Data> result = new ArrayList<>();
		try {
			con = MySQLUtility.connect();
			ResultSet rs = con.createStatement().executeQuery("select * from data where owner='" + email + "'");
			while (rs.next()) {
				Data d = new Data();
				d.setDid(rs.getString("did"));
				d.setFilename(rs.getString("filename"));
				d.setOwner(rs.getString("owner"));
				d.setEntry_time(rs.getTimestamp("entry_time"));
				result.add(d);
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
	public Data getDataByID(String did) {
		Connection con = null;
		Data result = new Data();
		try {
			con = MySQLUtility.connect();
			ResultSet rs = con.createStatement().executeQuery("select * from data where did='" + did + "'");
			rs.next();
			result.setDid(rs.getString("did"));
			result.setFilename(rs.getString("filename"));
			result.setOwner(rs.getString("owner"));
			result.setEntry_time(rs.getTimestamp("entry_time"));

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
	public void delete(String did) {
		Connection con = null;

		try {
			con = MySQLUtility.connect();
			con.createStatement().execute("delete from data where did='" + did + "'");
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
