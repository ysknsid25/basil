package jp.co.basil.app.archivementUpdate;

import jp.co.basil.app.constant.ConstantKbnBean;

/**
 * @author NOMOS) Yoshida
 * DBから取得した作業情報を格納する。
 *
 */
public class WorkInfoBean {

	private int workId = 0;
	private String workNm = "";


	/**
	 * コストラクタ
	 */
	public WorkInfoBean() {
	}

	public int getWorkId() {
		return workId;
	}
	public void setWorkId(int workId) {
		this.workId = workId;
	}
	public String getWorkNm() {
		return workNm;
	}
	public void setWorkNm(String workNm) {
		this.workNm = workNm;
	}

}
