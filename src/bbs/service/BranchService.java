package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.Branch;
import bbs.dao.BranchDao;

public class BranchService {
	// ユーザー情報取得用
	public List<Branch> getBranchName() {

		Connection connection = null;
		try {
			connection = getConnection();
			BranchDao branchDao = new BranchDao();

			List<Branch> ret = branchDao.getBranchName(connection);

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
}
