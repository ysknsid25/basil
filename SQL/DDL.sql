CREATE DATABASE basil;
GRANT ALL PRIVILEGES ON basil.* TO 'basil'@'localhost';
FLUSH PRIVILEGES;

use Basil;

ALTER DATABASE Basil CHARACTER SET UTF8;

#指定日に実績を入力しておらず、かつ通知対象になっている相手に通知を送る
select
	u.SHIMEI
from
	USER_TBL u
		LEFT JOIN
			(select * from DAILY_ARC_VIW where REGISTED_DAY = '2020/04/23') a
		ON
			a.USER_NO = u.USER_NO
			where
					a.USER_NO is null
				and
					u.NOTI'basil = '1';

#指定年別の特定のユーザーの実績を取得する
select
	WORK_NM,
	REGISTED_YMD,
  FUNCP
from
	ACHIEVE_TBL
where
	REGISTED_YMD like '2020/04%'
	and
	USER_NO = ''
order by REGISTED_YMD;

#作業別の総工数を取得する
select
	u.SHIMEI,
	main.WORK_NO,
	main.WORK_NM,
	main.ORVERVIEW,
	main.CUSTOM_KBN,
	main.PLAN_END_YMD,
	main.END_YMD,
	main.TOTAL_FUNCP,
	main.SERACH_FUNCP,
	main.PG_FUNCP,
	main.PT_FUNCP,
	main.ARC_TOTAL_FUNCP
from
	(select
		w.*,
		total.ARC_TOTAL_FUNCP
	from
		WORK_TBL w
			LEFT JOIN
		(select WORK_ID, USER_NO, SUM(FUNCP) as ARC_TOTAL_FUNCP from ACHIEVE_TBL group by WORK_ID, USER_NO) total
			ON
				w.WORK_ID = total.WORK_ID
				and
				w.USER_NO = total.USER_NO
	)main
		INNER JOIN
	USER_TBL u
		ON
	main.USER_NO = u.USER_NO;

#報告資料用のデータを抜く
select
	u.SHIMEI,
	w.WORK_NO,
	dayArc.FUNCP,
	totalArc.TOTAL_FUNCP,
	w.TOTAL_FUNCP as P_TOTAL_FUNCP
from
	(select WORK_ID, USER_NO, FUNCP from ACHIEVE_TBL where REGISTED_YMD = '2020/04/22') dayArc
		INNER JOIN
	(select WORK_ID, USER_NO, SUM(FUNCP) as TOTAL_FUNCP from ACHIEVE_TBL group by WORK_ID, USER_NO) totalArc
		ON
			dayArc.WORK_ID = totalArc.WORK_ID and dayArc.USER_NO = totalArc.USER_NO
		INNER JOIN
	(select WORK_ID, USER_NO, WORK_NO, TOTAL_FUNCP from WORK_TBL) w
		ON
			dayArc.WORK_ID = w.WORK_ID and dayArc.USER_NO = w.USER_NO
		INNER JOIN
	(select USER_NO, SHIMEI from USER_TBL) u
		ON w.USER_NO = u.USER_NO;
