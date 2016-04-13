package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Comment;
import bbs.exception.SQLRuntimeException;

public class CommentDao {

	// コメント一覧を取得
	public List<Comment> getComments (Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT  comment.*, user.name FROM user, comment WHERE user.id = comment.user_id ORDER BY insert_date;";
			// DBからデータを取得
			ps = connection.prepareStatement(sql);
			// SELECTの結果セットを表す
			ResultSet rs = ps.executeQuery();
			List<Comment> ret = toCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Comment> toCommentList(ResultSet rs) throws SQLException {
		List<Comment> ret = new ArrayList<Comment>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				int messageId = rs.getInt("message_id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				String name = rs.getString("name");

				Comment comment = new Comment();
				comment.setId(id);
				comment.setUserId(userId);
				comment.setMessageId(messageId);
				comment.setText(text);
				comment.setInsertDate(insertDate);
				comment.setName(name);

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	// コメントをDBに登録
	public void insert(Connection connection, Comment comment) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comment ( ");
			sql.append("user_id");
			sql.append(", message_id");
			sql.append(", text");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");

			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, comment.getUserId());
			ps.setInt(2, comment.getMessageId());
			ps.setString(3, comment.getText());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
