package com.se.servlet;

import java.io.File;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.se.dao.DataDAO;
import com.se.dao.SecretKeysDAO;
import com.se.daoimpl.DataDAOImpl;
import com.se.daoimpl.SecretKeysDAOImpl;
import com.se.hybrid.encrypt.EncryptData;
import com.se.hybrid.encrypt.EncryptKey;
import com.se.hybrid.encrypt.StartEncryption;
import com.se.pojo.Data;
import com.se.pojo.SecretKeys;
import com.se.pojo.User;
import com.se.sync.CloudAccess;
import com.se.util.Util;

public class UploadDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file;

	public void init() {
		// Get the file location where it would be stored.
		filePath = "D:/security_ecosystem/data";
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {

		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(req);
		if (!isMultipart) {
			resp.sendRedirect("uploadfiles.jsp?msg=Form doesn't supports file upload");
		}
		String id = Util.generateID();

		DiskFileItemFactory factory = new DiskFileItemFactory();

		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);

		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File("C:/temp"));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);

		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(req);

			// Process the uploaded file items
			Iterator i = fileItems.iterator();
			String fname = "";

			String data_file_name = "";
			String keyfile_text = "";

			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (fi.getFieldName().equals("keyfile")) {
					keyfile_text = fi.getString();
				}
				if (!fi.isFormField()) {
					// Get the uploaded file parameters
					String fieldName = fi.getFieldName();
					String fileName = fi.getName();
					String contentType = fi.getContentType();
					boolean isInMemory = fi.isInMemory();
					long sizeInBytes = fi.getSize();

					// Write the file
					if (fileName.lastIndexOf("\\") >= 0) {
						fname = filePath + File.separator + id + "_" + fileName.substring(fileName.lastIndexOf("\\"));
						file = new File(fname);
						data_file_name = id+"_"+fileName.substring(fileName.lastIndexOf("\\"));
					} else {
						fname = filePath + File.separator + id + "_"
								+ fileName.substring(fileName.lastIndexOf("\\") + 1);
						file = new File(fname);
						data_file_name = id+"_"+fileName.substring(fileName.lastIndexOf("\\") + 1);

					}
					fi.write(file);
				}
			}
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("keyfile.. "+keyfile_text);
			File keyfile = new File("D:/security_ecosystem/keys/"+keyfile_text);
			File keyfile_enc = new File("D:/security_ecosystem/keys_enc/"+keyfile_text);
			File datafile = new File("D:/security_ecosystem/data/"+data_file_name);
			File datafile_enc = new File("D:/security_ecosystem/data_enc/"+data_file_name);
			
			StartEncryption startEnc = new StartEncryption();			
			new EncryptKey(startEnc.getPublic("D:/security_ecosystem/KeyPair/publicKey_B", "RSA"), keyfile, keyfile_enc, "RSA");			
			new EncryptData(datafile, datafile_enc, startEnc.getSecretKey("D:/security_ecosystem/keys/"+keyfile_text, "AES"), "AES");

			DataDAO datadao = new DataDAOImpl();
			Data d = new Data();
			d.setDid(id);
			d.setEntry_time(new Timestamp(System.currentTimeMillis()));
			d.setFilename(data_file_name);
			d.setOwner(user.getEmail());
			datadao.write(d);
			
			CloudAccess ca = new CloudAccess();
			ca.checkin("D:/security_ecosystem/data_enc/"+data_file_name);
					

			resp.sendRedirect("keys?type=getkeyforupload&msg=Data upload to cloud successful");
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("keys?type=getkeyforupload&sg=Something went wrong");
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
	}
}