package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.User;
import bbs.service.UserService;

@WebServlet(urlPatterns = {"/userManagement"})
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		List<User> user = new UserService().getAllUser();

		request.setAttribute("user", user);
		request.getRequestDispatcher("userManagement.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		// ユーザー停止・復活機能の実装
		Integer userId = Integer.parseInt(request.getParameter("user_id"));
		Integer using;

		if(request.getParameter("delete-user") != null){
			new UserService().editor(userId);
			response.sendRedirect("userManagement");
		}

		if(request.getParameter("using") != null){
			if(request.getParameter("using").equals("ON")) {
				using = 0;
				new UserService().editor(userId, using);
				response.sendRedirect("userManagement");
			} else {
				using = 1;
				new UserService().editor(userId, using);
				response.sendRedirect("userManagement");
			}
		}

	}

}