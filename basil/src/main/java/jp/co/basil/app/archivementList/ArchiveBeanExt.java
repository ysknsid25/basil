package jp.co.basil.app.archivementList;

import jp.co.basil.app.dailyArchivementList.ArchiveBean;

/**
 * @author NOMOS) Yoshida<br>
 * TyhmeLeafのクソ仕様のため、YYYY/MM/DDをJavaScriptの引数はめ込んでSubmit使用とすると、<br>
 * 数字と解釈→演算の順にレンダリングされてしまう。<br>
 * つまり、割り算結果がサーバーサイドに飛んできてしまう。<br>
 * これを回避するために、あらかじめYYYYMMDDの形式でhiddenの値を仕込んでおくため、このクラスが存在する。
 */
public class ArchiveBeanExt extends ArchiveBean {

	/** YYYYMMDD */
	private String hiddenRegistedDate = "";

	public String getHiddenRegistedDate() {
		return hiddenRegistedDate;
	}

	public void setHiddenRegistedDate(String hiddenRegistedDate) {
		this.hiddenRegistedDate = hiddenRegistedDate;
	}

}
