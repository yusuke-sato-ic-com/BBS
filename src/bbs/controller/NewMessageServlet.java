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

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
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
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("./");
		}
	}

	//TODO バリデーションエラー
	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String title = request.getParameter("title");
		// 件名 プルダウンでnullの設定
		String category = request.getParameter("category");
		String text = request.getParameter("text");

		if(StringUtils.isEmpty(title) == true) {
			messages.add("件名を入力してください。");
		}
		// 件名は-1のときにエラーという仕様にする？
		if(StringUtils.isEmpty(category) == true) {
			messages.add("カテゴリーを選択してください。");
		}
		if(StringUtils.isEmpty(text) == true) {
			messages.add("件名を入力してください。");
		}

		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
