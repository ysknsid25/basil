package jp.co.basil.app.db;

/**
 * @author NOMOS) Yoshida
 * 排他制御時に発生した例外をキャッチして、呼び出し元に伝えるための例外クラス
 *
 */
public class DBexception extends Exception {

	//warningを回避するための宣言
	private static final long serialVersionUID = 1L;

	//コンストラクタ
	public DBexception(String msg){
		super(msg);
	}

}
