-- Users
CREATE OR REPLACE PROCEDURE SP_GET_ALL_USERS (
    out_users OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_users FOR
        SELECT * FROM Users;
END;

-- Profiles
CREATE OR REPLACE PROCEDURE SP_GET_ALL_PROFILES (
       out_profiles OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_profiles FOR
        SELECT * FROM Profiles;
END;

-- Personas
CREATE OR REPLACE PROCEDURE SP_GET_ALL_PERSONA (
    out_persona OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_persona FOR
        SELECT * FROM personas;
END;

-- Departmentos
CREATE OR REPLACE PROCEDURE SP_GET_ALL_DEPARTAMENTO (
    out_departamento OUT SYS_REFCURSOR
) AS BEGIN
        OPEN out_departamento FOR
            SELECT * FROM departmentos;
END;

-- Tipos Servicio
CREATE OR REPLACE PROCEDURE SP_GET_ALL_TIPO_SERVICIO (
    out_tipo_servicio OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_tipo_servicio FOR
        SELECT * FROM tipos_servicio;
END;

-- Servicios
CREATE OR REPLACE PROCEDURE SP_GET_ALL_SERVICIO (
    out_servicio OUT SYS_REFCURSOR  
) AS BEGIN
    OPEN out_servicio FOR
        SELECT * FROM servicios;
END;

-- Inventarios
CREATE OR REPLACE PROCEDURE SP_GET_ALL_INVENTARIO (
    out_inventario OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_inventario FOR
        SELECT * FROM inventarios;
END;

-- Mantenciones
CREATE OR REPLACE PROCEDURE SP_GET_ALL_MANTENCIONES (
    out_mantencion OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_mantencion FOR
        SELECT * FROM Mantenciones;
END;

-- Checkin
CREATE OR REPLACE PROCEDURE SP_GET_ALL_CHECKIN (
    out_checkin OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_checkin FOR
        SELECT * FROM Checkin;
END;

-- Checkout 
CREATE OR REPLACE PROCEDURE SP_GET_ALL_CHECKOUT (
    out_checkout OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_checkout FOR
        SELECT * FROM Checkout;
END;

-- Multas
CREATE OR REPLACE PROCEDURE SP_GET_ALL_MULTAS (
    out_multa OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_multa FOR
        SELECT * FROM multas;
END;

-- Reservas
CREATE OR REPLACE PROCEDURE SP_GET_ALL_RESERVA (
    out_reserva OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_reserva FOR
        SELECT * FROM Reservas;
END;

-- Estadias
CREATE OR REPLACE PROCEDURE SP_GET_ALL_ESTADIA (
       out_resultado OUT NUMBER,
    out_estadia OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_estadia FOR
        SELECT * FROM Estadias;
END;

-- Acompanantes
CREATE OR REPLACE PROCEDURE SP_GET_ALL_ACOMPANANTE (
    out_acompanante OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_acompanante FOR
        SELECT * FROM acompanantes;
END;