CREATE SEQUENCE SEQ_POST;

CREATE TABLE TBL_POST(
    ID NUMBER CONSTRAINT PK_POST PRIMARY KEY,
    POST_TITLE VARCHAR2(255) NOT NULL,
    POST_CONTENT VARCHAR2(255) NOT NULL,
    POST_TYPE NUMBER NOT NULL,
    MEMBER_ID NUMBER NOT NULL,
    POST_VIEW NUMBER DEFAULT 0,
    CREATED_DATE DATE DEFAULT SYSDATE,
    UPDATED_DATE DATE DEFAULT SYSDATE,
    CONSTRAINT FK_POST_USER FOREIGN KEY(MEMBER_ID)
    REFERENCES TBL_MEMBER(ID)
);

SELECT * FROM (SELECT ROWNUM R, P1.*
               FROM (SELECT ID, POST_TITLE, POST_CONTENT, POST_TYPE, CREATED_DATE, MEMBER_ID, POST_VIEW,CREATED_DATE
                     FROM TBL_POST
                     ORDER BY POST_VIEW DESC) P1
               WHERE ROWNUM <= 10) P2
WHERE P2.R >= 1;



ALTER TABLE TBL_POST RENAME COLUMN USER_ID TO MEMBER_ID;


SELECT * FROM TBL_POST;
