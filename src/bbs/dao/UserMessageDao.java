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

	public List<UserMessage> getCategoryMessages(Connection connection, int num, String categoryName) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_message WHERE category = ? ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, categoryName);

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toCategoryMessagesList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserMessage> toCategoryMessagesList(ResultSet rs) throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserMessage categoryMessages = new UserMessage();
				categoryMessages.setId(id);
				categoryMessages.setName(name);
				categoryMessages.setCategory(category);
				categoryMessages.setTitle(title);
				categoryMessages.setText(text);
				categoryMessages.setInsertDate(insertDate);

				ret.add(categoryMessages);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

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

				UserMessage message = new UserMessage();
				message.setId(id);
				message.setTitle(title);
				message.setText(text);
				message.setInsertDate(insertDate);
				message.setName(name);
				message.setCategory(category);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}