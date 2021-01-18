package jp.co.basil.app.fileManager;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.util.DateTimeUtil;

/**
 * @author NOMOS) Yoshida
 * Poiの基本的な操作を集めたクラス
 *
 */
public class Poi {

	protected Workbook wb;
	protected CellStyle newCellStyle = null;
	protected String inputPath = "";
	protected String outPutPath = "";
	protected String fileName = "";


	/**
	 * コンストラクタ
	 * @param filePath
	 * @param outPutPath
	 * @param fileName
	 * @throws Exception
	 */
	protected Poi(String formatId) throws Exception{

		FormatBean formatBean = new FormatTblDBAccess(formatId).getFormatInfo();

		this.inputPath = formatBean.getFormatPath();
		this.fileName = formatBean.getFormatName() + DateTimeUtil.convYearOrMonth("", Constant.DATETIME_FOAMRT) + formatBean.getExtension();
	    this.outPutPath = formatBean.getOutputPath() + this.fileName;

		FileInputStream in = new FileInputStream(this.inputPath);
		this.wb = WorkbookFactory.create(in);
		in.close();

	}


	//セルに値をセットする
	protected void setCellValue(Cell outputCell, String value) {

		Cell workCell;

		workCell = outputCell;
		workCell.setCellValue(value);

	}


	//作成した定義書を新規ファイルとして出力する終了処理
	protected void outPutFile() {

		FileOutputStream out = null;

		try{

			out = new FileOutputStream(this.outPutPath);
			this.wb.write(out);

		}catch(IOException e){

			System.out.println(e.toString());

		}finally{

			try {

			out.close();

			}catch(IOException e){

				System.out.println(e.toString());

			}

		}

	}


}
