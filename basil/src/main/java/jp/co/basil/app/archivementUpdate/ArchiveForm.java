package jp.co.basil.app.archivementUpdate;

import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.util.DateTimeUtil;

/**
 * @author NOMOS) Yoshida
 * 作業情報登録のFormを格納する。
 *
 */
public class ArchiveForm {

	private String targetDay = "";
	private String workId = "";
	private float funcp = 0;
	private boolean isDelete = false;
	private boolean isFinished = false;


	/**
	 * コンストラクタ
	 */
	public ArchiveForm() {
	}


	public String getTargetDay() {
		return targetDay;
	}


	public void setTargetDay(String targetDay) {
		this.targetDay = DateTimeUtil.convDate(targetDay);
	}


	public String getWorkId() {
		return workId;
	}


	public void setWorkId(String workId) {
		this.workId = workId;
	}


	public float getFuncp() {
		return funcp;
	}


	public void setFuncp(String funcp) {
		this.funcp = Float.parseFloat(funcp);
	}


	public boolean isDelete() {
		return isDelete;
	}


	public void setDelete(String isDelete) {
		this.isDelete = Constant.CHECKBOX_ON.equals(isDelete);
	}


	public boolean isFinished() {
		return isFinished;
	}


	public void setFinished(String isFinished) {
		this.isFinished = Constant.CHECKBOX_ON.equals(isFinished);
	}


}
