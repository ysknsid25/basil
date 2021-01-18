package jp.co.basil.app.menuBar;

/**
 * @author NOMOS) Yoshida
 * MenuBarの活性・非活性を管理する。
 *
 */
public class MenuBarStatusBean {

	private boolean isSelectedWorkArcList = false;
	private boolean isSelectedDaily = false;
	private boolean isSelectedMontjly = false;
	private boolean isSelectedWorkRegist = false;
	private boolean isSelectedArcref = false;


	/**
	 * コンストラクタ
	 */
	public MenuBarStatusBean(boolean isSelectedWorkArcList, boolean isSelectedDaily, boolean isSelectedMontjly, boolean isSelectedWorkRegist, boolean isSelectedArcref) {

		this.isSelectedWorkArcList = isSelectedWorkArcList;
		this.isSelectedDaily = isSelectedDaily;
		this.isSelectedMontjly = isSelectedMontjly;
		this.isSelectedWorkRegist = isSelectedWorkRegist;
		this.isSelectedArcref = isSelectedArcref;

	}


	public boolean isSelectedWorkArcList() {
		return isSelectedWorkArcList;
	}

	public boolean isSelectedDaily() {
		return isSelectedDaily;
	}

	public boolean isSelectedMontjly() {
		return isSelectedMontjly;
	}

	public boolean isSelectedWorkRegist() {
		return isSelectedWorkRegist;
	}

	public boolean isSelectedArcref() {
		return isSelectedArcref;
	}

}
