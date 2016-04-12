package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.Comment;
import bbs.beans.Message;
import bbs.beans.User;
import bbs.beans.UserMessage;
import bbs.service.CommentService;
import bbs.service.MessageService;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws IOException, ServletException {

		// プルダウン用カテゴリー一覧
		List<Message> category = new MessageService().getCategory();

		// コメント投稿者取得用
		List<User> user  = new UserService().getAllUser();

		// 投稿、コメント一覧
		List<UserMessage> messages = new MessageService().getMessage();
		List<Comment> comments = new CommentService().getComment();

		if (request.getParameter("category") != null){
			String categoryName = request.getParameter("category");
			if(categoryName.equals("カテゴリー選択")) {
				categoryName = null;
			} else {
				messages =  new MessageService().getMessage(categoryName);
			}
		}
//		request.setAttribute("user", user);
		request.setAttribute("comments", comments);
		request.setAttribute("category", category);
		request.setAttribute("messages", messages);

		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

}
