package jp.co.basil.app.login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jp.co.basil.app.db.DBAccess;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS) Yoshida
 * ログイン時のDB処理を担当します。
 *
 */
public class LoginDbAccess extends DBAccess {


	protected LoginDbAccess() {
	}


	/**
	 * ログイン処理の入り口。ユーザー番号を返します。
	 * @param userId
	 * @param pass
	 * @return
	 * @throws Exception
	 */
	protected UserInfoBean doLogin(String userId, String pass) throws Exception{

		this.connect();
		return this.getUserNo(this.executeSelect(this.getPreparedStatement(userId, pass, this.loginSQL())));

	}


	/**
	 * ログイン処理に必要なデータを取得するためのSQLを取得します。
	 * @param userId
	 * @param pass
	 * @return
	 */
	protected String loginSQL() {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("USER_NO ");
		sb.append("from ");
			sb.append("USER_TBL ");
		sb.append("where ");
			sb.append("USER_ID = ? ");
		sb.append("and ");
			sb.append("PASSWORD = ?");

		return sb.toString();

	}


	/**
	 * プリペアードステートメントを用意する。
	 * @param formBean
	 * @return
	 * @throws Exception
	 */
	private PreparedStatement getPreparedStatement(String userId, String pass, String sql) throws Exception{

		PreparedStatement stmt = this.makePreparedStatement(sql);

	    stmt.setString(1, userId); //USER_ID
	    stmt.setString(2, pass); //PASS_WORD

		return stmt;

	}


	/**
	 * ユーザー番号をDB検索結果から取得する。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	protected UserInfoBean getUserNo(ResultSet rs) throws Exception{

		UserInfoBean tmpBean = null;

		if(rs.next()) {

			tmpBean = new UserInfoBean();
			tmpBean.setUsetNo(StringUtil.getString(rs.getString("USER_NO")));

		}

		this.disConnect();

		return tmpBean;

	}


}
