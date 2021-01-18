package jp.co.basil.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.co.basil.app.constant.Constant;

/**
 * @author NOMOS) Yoshida
 * 日付を取り扱う
 *
 */
public class DateTimeUtil {

	/**
	 * 日付を/形式に統一する。また、空文字だった場合は、現在日付をセットして返す。
	 * @param date
	 * @return
	 */
	public static String convDate(String date) {

		if(date == null || date.isEmpty()) {

			return new SimpleDateFormat(Constant.DATE_FORMAT).format(Calendar.getInstance().getTime());

		}

		//FlatPickerは-形式の日付を扱うので、DBと合わせるために/に変換する
		return date.replaceAll(Constant.HAIHUN, Constant.SLASH);

	}


	/**
	 * 現在日付を返す。YYYY/MM/DDのみならず、YYYY、MMなどの単体でも取得可能
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String convYearOrMonth(String date, String dateFormat) {

		if(date == null || date.isEmpty()) {

			return new SimpleDateFormat(dateFormat).format(Calendar.getInstance().getTime());

		}

		return date;

	}


	/**
	 * 日付の大小を比較します。
	 * @param dateA
	 * @param dateB
	 * @return dateA>dateBの場合true
	 */
	public static boolean compareDate(String dateA, String dateB) {

		Date date1 = null;
		Date date2 = null;

		try {

			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
	        date1 = sdf.parse(dateA);
	        date2 = sdf.parse(dateB);

		}catch(Exception e) {

			return false;

		}

		return date1.compareTo(date2) > 0;

	}

}
