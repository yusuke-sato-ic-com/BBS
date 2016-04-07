package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.User;
import bbs.exception.SQLRuntimeException;

public class UserDao {

	public User getUser(Connection connection, String loginId, String password) {

		// プリコンパイルされたSQL文を表す
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM user WHERE login_id = ? AND password = ?";

			// DBからデータを取得
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, password);

			// SELECTの結果セットを表す
			ResultSet rs = ps.executeQuery();

			List<User> userList = toUserList(rs);
			if(userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String branchId = rs.getString("branch_id");
				String departmentId = rs.getString("department_id");

				User user = new User();
				user.setId(id);
				user.setLoginId(loginId);
				user.setName(name);
				user.setPassword(password);
				user.setBranchId(branchId);
				user.setDepartmentId(departmentId);
				user.setUsing(true);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	// ユーザー新規登録で入力されたデータをDBに登録
	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO user ( ");
			sql.append("login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(") VALUES (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getBranchId());
			ps.setString(5, user.getDepartmentId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}
