package com.se.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se.dao.DataDAO;
import com.se.dao.SecretKeysDAO;
import com.se.daoimpl.DataDAOImpl;
import com.se.daoimpl.SecretKeysDAOImpl;
import com.se.hybrid.decrypt.DecryptData;
import com.se.hybrid.decrypt.DecryptKey;
import com.se.hybrid.decrypt.StartDecryption;
import com.se.pojo.Data;
import com.se.pojo.SecretKeys;
import com.se.pojo.User;
import com.se.sync.CloudAccess;

public class ViewFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			User user = (User) req.getSession().getAttribute("user");
			SecretKeysDAO secretkeydao = new SecretKeysDAOImpl();
			DataDAO datadao = new DataDAOImpl();
			CloudAccess ca = new CloudAccess();

			String type = req.getParameter("type");
			if (type.equals("get")) {
				List<Data> data = datadao.getDataByEmail(user.getEmail());
				List<SecretKeys> keys = secretkeydao.getSecretKeyByEmail(user.getEmail());
				req.setAttribute("keys", keys);
				req.setAttribute("data", data);
				req.getRequestDispatcher("view_data.jsp").forward(req, resp);

			} else if (type.equals("download")) {
				String data = req.getParameter("file");
				ca.checkout("D:/security_ecosystem/data_enc");

				
//				StartDecryption startEnc = new StartDecryption();
//				
//				File encryptedKeyReceived = new File("D:/security_ecosystem/keys_enc/"+key);
//				File decreptedKeyFile = new File("D:/security_ecosystem/keys_dec/"+key);
//				new DecryptKey(startEnc.getPrivate("D:/security_ecosystem/KeyPair"+"/privateKey_B", "RSA"), encryptedKeyReceived, decreptedKeyFile, "RSA");
//				
//				File encryptedFileReceived = new File("D:/security_ecosystem/data_enc/"+data);
//				File decryptedFile = new File("D:/security_ecosystem/data_dec/"+data);
//				new DecryptData(encryptedFileReceived, decryptedFile, startEnc.getSecretKey("D:/security_ecosystem/keys_dec/"+key, "AES"), "AES");

				
				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				String filename = data;
				String filepath = "D:/security_ecosystem/data/";
				
				
				resp.setContentType("APPLICATION/OCTET-STREAM");
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

				FileInputStream fileInputStream = new FileInputStream(filepath + filename);

				int i;
				while ((i = fileInputStream.read()) != -1) {
					out.write(i);
				}
				fileInputStream.close();
				out.close();


		
			} else if (type.equals("delete")) {
				String did = req.getParameter("did");
				String file = req.getParameter("file");
				datadao.delete(did);
				ca.delete(file);
				resp.sendRedirect("view?type=get&msg=File Deleted Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("view.jsp?msg=Something went wrong");
		}
	}

}
