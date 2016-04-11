package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Branch;
import bbs.exception.SQLRuntimeException;

public class BranchDao {
	// 登録されている全支店のデータを取得
	public List<Branch> getBranchName(Connection connection) {

		// プリコンパイルされたSQL文を表す
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM bbs.branch";
			// DBからデータを取得
			ps = connection.prepareStatement(sql);

			// SELECTの結果セットを表す
			ResultSet rs = ps.executeQuery();
			List<Branch> allBranch = toAllBranchList(rs);
			return allBranch;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Branch> toAllBranchList(ResultSet rs) throws SQLException {

		List<Branch> ret = new ArrayList<Branch>();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Branch branch = new Branch();
				branch.setBranchId(id);
				branch.setBranchName(name);

				ret.add(branch);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}
