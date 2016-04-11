package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.Message;
import bbs.beans.User;
import bbs.beans.UserMessage;
import bbs.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws IOException, ServletException {

		// 投稿一覧表示
		List<UserMessage> messages = new MessageService().getMessage();
		List<Message> category = new MessageService().getCategory();

		if (request.getParameter("category") != null){

		String categoryName = request.getParameter("category");

		if(categoryName.equals("カテゴリー選択")) {
			categoryName = null;
		} else {
//			List<UserMessage> categoryMessages = new MessageService().getMessage(categoryName);
			messages =  new MessageService().getMessage(categoryName);
			}
		}
//		request.setAttribute("categoryMessages", categoryMessages);
		request.setAttribute("category", category);
		request.setAttribute("messages", messages);

		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

}
