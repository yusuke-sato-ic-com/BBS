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
import bbs.service.LoginService;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/userEdit" })
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		// 支店、部署のプルダウンの為のデータList
		List<Branch> branch = new BranchService().getBranchName();
		List<Department> department = new DepartmentService().getDepartmentName();

		Integer userId = Integer.parseInt(request.getParameter("user_id"));
		User userEdit  = new UserService().getUserEdit(userId);

		request.setAttribute("branch", branch);
		request.setAttribute("department", department);
		request.setAttribute("user", userEdit);
		request.getRequestDispatcher("userEdit.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws IOException, ServletException {

		Integer userId = Integer.parseInt(request.getParameter("user_id"));
		User user = new User();
		user.setId(userId);
		user.setLoginId(request.getParameter("loginId"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(request.getParameter("branch"));
		user.setDepartmentId(request.getParameter("department"));

		List<Branch> branch = new BranchService().getBranchName();
		List<Department> department = new DepartmentService().getDepartmentName();

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		if(isValid(request, messages) == true) {
			new UserService().editor(user, userId);
			response.sendRedirect("userManagement");
		} else {
			request.setAttribute("branch", branch);
			request.setAttribute("department", department);
			request.setAttribute("user", user);
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("userEdit.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String name = request.getParameter("name");
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirm");

		if(StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください。");
		}

		if(StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください。");
		} else if (!loginId.matches("^\\w{6,20}$")) {
			messages.add("ログインIDは半角英数字6～20文字で入力してください。");
		}

		Integer userId = Integer.parseInt(request.getParameter("user_id"));
		User userEdit  = new UserService().getUserEdit(userId);

		LoginService loginService = new LoginService();
		User user = loginService.login(loginId);

		if(user != null && !userEdit.getLoginId().equals(loginId)) {
			messages.add("ログインIDがすでに使用されています。");
		}

		if(StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください。");
		} else if(StringUtils.isEmpty(confirm) == true) {
			messages.add("パスワード(確認用)も入力してください。");
		} else if((!confirm.equals(password) == true)) {
			messages.add("パスワードは同じものを入力してください。");
		} else if (!password.matches("^[a-zA-Z0-9-/:-@\\[-\\`\\{-\\~]{6,20}$")) { // ^[-/:-@\[-\`\{-\~]+$
			messages.add("パスワードは半角英数字6～20文字で入力してください。");
		}

		Integer branchName = Integer.parseInt(request.getParameter("branch"));
		Integer departmentName = Integer.parseInt(request.getParameter("department"));

		if(branchName == 0) {
			messages.add("所属支店を選択してください。");
		}

		if(departmentName == 0) {
			messages.add("所属部署を選択してください。");
		}

		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}