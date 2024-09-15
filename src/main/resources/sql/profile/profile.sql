CREATE TABLE TBL_PROFILE (
    ID NUMBER CONSTRAINT PK_TBL_PROFILE PRIMARY KEY,
    USER_ID NUMBER NOT NULL,
    FILE_NAME VARCHAR2(255) NOT NULL,
    FILE_PATH VARCHAR(255), -- 첨부파일 경로
    FILE_TYPE VARCHAR2(50),
    FILE_SIZE NUMBER,
    CREATED_DATE DATE DEFAULT SYSDATE,
    CONSTRAINT FK_PROFILE_USER FOREIGN KEY(USER_ID)
    REFERENCES TBL_USER(ID)
);