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

@WebServlet(urlPatterns = { "/newComment" })
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");

		Message message = new Message();
		message.setText(request.getParameter("commentText"));
		message.setUserId(user.getId());

		if(isValid(request, messages) == true) {
			new MessageService().register(message);
			response.sendRedirect("./");
		} else {
			request.setAttribute("message", message);
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}
	}

	//バリデーションエラー
	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String commentText = request.getParameter("commentText");

		if(StringUtils.isEmpty(commentText) == true) {
			messages.add("コメントを入力してください。");
		} else {
			int digit = commentText.length();
			if(digit > 500) {
				messages.add("コメントは500文字以下で入力してください。");
			}
		}

		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
