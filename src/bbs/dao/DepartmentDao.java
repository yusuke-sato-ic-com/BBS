package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Department;
import bbs.exception.SQLRuntimeException;

public class DepartmentDao {
	// 登録されている全部署のデータを取得
	public List<Department> getDepartmentName(Connection connection) {

		// プリコンパイルされたSQL文を表す
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM bbs.department";
			// DBからデータを取得
			ps = connection.prepareStatement(sql);

			// SELECTの結果セットを表す
			ResultSet rs = ps.executeQuery();
			List<Department> allDepartment = toAllDepartmentList(rs);
			return allDepartment;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Department> toAllDepartmentList(ResultSet rs) throws SQLException {

		List<Department> ret = new ArrayList<Department>();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Department department = new Department();
				department.setId(id);
				department.setName(name);

				ret.add(department);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
