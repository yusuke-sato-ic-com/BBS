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

import bbs.beans.Comment;
import bbs.beans.User;
import bbs.service.CommentService;

@WebServlet(urlPatterns = { "/newComment" })
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		//messageIDの取得
		Integer messageId = Integer.parseInt((request.getParameter("messageId")));

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");

		Comment comment = new Comment();
		comment.setUserId(user.getId());
		comment.setMessageId(messageId);
		comment.setText(request.getParameter("text"));

		if(isValid(request, messages) == true) {
			new CommentService().register(comment);
			response.sendRedirect("./");
		} else {
			request.setAttribute("comment", comment);
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("./");
		}
	}

	//バリデーションエラー
	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String commentText = request.getParameter("text");

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
