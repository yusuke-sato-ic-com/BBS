package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Message;
import bbs.exception.SQLRuntimeException;

public class MessageDao {

	public void delete(Connection connection, Integer messageId) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM message WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, messageId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<Message> getDate (Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT min(insert_date) as min, max(insert_date) as max FROM bbs.message";
			// DBからデータを取得
			ps = connection.prepareStatement(sql);
			// SELECTの結果セットを表す
			ResultSet rs = ps.executeQuery();
			List<Message> ret = toDateList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Message> toDateList(ResultSet rs) throws SQLException {
		List<Message> ret = new ArrayList<Message>();
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			while (rs.next()) {
			String minDate = df.format(rs.getDate("min"));
			String maxDate = df.format(rs.getDate("max"));

			Message Date = new Message();
			Date.setMinDate(minDate);
			Date.setMaxDate(maxDate);
			ret.add(Date);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public List<Message> getCategory (Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT DISTINCT category FROM bbs.message";
			// DBからデータを取得
			ps = connection.prepareStatement(sql);
			// SELECTの結果セットを表す
			ResultSet rs = ps.executeQuery();
			List<Message> ret = toCategoryList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Message> toCategoryList(ResultSet rs) throws SQLException {
		List<Message> ret = new ArrayList<Message>();
		try {
			while (rs.next()) {
				String category = rs.getString("category");
				Message categories = new Message();
				categories.setCategory(category);
				ret.add(categories);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public void insert(Connection connection, Message message) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO message ( ");
			sql.append("user_id");
			sql.append(", title");
			sql.append(", category");
			sql.append(", text");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");

			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, message.getUserId());
			ps.setString(2, message.getTitle());
			ps.setString(3, message.getCategory());
			ps.setString(4, message.getText());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
