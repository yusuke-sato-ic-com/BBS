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

		System.out.println(category.get(0).getCategory());

		// 投稿、コメント一覧
		List<UserMessage> messages = new MessageService().getMessage();
		List<Comment> comments = new CommentService().getComment();

		// DBのminDate,maxDateを取得
		List<Message> date = new MessageService().getDate();

//		System.out.println(date.get(0).getMinDate());
//		System.out.println(date.get(0).getMaxDate());
//
//
//		String fromDate = date.get(0).getMinDate();
//		String toDate = date.get(0).getMaxDate();

//		System.out.println(fromDate);
//		System.out.println(toDate);

//		if(request.getParameter("fromDate") != null) {
//			String fromDate = request.getParameter("fromDate");
//		} else {
//			String fromDate = minDate;
//		}
//		messages =  new MessageService().getMessage(fromDate);
//
//		if(request.getParameter("toDate") != null) {
//			String toDate = request.getParameter("toDate");
//		} else {
//			String toDate = maxDate;
//		}
//		messages =  new MessageService().getMessage(toDate);

		if(request.getParameter("category") != null){
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		//投稿削除機能
		if (request.getParameter("messageId") != null){ // 削除対象のmessageID
			Integer messageId = Integer.parseInt(request.getParameter("messageId"));
			new MessageService().deleteMessage(messageId);
			new CommentService().deleteCommentsOfMessage(messageId); // 対応するコメントも削除
		}

		if (request.getParameter("commentId") != null){ // 削除対象のcommentID
			Integer commentId = Integer.parseInt(request.getParameter("commentId"));
			new CommentService().deleteComment(commentId);
		}

		response.sendRedirect("./");


	}

}
