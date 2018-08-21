package com.se.dao;

import java.util.List;

import com.se.pojo.SecretKeys;

public interface SecretKeysDAO {

	public void add(SecretKeys sk);

	public List<SecretKeys> getSecretKeyByEmail(String email);

	public SecretKeys getSecretKeyByID(String kid);

	public void deleteSecretKey(String kid);

}
