package bbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bbs.beans.User;
import bbs.service.LoginService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService();
		User user = loginService.login(loginId, password);

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		if(isValid(request, messages, loginId, password) == true) {

			if(user == null || user.getUsing() == 0) {
				session.setAttribute("errorMessages", messages);
				messages.add("ログインに失敗しました");
				response.sendRedirect("login");
			} else {
				session.setAttribute("loginUser", user);
				response.sendRedirect("./");
			}
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("login");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages, String loginId, String password) {

		if(StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください。");
		}

		if(StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください。");
		}

		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}

