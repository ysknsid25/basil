#作業テーブル
CREATE TABLE WORK_TBL (
	WORK_ID	         INT PRIMARY KEY,
	USER_NO	         INT NOT NULL,
	PROJECT_ID	     INT NOT NULL,
	WORK_NO	         VARCHAR(10) NOT NULL,
	WORK_NM	         VARCHAR(100) NOT NULL,
	ORVERVIEW	       VARCHAR(500) NOT NULL,
	WORK_KBN	       VARCHAR(1),
	CUSTOM_KBN	     VARCHAR(1) NOT NULL,
	PROCESS_KBN	     VARCHAR(1),
	PLAN_BEGIN_YMD	 VARCHAR(10) NOT NULL,
	PLAN_END_YMD	   VARCHAR(10) NOT NULL,
	TOTAL_FUNCP	     FLOAT NOT NULL,
	SERACH_FUNCP	   FLOAT NOT NULL,
	PG_FUNCP	       FLOAT NOT NULL,
	PT_FUNCP	       FLOAT NOT NULL,
	INCIDENTAL_FUNCP FLOAT NOT NULL,
	UPDATE_YMD	     VARCHAR(10) NOT NULL,
	UPDATE_HMS	     VARCHAR(12) NOT NULL
);

#作業テーブルのデータ
INSERT INTO WORK_TBL VALUES(0, 1, 1, '01', 'テスト作業0', '完了済みのテスト', '2', '2', '0', '2020/04/10' , '2020/04/30',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 1, 1, '01', 'テスト作業1', 'テスト用の作業一つ目です。', '0', '2', '0', '2020/04/10' , '2020/04/30',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 1, 1, '02', 'テスト作業2', 'テスト用の作業2つ目です。', '0', '2', '1','2020/04/30' , '2020/05/01',
60, 5, 30, 20, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 1, 1, '03', 'テスト作業3', 'テスト用の作業3つ目です。', '0', '1', '0','2020/05/01' , '2020/05/20',
100, 5, 60, 30, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 1, 1, 'XXXX-01', 'テスト作業4', 'テスト用の作業4つ目です。', '0', '2', '1','2020/05/21' , '2020/05/31',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 2, 1, '04', 'テスト作業5', 'テスト用の作業5つ目です。', '0', '2', '0', '2020/04/10' , '2020/04/20',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 2, 1, 'XXXX-02', 'テスト作業6', 'テスト用の作業6つ目です。', '0', '1', '1', '2020/04/21' , '2020/04/30',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 3, 1, '05', 'テスト作業7', 'テスト用の作業7つ目です。', '0', '2', '0', '2020/05/01' , '2020/05/05',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
