package com.se.servlet;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.se.dao.SecretKeysDAO;
import com.se.daoimpl.SecretKeysDAOImpl;
import com.se.pojo.SecretKeys;
import com.se.pojo.User;
import com.se.util.Util;

public class UploadKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file;

	public void init() {
		// Get the file location where it would be stored.
		filePath = "D:/security_ecosystem/keys";
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {

		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(req);
		if (!isMultipart) {
			resp.sendRedirect("upload.jsp?msg=Form doesn't supports file upload");
		}
		String id = Util.generateID();

		DiskFileItemFactory factory = new DiskFileItemFactory();

		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);

		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File("/roo/temp"));

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

			String key_text = "";
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
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
						key_text = id + "_" + fileName.substring(fileName.lastIndexOf("\\"));
					} else {
						fname = filePath + File.separator + id + "_"
								+ fileName.substring(fileName.lastIndexOf("\\") + 1);
						file = new File(fname);
						key_text = id + "_" + fileName.substring(fileName.lastIndexOf("\\") + 1);

					}
					fi.write(file);
				}
			}
			User user = (User) req.getSession().getAttribute("user");

			SecretKeysDAO secretkeydao = new SecretKeysDAOImpl();
			SecretKeys sk = new SecretKeys();
			sk.setEmail(user.getEmail());
			sk.setKey_text(key_text);
			sk.setKid(id);
			secretkeydao.add(sk);

			resp.sendRedirect("uploadkey.jsp?msg=File Upload Successful");
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("uploadkey.jsp?msg=Something went wrong");
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
	}
}