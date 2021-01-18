package jp.co.basil.app.archivementUpdate;

/**
 * @author NOMOS) Yoshida
 * 作業の状態を格納するBean
 *
 */
public class WorkStatusBean {

	private String beginYMD = "";
	private String endYMD = "";
	private String finishKbn = "";

	public String getBeginYMD() {
		return beginYMD;
	}
	public void setBeginYMD(String beginYMD) {
		this.beginYMD = beginYMD;
	}
	public String getEndYMD() {
		return endYMD;
	}
	public void setEndYMD(String endYMD) {
		this.endYMD = endYMD;
	}
	public String getFinishKbn() {
		return finishKbn;
	}
	public void setFinishKbn(String finishKbn) {
		this.finishKbn = finishKbn;
	}


}
