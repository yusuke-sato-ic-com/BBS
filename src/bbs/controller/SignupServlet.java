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

import bbs.beans.Branch;
import bbs.beans.Department;
import bbs.beans.User;
import bbs.service.BranchService;
import bbs.service.DepartmentService;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		Integer branchName = Integer.parseInt(request.getParameter("branch"));
		Integer departmentName = Integer.parseInt(request.getParameter("department"));
		// 支店、部署のプルダウンの為のデータList
		List<Branch> branch = new BranchService().getBranchName();
		List<Department> department = new DepartmentService().getDepartmentName();

		request.setAttribute("branch", branch);
		request.setAttribute("department", department);
		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		User user = new User();
		user.setLoginId(request.getParameter("loginId"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(request.getParameter("branchId"));
		user.setDepartmentId(request.getParameter("departmentId"));

		List<Branch> branch = new BranchService().getBranchName();
		List<Department> department = new DepartmentService().getDepartmentName();

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		if(isValid(request, messages) == true) {
			new UserService().register(user);
			response.sendRedirect("userManagement");
		} else {
			request.setAttribute("branch", branch);
			request.setAttribute("department", department);
			request.setAttribute("user", user);
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirm");

		if(StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください。");
		}
		if(StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください。");
		} else if(StringUtils.isEmpty(confirm) == true) {
			messages.add("パスワード(確認用)も入力してください。");
		} else if((!confirm.equals(password) == true)) {
			messages.add("パスワードは同じものを入力してください。");
		}

		//TODO
		// ログインIDがすでに使われていないかも、確認必要
		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}