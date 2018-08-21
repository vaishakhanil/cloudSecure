package com.se.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se.dao.SecretKeysDAO;
import com.se.daoimpl.SecretKeysDAOImpl;
import com.se.pojo.SecretKeys;
import com.se.pojo.User;

public class KeysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			User user = (User) req.getSession().getAttribute("user");
			String type = req.getParameter("type");
			SecretKeysDAO secretkeydao = new SecretKeysDAOImpl();
			if (type.equals("view")) {
				List<SecretKeys> keys = secretkeydao.getSecretKeyByEmail(user.getEmail());
				req.setAttribute("keys", keys);
				req.getRequestDispatcher("keys.jsp").forward(req, resp);
			} else if (type.equals("delete")) {
				String kid = req.getParameter("kid");
				String filename = req.getParameter("filename");
				File file = new File("D:/security_ecosystem/keys/" + filename);
				file.delete();
				secretkeydao.deleteSecretKey(kid);
				resp.sendRedirect("keys?type=view&msg=Key Deleted");
			} else if (type.equals("getkeyforupload")) {
				List<SecretKeys> keys = secretkeydao.getSecretKeyByEmail(user.getEmail());
				req.setAttribute("keys", keys);
				req.getRequestDispatcher("uploadfiles.jsp").forward(req, resp);
			} else if (type.equals("key_select")) {
				String keyfile = req.getParameter("keyfile");
				req.setAttribute("keyfile", keyfile);
				req.getRequestDispatcher("uploadfiles.jsp").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("keys.jsp?msg=Something went wrong");
		}
	}

}
