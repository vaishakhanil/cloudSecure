package com.se.dao;

import java.util.List;

import com.se.pojo.Data;

public interface DataDAO {

	public void write(Data d);

	public List<Data> getDataByEmail(String email);

	public Data getDataByID(String did);

	public void delete(String did);

}
