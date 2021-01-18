CREATE TABLE FORMATM_TBL (
	FORMAT_ID varchar(5) PRIMARY KEY,
	FORMAT_PATH varchar(200) NOT NULL,
	FORMAT_NAME varchar(200) NOT NULL,
	EXTENSION varchar(5) NOT NULL,
	OUTPUTPATH varchar(200) NOT NULL
);

INSERT INTO FORMATM_TBL VALUES ('XLS01','C:\\workSpace\\basil\\basil\\src\\main\\resources\\static\\format\\fReportFormat.xls','fReportFormat','.xls','C:\\workSpace\\basil\\basil\\src\\main\\resources\\static\\download\\');
