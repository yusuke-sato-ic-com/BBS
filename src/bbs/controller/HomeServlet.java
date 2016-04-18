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

		// DBのminDate,maxDateを取得
		List<Message> date = new MessageService().getDate();

		String minDate = date.get(0).getMinDate();
		String maxDate = date.get(0).getMaxDate();

//		System.out.println("DaoMinDate : " + minDate);
//		System.out.println("DaoMaxDate : " + maxDate);

//		System.out.println(request.getParameter("fromDate"));
//		System.out.println(request.getParameter("toDate"));

		String fromDate;
		if(request.getParameter("fromDate") == "") {
			fromDate = minDate;
		} else {
			fromDate = (request.getParameter("fromDate"));
		}
//		System.out.println("fromDate : " + fromDate);

		String toDate;
		if(request.getParameter("toDate") == "") {
			toDate = maxDate;
		} else {
			toDate = (request.getParameter("toDate"));
		}
//		System.out.println("toDate : " + toDate);

		String categoryName;
		if(request.getParameter("category") != null){
			categoryName = request.getParameter("category");
			//TODO すべての部分をなんとかする、
			if(categoryName.equals("すべて")) {
				categoryName = null;
			}
			messages =  new MessageService().getMessage(categoryName, fromDate, toDate);
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
