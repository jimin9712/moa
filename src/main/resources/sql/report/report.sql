CREATE SEQUENCE SEQ_REPORT;

CREATE TABLE TBL_REPORT(
                           ID NUMBER CONSTRAINT PK_TBL_REPORT PRIMARY KEY,
                           POST_ID NUMBER NOT NULL,
                           MEMBER_ID NUMBER NOT NULL,
                           REPORT_REASON VARCHAR2(255) NOT NULL,
                           REPORT_STATUS NUMBER NOT NULL,
                           CONSTRAINT FK_REPORT_POST FOREIGN KEY(POST_ID)
                               REFERENCES TBL_POST(ID),
                           CONSTRAINT FK_REPORT_MEMBER FOREIGN KEY(MEMBER_ID)
                               REFERENCES TBL_MEMBER(ID)
);

SELECT *FROM TBL_REPORT;
SELECT * FROM TBL_POST;
SELECT * FROM TBL_REPLY;
DROP TABLE TBL_REPORT;

SELECT RP2.ID, RP2.MEMBER_NICKNAME, RP2.CREATED_DATE, RP2.POST_TITLE, RP2.POST_VIEW, RP2.REPLY_COUNT, RP2.REPORT_REASON, RP2.REPORT_STATUS
FROM (
         SELECT RP1.*, ROWNUM AS R
         FROM (
                  SELECT RP.ID, M.MEMBER_NICKNAME, P.CREATED_DATE, P.POST_TITLE, P.POST_VIEW, COUNT(RE.ID) AS REPLY_COUNT, RP.REPORT_REASON, RP.REPORT_STATUS
                  FROM TBL_REPORT RP
                           JOIN TBL_MEMBER M ON RP.MEMBER_ID = M.ID
                           JOIN TBL_POST P ON RP.POST_ID = P.ID
                           LEFT JOIN TBL_REPLY RE ON P.ID = RE.POST_ID
                  GROUP BY RP.ID, M.MEMBER_NICKNAME, P.CREATED_DATE, P.POST_TITLE, P.POST_VIEW, RP.REPORT_REASON, RP.REPORT_STATUS
                  ORDER BY RP.ID DESC
              ) RP1
         WHERE ROWNUM <= 10
     ) RP2
WHERE RP2.R >= 1;


UPDATE TBL_POST P
SET
    P.POST_TITLE = (SELECT #{postTitle} FROM TBL_REPORT RP WHERE RP.POST_ID = P.ID),
    P.POST_CONTENT = (SELECT #{postContent} FROM TBL_REPORT RP WHERE RP.POST_ID = P.ID)
WHERE P.ID = #{id}





