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

@WebServlet(urlPatterns = { "/newMessage" })
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		request.getRequestDispatcher("/newMessage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");

		Message message = new Message();
		message.setTitle(request.getParameter("title"));
		message.setCategory(request.getParameter("category"));
		message.setText(request.getParameter("text"));
		message.setUserId(user.getId());

		if(isValid(request, messages) == true) {
			new MessageService().register(message);
			response.sendRedirect("./");
		} else {
			request.setAttribute("message", message);
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/newMessage.jsp").forward(request, response);
		}
	}

	//バリデーションエラー
	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String text = request.getParameter("text");

		if(StringUtils.isEmpty(title) == true) {
			messages.add("件名を入力してください。");
		} else {
			int digit = title.length();
			if(digit > 50) {
				messages.add("件名は50文字以下で入力してください。");
			}
		}

		if(StringUtils.isEmpty(category) == true) {
			messages.add("カテゴリーを入力してください。");
		} else {
			int digit = category.length();
			if(digit > 10) {
				messages.add("カテゴリーは10文字以下で入力してください。");
			}
		}

		if(StringUtils.isEmpty(text) == true) {
			messages.add("本文を入力してください。");
		} else {
			int digit = text.length();
			if(digit > 1000) {
				messages.add("本文は1000文字以下で入力してください。");
			}
		}

		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
