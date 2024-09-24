CREATE TABLE TBL_USER_SKILL(
    ID NUMBER CONSTRAINT USER_POSSESSED_TECHNOLOGY PRIMARY KEY,
    TECHNOLOGY_NAME VARCHAR2(255),
    PROFICIENCY VARCHAR2(255),
    EXPERIENCE VARCHAR2(255),
    USER_ID NUMBER NOT NULL,
    CREATED_DATE DATE DEFAULT SYSDATE,
    UPDATED_DATE DATE DEFAULT SYSDATE,
    CONSTRAINT FK_USER_P_TECHNOLOGY_USER FOREIGN KEY(USER_ID)
    REFERENCES TBL_USER(ID)
);