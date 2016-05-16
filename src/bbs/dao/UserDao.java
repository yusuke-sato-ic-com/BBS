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


	// 編集対象のユーザー情報を取得
	public void deleteUser(Connection connection, Integer userId) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM user WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, userId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	// ユーザーの利用状況を編集
	public void updateUsing(Connection connection, Integer userId, Integer using) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE user SET ");
			sql.append("`using` = ? ");
			sql.append("WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, using);
			ps.setInt(2, userId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	// ユーザー編集で入力されたデータでDB更新
	public void update(Connection connection, User user, Integer userId) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE user SET ");
			sql.append("login_id = ?");
			sql.append(", password = ?");
			sql.append(", name = ?");
			sql.append(", branch = ?");
			sql.append(", department = ?");
			sql.append("WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getBranchId());
			ps.setString(5, user.getDepartmentId());
			ps.setInt(6, userId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	// 編集対象のユーザー情報を取得
	public User getUserEdit(Connection connection, Integer userId) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM user WHERE id = ?";
			// DBからデータを取得
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);

			// SELECTの結果セットを表す
			ResultSet rs = ps.executeQuery();
			List<User> userEdit = toUserList(rs);
			if(userEdit.isEmpty() == true) {
				return null;
			} else if (2 <= userEdit.size()) {
				throw new IllegalStateException("2 <= userEdit.size()");
			} else {
				return userEdit.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	// 登録されている全ユーザーのデータを取得
	public List<User> getAllUser(Connection connection) {

		// プリコンパイルされたSQL文を表す
		PreparedStatement ps = null;
		try {
			String sql = "SELECT user.*, branch.name AS branch_name, department.name AS department_name "
					+ "FROM bbs.user, bbs.branch, bbs.department "
					+ "WHERE user.branch = branch.id AND user.department = department.id";
			// DBからデータを取得
			ps = connection.prepareStatement(sql);

			// SELECTの結果セットを表す
			ResultSet rs = ps.executeQuery();
			List<User> allUser = toAllUserList(rs);
			return allUser;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toAllUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String branchId = rs.getString("branch");
				String departmentId = rs.getString("department");
				int using = rs.getInt("using");
				String branchName = rs.getString("branch_name");
				String departmentName = rs.getString("department_name");


				User user = new User();
				user.setId(id);
				user.setLoginId(loginId);
				user.setName(name);
				user.setPassword(password);
				user.setBranchId(branchId);
				user.setDepartmentId(departmentId);
				user.setUsing(using);
				user.setBranchName(branchName);
				user.setDepartmentName(departmentName);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	// 新規登録、編集時にユーザー情報を取得
		public User getUser(Connection connection, String loginId) {

			// プリコンパイルされたSQL文を表す
			PreparedStatement ps = null;
			try {
				String sql = "SELECT * FROM user WHERE login_id = ?";

				// DBからデータを取得
				ps = connection.prepareStatement(sql);
				ps.setString(1, loginId);

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

	// ログイン時にユーザー情報を取得
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
				String branchId = rs.getString("branch");
				String departmentId = rs.getString("department");
				int using = rs.getInt("using");

				User user = new User();
				user.setId(id);
				user.setLoginId(loginId);
				user.setName(name);
				user.setPassword(password);
				user.setBranchId(branchId);
				user.setDepartmentId(departmentId);
				user.setUsing(using);

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
			sql.append(", branch");
			sql.append(", department");
			sql.append(") VALUES (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(") ");

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
