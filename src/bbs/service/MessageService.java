package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.Message;
import bbs.beans.UserMessage;
import bbs.dao.MessageDao;
import bbs.dao.UserMessageDao;

public class MessageService {


	// DBからカテゴリーを取得
	public List<Message> getCategory() {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			List<Message> ret = messageDao.getCategory(connection);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	// DBから投稿メッセージ一覧を取得
	private static final int LIMIT_NUM = 1000;

	public List<UserMessage> getMessage() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> ret = messageDao.getUserMessages(connection, LIMIT_NUM);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	// DBからカテゴリー検索用の投稿メッセージを取得
	public List<UserMessage> getMessage(String categoryName) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> ret = messageDao.getCategoryMessages(connection, LIMIT_NUM, categoryName);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}


	// 投稿メッセージをDBへ登録
	public void register(Message message) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, message);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}
