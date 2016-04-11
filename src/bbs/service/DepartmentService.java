package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.Department;
import bbs.dao.DepartmentDao;

public class DepartmentService {

	// ユーザー情報取得用
	public List<Department> getDepartmentName() {

		Connection connection = null;
		try {
			connection = getConnection();
			DepartmentDao departmentDao = new DepartmentDao();

			List<Department> ret = departmentDao.getDepartmentName(connection);

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
