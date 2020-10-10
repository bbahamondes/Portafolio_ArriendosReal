-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_USERS (
    username IN VARCHAR2,
    email IN VARCHAR2,
    password IN VARCHAR2,
    profile_id IN INTEGER,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO Users (username,email,password,profiles_profile_id) VALUES (username,email,password,profile_id) RETURNING user_id INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_USERS (
    in_user_id IN VARCHAR2,
    resultado OUT NUMBER,
    out_users OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_users FOR 
            SELECT * FROM Users WHERE user_id = in_user_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_USERS (
    user_id IN INTEGER,
    username IN VARCHAR2,
    email IN VARCHAR2,
    password IN VARCHAR2,
    profile_id IN INTEGER,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE Users SET username=username,email=email,password=password,PROFILES_PROFILE_ID=profile_id WHERE user_id = user_id; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_USERS (
    user_id IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM Users WHERE USER_ID = user_id;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;
