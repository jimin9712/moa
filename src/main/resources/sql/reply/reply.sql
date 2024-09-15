CREATE TABLE TBL_REPLY(
    ID NUMBER CONSTRAINT PK_REPLY PRIMARY KEY,
    POST_ID NUMBER NOT NULL,
    USER_ID NUMBER NOT NULL,
    REPLY_CONTENT VARCHAR2(255) NOT NULL,
    CREATED_DATE DATE DEFAULT SYSDATE,
    UPDATED_DATE DATE DEFAULT SYSDATE,
    GROUP_ID NUMBER NOT NULL,
    REPLY_DEPTH NUMBER NOT NULL,
    CONSTRAINT FK_REPLY_POST FOREIGN KEY(POST_ID)
    REFERENCES TBL_POST(ID),
    CONSTRAINT FK_REPLY_USER FOREIGN KEY(USER_ID)
    REFERENCES TBL_USER(ID)
);