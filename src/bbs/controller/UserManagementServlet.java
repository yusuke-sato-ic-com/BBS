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

import bbs.beans.Message;
import bbs.beans.User;
import bbs.service.MessageService;



@WebServlet(urlPatterns = {"/userManagement"})
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {


		request.getRequestDispatcher("userManagement.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");

		if(isValid(request, messages) == true) {
			response.sendRedirect("./");
		} else {

			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/newMessage.jsp").forward(request, response);
		}
	}

	//バリデーションエラー
	private boolean isValid(HttpServletRequest request, List<String> messages) {

		//TODO 修整
		// 人事でなければエラー
		if((!user.getDepartmentId().equals() == true)) {
			messages.add("アクセス権限がありません。");
		}

		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
