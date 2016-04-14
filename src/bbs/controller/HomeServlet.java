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
import bbs.beans.UserMessage;
import bbs.service.CommentService;
import bbs.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws IOException, ServletException {

		// プルダウン用カテゴリー一覧
		List<Message> category = new MessageService().getCategory();

		// 投稿、コメント一覧
		List<UserMessage> messages = new MessageService().getMessage();
		List<Comment> comments = new CommentService().getComment();

		if (request.getParameter("category") != null){
			String categoryName = request.getParameter("category");
			if(categoryName.equals("すべて")) {
				categoryName = null;
			} else {
				messages =  new MessageService().getMessage(categoryName);
			}
		}

		request.setAttribute("comments", comments);
		request.setAttribute("category", category);
		request.setAttribute("messages", messages);

		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			 throws IOException, ServletException {
//
//		//TODO 投稿削除機能の実装
//
//		//
//		Integer userId = Integer.parseInt(request.getParameter("loginUserId")); // ログイン中のユーザーID
//		Integer messageId = Integer.parseInt(request.getParameter("messageId")); // 削除対象のmessageID
//
//		System.out.println(userId);
//		System.out.println(messageId);
//
//		new MessageService().deleteMessage(userId,messageId);
//		response.sendRedirect("./");
//
//
//	}

}
