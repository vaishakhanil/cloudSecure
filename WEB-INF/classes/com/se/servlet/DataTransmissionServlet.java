package com.se.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataTransmissionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {

			String type = req.getParameter("type");
			if (type!=null && type.equals("get")) {
				List<String> data_c = (List<String>) req.getSession().getAttribute("data_c");
				List<String> data_nc = (List<String>) req.getSession().getAttribute("data_nc");
				req.setAttribute("data_c", data_c);
				req.setAttribute("data_nc", data_nc);
				req.getRequestDispatcher("read_data.jsp").forward(req, resp);
			} else {

				String data_c = req.getParameter("data_c");
				String data_nc = req.getParameter("data_nc");
				List<String> clist = new ArrayList<>();
				List<String> nclist = new ArrayList<>();
				try {
					clist.addAll((List<String>) req.getSession().getAttribute("data_c"));
					nclist.addAll((List<String>) req.getSession().getAttribute("data_nc"));
				} catch (Exception e) {
					// IGNORE
				}
				clist.add(data_c);
				nclist.add(data_nc);
				System.out.println(clist);
				System.out.println(nclist);
				req.getSession().setAttribute("data_c", clist);
				req.getSession().setAttribute("data_nc", nclist);

				resp.sendRedirect("send_data.jsp?msg=Data added to the session");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("send_data.jsp?msg=Something went wrong");
		}
	}

}
