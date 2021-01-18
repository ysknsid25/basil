package jp.co.basil.app.dailyArchivementList;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.basil.app.fileManager.Poi;

/**
 * @author NOMOS) Yoshida
 * 進捗報告用にXLSフォーマットを作成します。
 *
 */
public class DailyReportMaker extends Poi{

	public static final String KEY_WORKNO = "workNo";
	public static final String KEY_DAILYFUNCP = "dailyFuncp";
	public static final String KEY_SHIMEI = "shimei";
	public static final String KEY_ARC_TOTALFUNCP = "arcTotalFuncp";
	public static final String KEY_PLAN_TOTALFUNCP = "planTotalFuncp";
	public static final String KEY_USER_DAILY_FUNCP = "userDailyFuncp";

	public static final String KEY_TITLE_CELL = "title";
	public static final String KEY_TOTALFUNCP_CELL = "totalFuncp";

	private String taegrtDay = "";


	/**
	 * コンストラクタ
	 * @param inputPath
	 * @param outPutPath
	 * @param fileName
	 * @throws Exception
	 */
	protected DailyReportMaker(String formatId, String targetDay) throws Exception{

		super(formatId);
		this.taegrtDay = targetDay;

	}


	/**
	 * ファイルへの出力処理
	 * @param totalValueList
	 * @param dailyArcMapList
	 * @return 出力先ファイル絶対パス
	 */
	protected String execute(ArrayList<HashMap<String, String>> totalValueList, ArrayList<HashMap<String, String>> dailyArcMapList) {

		Sheet sheet;
		Row outputRow;
		int beginRow = 1;
		int nowLine = beginRow;

		//作業対象のシートを取得し、ヘッダに出力対象日を記録
		sheet = wb.getSheetAt(0);
		outputRow = sheet.getRow(0);
	    this.setCellValue(outputRow.getCell(0), this.taegrtDay);

		//トータルの実績工数を出力
		for(HashMap<String, String> valueMap : totalValueList){

			HashMap<String, String> outPutValmap = this.makeCellVal(valueMap);
			outputRow = sheet.getRow(nowLine);
		    this.setCellValue(outputRow.getCell(1), outPutValmap.get(KEY_TITLE_CELL));
		    this.setCellValue(outputRow.getCell(6), outPutValmap.get(KEY_TOTALFUNCP_CELL));

		    nowLine++;

		}

		//その日の担当者別実績工数を出力
		nowLine = beginRow + 17;
		for(HashMap<String, String> valueMap : dailyArcMapList) {

			outputRow = sheet.getRow(nowLine);
		    this.setCellValue(outputRow.getCell(1), valueMap.get(KEY_SHIMEI));
		    this.setCellValue(outputRow.getCell(2), valueMap.get(KEY_USER_DAILY_FUNCP));

		    nowLine++;

		}

		this.outPutFile();

		return this.fileName;

	}


	/**
	 * 一行分のデータを作成して返します。
	 * @param valueMap
	 * @return
	 */
	private HashMap<String, String> makeCellVal(HashMap<String, String> valueMap) {

		HashMap<String, String> retMap = new HashMap<String, String>();

		String workNo = valueMap.get(KEY_WORKNO);
		String dailyFuncp = valueMap.get(KEY_DAILYFUNCP);
		String shimei = valueMap.get(KEY_SHIMEI);
		String arcTotalFuncp = valueMap.get(KEY_ARC_TOTALFUNCP);
		String planTotalFuncp = valueMap.get(KEY_PLAN_TOTALFUNCP);

		StringBuffer title = new StringBuffer();
		StringBuffer total = new StringBuffer();

		title.append(workNo + "(大学様案件番号:" + workNo + ")");
		title.append(System.lineSeparator());
		title.append(dailyFuncp + "h");
		title.append(System.lineSeparator());
		title.append(shimei);

		total.append("実工数/予定工数");
		total.append(System.lineSeparator());
		total.append(arcTotalFuncp + "/" + planTotalFuncp);

		retMap.put(KEY_TITLE_CELL, title.toString());
		retMap.put(KEY_TOTALFUNCP_CELL, total.toString());

		return retMap;

	}


}
