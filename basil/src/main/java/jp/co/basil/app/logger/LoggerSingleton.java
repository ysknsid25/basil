package jp.co.basil.app.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author NOMOS) Yoshida
 * ロギング機能を提供するシングルトンです。
 *
 */
public class LoggerSingleton {

	private static LoggerSingleton singleton = new LoggerSingleton();
	private Logger logger = null;


	/**
	 * コンストラクタ
	 */
	private LoggerSingleton() {

		this.logger = LogManager.getLogger();

	}


	/**
	 * シングルトンを返す
	 * @return
	 */
	public static LoggerSingleton getInstance() {

		return singleton;

	}


	/**
	 * Log4jのロガーを返す
	 * @return
	 */
	public Logger getLogger() {

		return this.logger;

	}



}
