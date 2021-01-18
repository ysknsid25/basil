package jp.co.basil.app.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.logger.LoggerSingleton;

import java.sql.Connection;

/**
 * @author NOMOS) Yoshida
 * DB接続の機能を提供します。
 *
 */
public class DBAccess {

	private Connection con;
	private ResultSet rs;
	private Statement stmt;


	/**
	 * コンストラクタ
	 */
	protected DBAccess(){
	}


	/**
	 * Select文を発行して、結果を得る
	 * @param sql
	 * @param colInfoList
	 * @return
	 * @throws Exception
	 */
	protected ResultSet executeSelect(String sql) throws Exception{

		this.connect();

		this.debugQuery(sql, false);

		try {

		     this.stmt = this.con.createStatement();
		     this.rs = this.stmt.executeQuery(sql);

		}catch(Exception e) {

			this.disConnect();

		}

		return this.rs;

	}


	/**
	 * Select文を発行して、結果を得る。プリペアードステートメントを使ってSelect文を発行する。
	 * @param stmt
	 * @param colInfoList
	 * @return
	 * @throws Exception
	 */
	protected ResultSet executeSelect(PreparedStatement stmt) throws Exception{

		this.debugQuery(stmt.toString(), false);

		try {

		     this.rs = stmt.executeQuery();

		}catch(Exception e) {

			this.disConnect();

		}

		return this.rs;

	}


	/**
	 * 登録・更新・削除処理を行う。
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	protected void executeUpdate(PreparedStatement stmt) throws Exception{

		this.debugQuery(stmt.toString(), true);

		try {

			this.doTransAction(stmt.executeUpdate());

		}catch(Exception e) {

			stmt.close();
		    this.con.close();
			throw e;

		}finally {

			stmt.close();
		    this.con.close();

		}

	}


	/**
	 * 登録・更新・削除処理を行う。
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	protected void executeUpdate(String sql) throws Exception{

		this.debugQuery(sql, true);

		this.connect();

		try {

		    this.stmt = this.con.createStatement();
			this.doTransAction(this.stmt.executeUpdate(sql));

		}catch(Exception e) {

		    this.con.close();
			throw e;

		}finally {

		     this.con.close();

		}

	}


	/**
	 * 連続して処理を行う場合のために、クエリの実行のみを行うメソッドを提供する。
	 * @param sql
	 * @return trueなら成功
	 * @throws Exception
	 */
	protected int executeQuery(String sql) throws Exception{

		this.debugQuery(sql, true);
	    this.stmt = this.con.createStatement();
		return this.stmt.executeUpdate(sql);

	}


	/**
	 * コミット、ロールバックを行う。
	 * @param retVal
	 * @throws Exception
	 */
	protected void doTransAction(int retVal) throws Exception{

		if(retVal < 0) {

			this.con.rollback();

		}else {

			this.con.commit();

		}

	}


	/**
	 * 排他制御を行う
	 * @param lockSQL for update nowaitのSQL
	 * @param updateYMD データ取得時点の更新日付
	 * @param updateHMS　データ取得時点の更新時間
	 * @return true:排他制御成功<br> false:失敗
	 * @throws Exception
	 */
	protected boolean exclusionControl(String lockSQL, String updateYMD, String updateHMS) throws Exception{

		//更新直前の日付
		String latestUpdateYMD = "";
		String latastUpdateHMS = "";

		this.executeSelect(lockSQL);

		//更新直前の更新日付・時間を取得。
		//取得できない場合は、既に削除されているのでfalseをreturn
		if(rs.next()){

			latestUpdateYMD = this.rs.getString("UPDATE_YMD");
			latastUpdateHMS = this.rs.getString("UPDATE_HMS");

		}else {

			return false;

		}

		this.disConnect();

		return updateYMD.equals(latestUpdateYMD) && updateHMS.equals(latastUpdateHMS);

	}


	/**
	 * プリペアードステートメントを用意して、SQLインジェクション対策をします。
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	protected PreparedStatement makePreparedStatement(String sql) throws Exception{

		PreparedStatement stmt = this.con.prepareStatement(sql);
		return stmt;

	}


	/**
	 * DBとの接続を確立する
	 */
	protected void connect() throws Exception{

		if(Constant.IS_DEVELOP) {

			this.con = (Connection) DriverManager.getConnection(Constant.DEV_DB_URL, Constant.DEV_DB_IDPASS, Constant.DEV_DB_IDPASS);

		}else {

			this.con = (Connection) DriverManager.getConnection(Constant.PROC_DB_URL, Constant.PROC_DB_IDPASS, Constant.PROC_DB_IDPASS);

		}

		this.con.setAutoCommit(false);

	}


	/**
	 * 接続も含めて閉じる。
	 * @throws Exception
	 */
	protected void disConnect() throws Exception{

		if(this.rs != null) {

		     this.rs.close();

		}

		if(this.stmt != null) {

			this.stmt.close();

		}

		if(this.con != null) {

		     this.con.close();

		}

	}


	/**
	 * 開発用に、実行されたSQLをカンニングするためのSQL
	 * @param sql
	 */
	private void debugQuery(String sql, boolean useLog4j) {

		LoggerSingleton.getInstance().getLogger().debug(sql);

		if(useLog4j){

			LoggerSingleton.getInstance().getLogger().info(sql);

		}

	}


}

