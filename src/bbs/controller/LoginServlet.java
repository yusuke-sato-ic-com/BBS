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

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();

		// ログインIDとパスワードのフォーマットチェック
//		if(signupCheck(loginId, "ログインID", "^\\w{6,20}$")) {
//			session.setAttribute("loginUser", user); // Serviceを経由してuserオブジェクトを取得できたらセッションにセット
//			response.sendRedirect("./"); //トップ画面に遷移
//		} else {
//			messages.add("ログインIDを入力してください。");
//			session.setAttribute("errorMessages", messages);
//			response.sendRedirect("login");
//		}
//
//		if(signupCheck(password, "パスワード", "^\\w{6,255}$")) {
//			session.setAttribute("loginUser", user); // Serviceを経由してuserオブジェクトを取得できたらセッションにセット
//			response.sendRedirect("./"); //トップ画面に遷移
//		} else {
//			messages.add("パスワードを入力してください。");
//			session.setAttribute("errorMessages", messages);
//			response.sendRedirect("login");
//		}
//	}
//
//		// 【メソッド】ログインIDとパスワードのフォーマットチェック
//	static boolean signupCheck (String idOrPass, String idOrPassMessage, String formatCheck) throws IOException {
//		if(idOrPass != null && idOrPass.matches((formatCheck))) {
//			return true;
//		} else {
//			return false;
//		}
//
	}
}


