package jp.co.basil.app.fileManager;

import java.sql.ResultSet;

import jp.co.basil.app.db.DBAccess;
import jp.co.basil.app.util.StringUtil;

/**
 * @author 出力用フォーマットの情報を取得します。
 *
 */
public class FormatTblDBAccess extends DBAccess {

	private String formatId = "";


	/**
	 * コンストラクタ
	 * @param formatId
	 */
	protected FormatTblDBAccess (String formatId) {

		this.formatId = formatId;

	}


	/**
	 * フォーマット情報を取得するための入り口
	 * @return
	 * @throws Exception
	 */
	protected FormatBean getFormatInfo() throws Exception{

		return this.getColVal(this.executeSelect(this.getSQL(this.formatId)));

	}


	/**
	 * フォーマット情報を取得するSQL
	 * @param formatId
	 * @return
	 */
	private String getSQL(String formatId) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("FORMAT_PATH, ");
			sb.append("FORMAT_NAME, ");
			sb.append("EXTENSION, ");
			sb.append("OUTPUTPATH ");
		sb.append("from ");
			sb.append("FORMATM_TBL ");
		sb.append("where ");
			sb.append("FORMAT_ID = '" + formatId + "'");

		return sb.toString();

	}


	/**
	 * DBから取得したデータを取り出してBeanにセットする。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private FormatBean getColVal(ResultSet rs) throws Exception{

		FormatBean formatBean = new FormatBean();

		if(rs.next()) {

			formatBean.setFormatPath(StringUtil.getString(rs.getString("FORMAT_PATH")));
			formatBean.setFormatName(StringUtil.getString(rs.getString("FORMAT_NAME")));
			formatBean.setExtension(StringUtil.getString(rs.getString("EXTENSION")));
			formatBean.setOutputPath(StringUtil.getString(rs.getString("OUTPUTPATH")));

		}

		return formatBean;

	}

}
