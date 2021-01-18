package jp.co.basil.app.notifier;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author NOMOS) yoshida
 * 任意のメッセージを受けて、Google HangOuts Chatに通知を行います。
 *
 */
public class Notifier {


	/** JSON形式の書式で文字列を格納 */
	private String JSON_data = "";

	/** ChatRoomのURL */
	private String url = "";


	/**
	 * コンストラクタ
	 * @param message 通知したいメッセージ
	 * @param url ChatRoomのURL
	 */
	public Notifier(String message, String url) {

		this.JSON_data = this.makeJSONdata(message);
		this.url = url;

	}


	/**
	 * 通知に必要なJSONの形式に受け取ったメッセージを変換します。
	 * @param message 通知したいメッセージ
	 * @return
	 */
	private String makeJSONdata(String message) {

		return "{\"text\":\"" + message + "\"}";

	}


	/**
	 * HangOuts Chatへの通知を行う。
	 * @return true:通知成功 false:通知失敗
	 * @throws Exception
	 */
   public boolean notice() throws Exception{

	   HttpURLConnection con = null;
       int resultCd = 200;

       try {

    	   con = this.initPost(con);

    	   // HTTPレスポンスコード
    	   resultCd = con.getResponseCode();

       } catch (Exception e1) {

           e1.printStackTrace();

       } finally {

           if (con != null) {

               // コネクションを切断
               con.disconnect();

           }
       }

       return resultCd != HttpURLConnection.HTTP_OK;

   }


   /**
    * HTTPでPOST送信するための準備
    * @param con HttpURLConnection
    * @throws Exception
    */
   private HttpURLConnection initPost(HttpURLConnection con) throws Exception{

       URL url = new URL(this.url);
       con = (HttpURLConnection) url.openConnection();

       // HTTPリクエストコード
       con.setDoOutput(true);
       con.setRequestMethod("POST");

       // データがJSONであること、エンコードを指定する
       con.setRequestProperty("Content-Type", "application/json");

       // POSTデータの長さを設定
       con.setRequestProperty("Content-Length", String.valueOf(this.JSON_data.length()));

       // リクエストのbodyにJSON文字列を書き込む
       OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
       out.write(this.JSON_data);
       out.flush();
       con.connect();

       return con;

   }


}

