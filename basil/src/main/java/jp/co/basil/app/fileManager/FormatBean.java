package jp.co.basil.app.fileManager;

/**
 * @author NOMOS) Yoshida
 * フォーマット情報を格納するBean
 *
 */
public class FormatBean {

	private String formatPath = "";
	private String formatName = "";
	private String extension = "";
	private String outputPath = "";

	/**
	 * コンストラクタ
	 */
	public FormatBean() {
	}

	public String getFormatPath() {
		return formatPath;
	}

	public void setFormatPath(String formatPath) {
		this.formatPath = formatPath;
	}

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

}
