package com.se.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se.daoimpl.UserDAOImpl;
import com.se.daoimpl.UserLoginDAO;
import com.se.dao.UserDAO;
import com.se.daoimpl.UserDAOImpl;
import com.se.mail.MailThread;
import com.se.mail.SendMail;
import com.se.pojo.User;
import com.se.util.Util;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDAO dao = new UserDAOImpl();
		
		try {
			String request_type = req.getParameter("request_type");
			if (request_type.equals("register")) {
				User user = new User();
				String email = req.getParameter("email");
				user.setEmail(email);
				String fname = req.getParameter("fname");
				user.setFname(fname);
				String lname = req.getParameter("lname");
				user.setLname(lname);
				String gender = req.getParameter("gender");
				user.setGender(gender);
				String password = req.getParameter("password");
				user.setPassword(password);
				String role = req.getParameter("role");
				if (role == null || role.trim().length() == 0)
					role = "USER";
				user.setRole(role);

				if ( (email == null || email.trim().length() == 0)
						|| (fname == null || fname.trim().length() == 0)
						|| (lname == null || lname.trim().length() == 0)
						|| (role == null || role.trim().length() == 0)
						|| (gender == null || gender.trim().length() == 0)
						|| (password == null || password.trim().length() == 0)) {
					resp.sendRedirect(
							"register.jsp?msg=Error! All the fields are mandatory. Please provide the details.");
				} else {

					dao.register(user);
					resp.sendRedirect("register.jsp?msg=Registration Successful");
				}
			} else if (request_type.equals("login")) {
				String email = req.getParameter("email");
				String password = req.getParameter("password");
				User user = dao.getUserDetails(email, password);
				if (email == null || email.trim().length() == 0 || password == null || password.trim().length() == 0) {
					resp.sendRedirect("login.jsp?msg=Error! All the fields are mandatory. Please provide the details");
				} else if (user != null) {
					
					SendMail mail = new SendMail();
					List<String> to  =new ArrayList<>();
					to.add(email);
					String sub = "Login Verification code";
					String code= Util.generateverificationcode();
					String body = "Dear "+email+", <br/>Your login verification code is : <b>"+code+"</b>";
					UserLoginDAO udao = new UserLoginDAO();
					udao.writecode(email, code);
					new MailThread(body, sub, to);
					
					req.getSession().setAttribute("user1", user);
					resp.sendRedirect("welcome1.jsp?email="+email+"&msg=Login verification code sent to your email ID ");
				} else {
					resp.sendRedirect("login.jsp?msg=Invalid Credentials");
				}
			}else if (request_type.equals("login2")) {
				String email = req.getParameter("email");
				String code = req.getParameter("code");
				User u = (User) req.getSession().getAttribute("user1");
				req.getSession().setAttribute("user", u);
				UserLoginDAO udao = new UserLoginDAO();
				if (udao.verifycode(email, code)) {
					User user = dao.getUserDetails(email);
					req.getSession().setAttribute("user", user);
					resp.sendRedirect("welcome.jsp?msg=Successfully logged in as " + user.getFname() + " "
							+ user.getLname() + " (" + user.getRole() + ") ");

				} else {
					resp.sendRedirect("welcome1.jsp?email="+email+"&msg=Invalid Code ");
				}
			}
			
			else if (request_type.equals("updateprofile")) {
				User user = new User();

				String email = req.getParameter("email");
				String fname = req.getParameter("fname");
				String lname = req.getParameter("lname");
				String gender = req.getParameter("gender");
				String role = req.getParameter("role");
				if (role == null || role.trim().length() == 0)
					role = "USER";
		
				user.setEmail(email);
				user.setFname(fname);
				user.setLname(lname);
				user.setGender(gender);
		
				user.setRole(role);

				if ( (email == null || email.trim().length() == 0)
						|| (fname == null || fname.trim().length() == 0)
						|| (lname == null || lname.trim().length() == 0)
						
						|| (role == null || role.trim().length() == 0)
						|| (gender == null || gender.trim().length() == 0)) {
					resp.sendRedirect(
							"updateprofile.jsp?msg=Error! All the fields are mandatory. Please provide the details");

				} else {

					dao.updateProfile(user);
					req.getSession().removeAttribute("user");
					req.getSession().setAttribute("user", user);
					resp.sendRedirect("updateprofile.jsp?msg=Profile Updated Successfully");
				}

			} else if (request_type.equals("changepassword")) {
				String oldpassword = req.getParameter("oldpassword");
				String newpassword = req.getParameter("newpassword");

				if (oldpassword == null || oldpassword.trim().length() == 0 || newpassword == null
						|| newpassword.trim().length() == 0) {
					resp.sendRedirect(
							"changepassword.jsp?msg=Error! All the fields are mandatory. Please provide the details");
				} else {

					boolean result = dao.changePassword(((User) req.getSession().getAttribute("user")).getEmail(),
							oldpassword, newpassword);

					if (result) {
						resp.sendRedirect("changepassword.jsp?msg=Successfully Updated Your Password");
					} else {
						resp.sendRedirect("changepassword.jsp?msg=Your Current Password is Wrong");

					}
				}
			} else if (request_type.equals("deleteprofile")) {
				dao.deleteProfile(((User) req.getSession().getAttribute("user")).getEmail());
				req.getSession().invalidate();
				resp.sendRedirect("login.jsp?msg=Profile Deleted Successfully");
			} else if (request_type.equals("forgotpassword")) {
				String email = req.getParameter("email");
				if (email == null || email.trim().length() == 0) {
					resp.sendRedirect("forgotpassword.jsp?msg=Please enter your email ID");
				} else {

					String password = dao.forgotPassword(email);

					if (password != null) {
						List<String> rcv = new ArrayList<>();
						rcv.add(email);
						new MailThread(
								"<b>Dear " + email + ",</b> <br> Your Current Password is: <b>" + password + "</b>",
								"Password Recovery", rcv);
						resp.sendRedirect("forgotpassword.jsp?msg=Password Recovery mail sent to your email address");
					} else {
						resp.sendRedirect("forgotpassword.jsp?msg=Email ID did not match with any account");
					}
				}
			} else if (request_type.equals("logout")) {
				req.getSession().removeAttribute("dir");
				req.getSession().removeAttribute("sync");
				req.getSession().invalidate();
				resp.sendRedirect("login.jsp?msg=Successfully Logged Out");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("error.jsp?msg=OOPS! Something went wrong");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
