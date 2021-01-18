use BasilTest;
ALTER DATABASE Basil CHARACTER SET UTF8;
FLUSH PRIVILEGES;

CREATE TABLE ACHIEVE_DAY_TBL (
	WORK_ID	     INT,
	USER_NO	     INT,
  BEGIN_YMD	       VARCHAR(10),
	END_YMD	         VARCHAR(10),
	FINISH_KBN VARCHAR(1),
	CONSTRAINT ACHIEVE_DAY_TBL PRIMARY KEY(WORK_ID, USER_NO)
);

CREATE TABLE ACHIEVE_TBL (
	WORK_ID	     INT,
	USER_NO	     INT,
	REGISTED_YMD VARCHAR(10),
	FUNCP        FLOAT NOT NULL,
	CONSTRAINT PK_ACHIEVE_TBL PRIMARY KEY(WORK_ID, USER_NO, REGISTED_YMD)
);

CREATE TABLE FORMATM_TBL (
	FORMAT_ID varchar(5) PRIMARY KEY,
	FORMAT_PATH varchar(200) NOT NULL,
	FORMAT_NAME varchar(200) NOT NULL,
	EXTENSION varchar(5) NOT NULL,
	OUTPUTPATH varchar(200) NOT NULL
);

INSERT INTO FORMATM_TBL VALUES ('XLS01','C:\\workSpace\\basil\\basil\\src\\main\\resources\\static\\format\\fReportFormat.xls','fReportFormat','.xls','C:\\workSpace\\basil\\basil\\src\\main\\resources\\static\\download\\');

CREATE TABLE PROJECT_TBL (
	PROJECT_ID    INT PRIMARY KEY,
	PROJECT_NM    VARCHAR(100) NOT NULL,
	BEGIN_YMD     VARCHAR(10) NOT NULL,
	END_YMD       VARCHAR(10) NOT NULL,
	LEADER_NO     INT NOT NULL,
	SUB_LEADER_NO INT,
	UPDATE_YMD    VARCHAR(10) NOT NULL,
	UPDATE_HMS    VARCHAR(12) NOT NULL
);

CREATE TABLE SEQUENCE (
  SEQ_NAME VARCHAR(50) PRIMARY KEY,
  CURRENT_VAL INT NOT NULL,
  INCREMENT INT NOT NULL
);

#シークエンスの追加
INSERT INTO SEQUENCE VALUES ('PROJECT_ID', 0, 1);
INSERT INTO SEQUENCE VALUES ('WORK_ID', 0, 1);
INSERT INTO SEQUENCE VALUES ('USER_NO', 0, 1);

#現在のシークエンス番号を取得する
DELIMITER $
CREATE FUNCTION currval (seqName VARCHAR(50))
    RETURNS INTEGER
    LANGUAGE SQL
    DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN
    DECLARE value INTEGER;
    SET value = 0;
    SELECT CURRENT_VAL INTO value
        FROM SEQUENCE
        WHERE SEQ_NAME = seqName;
    RETURN value;
END
$
DELIMITER ;

#次のシークエンス番号を取得する
DELIMITER $
CREATE FUNCTION nextval (seqName VARCHAR(50))
    RETURNS INTEGER
    LANGUAGE SQL
    DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN
    UPDATE SEQUENCE
    SET CURRENT_VAL = CURRENT_VAL + INCREMENT
    WHERE SEQ_NAME = seqName;
    RETURN currval(seqName);
END
$
DELIMITER ;

CREATE TABLE USER_TBL (
	USER_NO	      int PRIMARY KEY,
	USER_ID	      varchar(10) NOT NULL,
	PASSWORD      varchar(10) NOT NULL,
	SHIMEI	      varchar(20) NOT NULL,
	AUTHORITY_KBN varchar(1) NOT NULL,
	NOTIFICATION varchar(1),
    UNIQUE idPass_index (USER_ID, PASSWORD)
);

commit;

INSERT INTO USER_TBL values (1, 'satou', 'tarou', '佐藤', '1', '1');
INSERT INTO USER_TBL values (2, 'suzuki', 'jiro', '鈴木', '0', '1');
INSERT INTO USER_TBL values (3, 'tanaka', 'saburo', '田中', '0', '');

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

CREATE VIEW ARC_TOTALFUNCP_VIW (

	WORK_ID,
    USER_NO,
    ARC_TOTAL_FUNCP

)
AS
select
	WORK_ID,
	USER_NO,
    SUM(FUNCP) as ARC_TOTAL_FUNCP
from
	ACHIEVE_TBL
group by
	WORK_ID,
    USER_NO;

    #日別の作業工数を取得するためのビュー
    CREATE VIEW DAILY_ARC_VIW(

    	USER_NO,
    	SHIMEI,
    	REGISTED_DAY,
    	WORK_ID,
    	WORK_NO,
    	WORK_NM,
    	FUNCP

    )
    AS
    select
    	u.USER_NO,
    	u.SHIMEI,
      a.REGISTED_YMD,
    	w.WORK_ID,
      w.WORK_NO,
      w.WORK_NM,
      a.FUNCP
    from
    	ACHIEVE_TBL a
        INNER JOIN WORK_TBL w
    		ON a.WORK_ID = w.WORK_ID
    			and a.USER_NO = w.USER_NO
    	INNER JOIN USER_TBL  u
    		ON w.USER_NO = u.USER_NO;

        CREATE VIEW TOTAL_FUNCP_VIW (

        PROJECT_ID,
        WORK_NO,
        TOTAL_FUNCP

        )
        AS
        select
        	PROJECT_ID,
        	WORK_NO,
          SUM(TOTAL_FUNCP)
        from
        	WORK_TBL
        group by
        	WORK_NO;

          #作業状態ビュー
          CREATE VIEW WORK_STATUS_VIW (

          WORK_ID,
          USER_NO,
          WORK_NO,
          WORK_NM,
          PLAN_BEGIN_YMD,
          END_BEGIN_YMD,
          BEGIN_YMD,
          END_YMD,
          FINISH_KBN

          )
          AS
          select
          	w.WORK_ID,
              w.USER_NO,
              w.WORK_NO,
              w.WORK_NM,
              w.PLAN_BEGIN_YMD,
              w.PLAN_END_YMD,
              a.BEGIN_YMD,
              a.END_YMD,
              a.FINISH_KBN
          from
          	WORK_TBL w
              LEFT JOIN
              ACHIEVE_DAY_TBL a
              ON
              w.WORK_ID = a.WORK_ID
              and
              w.USER_NO = a.USER_NO;
