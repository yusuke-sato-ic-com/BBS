package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;

import bbs.beans.User;
import bbs.dao.UserDao;
import bbs.utils.CipherUtil;

public class LoginService {

	public User login(String loginId, String password) {

//		データベースに接続するためには、JDBCからConnectionインスタンスを取得しなければならない。
//		このためにDriverManager.getConnection()メソッドを使う。
//		Connection db = DriverManager.getConnection(url, username, password);

		Connection connection = null;
		try {
			connection = getConnection();

			// passwordを暗号化
			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			User user = userDao.getUser(connection, loginId, encPassword);

			commit(connection); // commit：保存処理のようなもの

			return user;
		} catch (RuntimeException e) {
			rollback(connection); // rollback：commitと逆で、テーブルを前の状態に戻す
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection); // データベースとの接続 (セッション) を切断
		}
	}

	public User login(String loginId) {

//		データベースに接続するためには、JDBCからConnectionインスタンスを取得しなければならない。
//		このためにDriverManager.getConnection()メソッドを使う。
//		Connection db = DriverManager.getConnection(url, username, password);

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			User user = userDao.getUser(connection, loginId);

			commit(connection); // commit：保存処理のようなもの

			return user;
		} catch (RuntimeException e) {
			rollback(connection); // rollback：commitと逆で、テーブルを前の状態に戻す
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection); // データベースとの接続 (セッション) を切断
		}
	}


}
