package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.UserMessage;
import bbs.exception.SQLRuntimeException;

public class UserMessageDao {

	// 絞り込みの投稿メッセージ一覧を取得
	public List<UserMessage> getRefineMessages(Connection connection, int num, String categoryName, String fromDate, String toDate) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			if(categoryName == null) {
				sql.append("SELECT * FROM user_message WHERE");
				sql.append(" date(insert_date) BETWEEN ?");
				sql.append(" AND ?");
				sql.append(" ORDER BY insert_date DESC limit " + num);

				ps = connection.prepareStatement(sql.toString());
				ps.setString(1, fromDate);
				ps.setString(2, toDate);

			} else {
				sql.append("SELECT * FROM user_message WHERE category = ?");
				sql.append(" AND date(insert_date) BETWEEN ?");
				sql.append(" AND ?");
				sql.append(" ORDER BY insert_date DESC limit " + num);

				ps = connection.prepareStatement(sql.toString());
				ps.setString(1, categoryName);
				ps.setString(2, fromDate);
				ps.setString(3, toDate);
			}

			System.out.println(ps.toString());
//			System.out.println(categoryName);
//			System.out.println(fromDate);
//			System.out.println(toDate);

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	// 投稿メッセージの一覧を取得
	public List<UserMessage> getUserMessages(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_message ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserMessage> toUserMessageList(ResultSet rs) throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {

				int id = rs.getInt("id");
				String title = rs.getString("title");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				String name = rs.getString("name");
				String category = rs.getString("category");
				int userId = rs.getInt("user_id");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");

				UserMessage message = new UserMessage();
				message.setId(id);
				message.setTitle(title);
				message.setText(text);
				message.setInsertDate(insertDate);
				message.setName(name);
				message.setCategory(category);
				message.setUserId(userId);
				message.setBranchId(branchId);
				message.setDepartmentId(departmentId);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}