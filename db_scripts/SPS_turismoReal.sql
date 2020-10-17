--USUARIO
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_USERS (
    in_username IN VARCHAR2,
    in_email IN VARCHAR2,
    in_password IN VARCHAR2,
    in_profile_id IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO Users (username,email,password,profiles_profile_id) VALUES (in_username,in_email,in_password,in_profile_id) RETURNING user_id INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_USERS (
    in_user_id IN VARCHAR2,
    out_users OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_users FOR
        SELECT * FROM Users WHERE user_id = in_user_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_USERS (
    in_user_id IN INTEGER,
    in_username IN VARCHAR2,
    in_email IN VARCHAR2,
    in_password IN VARCHAR2,
    in_profile_id IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE Users SET username=in_username,email=in_email,password=in_password,PROFILES_PROFILE_ID=in_profile_id WHERE user_id = in_user_id;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_USERS (
    in_user_id IN NUMBER,
    out_estado OUT NUMBER) AS
BEGIN
    out_estado := 0;
    DELETE FROM Users WHERE USER_ID = in_user_id;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;


--Profiles
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_PROFILES (
    in_profile_name IN VARCHAR2,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO Profiles (profile_name) VALUES (in_profile_name) RETURNING profile_id INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_PROFILES (
    in_profile_id IN VARCHAR2,
    out_users OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_users FOR
        SELECT * FROM Profiles WHERE profile_id = in_profile_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_PROFILES (
    in_profile_id IN INTEGER,
    in_profile_name IN VARCHAR2,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE Profiles SET profile_name=in_profile_name WHERE profile_id = in_profile_id;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_PROFILES (
    in_profile_id IN NUMBER,
    out_estado OUT NUMBER) AS
BEGIN
    out_estado := 0;
    DELETE FROM Profiles WHERE profile_id = in_profile_id;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;


--DEPARTAMENTOS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_DEPARTAMENTO (
    in_nombre IN VARCHAR2,
    in_direccion IN VARCHAR2,
    in_region IN VARCHAR2,
    in_ciudad IN VARCHAR2,
    in_precio IN INTEGER,
    in_disponibilidad IN CHAR,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO departmentos (nombre,direccion,region,ciudad,precio,disponibilidad) VALUES (in_nombre,in_direccion,in_region,in_ciudad,in_precio,in_disponibilidad) RETURNING ID_DEPARTMENTO INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_DEPARTAMENTO (
    in_departamento_id IN VARCHAR2,
    out_departamento OUT SYS_REFCURSOR
) AS BEGIN
        OPEN out_departamento FOR
            SELECT * FROM departmentos WHERE id_departmento = in_departamento_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_DEPARTAMENTO (
    in_id_departamento IN INTEGER,
    in_nombre IN VARCHAR2,
    in_direccion IN VARCHAR2,
    in_region IN VARCHAR2,
    in_ciudad IN VARCHAR2,
    in_precio IN INTEGER,
    in_disponibilidad IN CHAR,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE departmentos SET nombre=in_nombre,direccion=in_direccion,region=in_region,ciudad=in_ciudad,precio=in_precio,disponibilidad=in_disponibilidad WHERE id_departmento = in_id_departamento;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_DEPARTAMENTO (
    in_id_departamento IN NUMBER,
    out_estado OUT NUMBER) AS
BEGIN
    out_estado := 0;
    DELETE FROM departmentos WHERE id_departmento = in_id_departamento;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;

--SERVICIOS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_SERVICIO (
    in_descripcion IN VARCHAR2,
    in_tipos_servicio_id_tipo IN INTEGER,
    in_costo_operacion IN INTEGER,
    in_valor_cliente IN INTEGER,
    in_id_departamento IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO servicios (descripcion, tipos_servicio_id_tipo, costo_operacion, valor_cliente, departmentos_id_departmento) VALUES (in_descripcion, in_tipos_servicio_id_tipo, in_costo_operacion, in_valor_cliente, in_id_departamento) RETURNING id_servicio INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_SERVICIO (
    in_servicio_id IN VARCHAR2,
    out_servicio OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_servicio FOR
        SELECT * FROM servicios WHERE id_servicio = in_servicio_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_SERVICIO (
    in_id_servicios IN INTEGER,
    in_descripcion IN VARCHAR2,
    in_tipos_servicio_id_tipo IN INTEGER,
    in_costo_operacion IN INTEGER,
    in_valor_cliente IN INTEGER,
    in_id_departamento IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE servicios SET descripcion=in_descripcion,tipos_servicio_id_tipo=in_tipos_servicio_id_tipo,costo_operacion=in_costo_operacion,valor_cliente=in_valor_cliente,departmentos_id_departmento=in_id_departamento WHERE id_servicio = in_id_servicios;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_SERVICIO (
    in_id_servicios IN NUMBER,
    out_estado OUT NUMBER) AS
BEGIN
    out_estado := 0;
    DELETE FROM servicios WHERE id_servicio = in_id_servicios;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;    

--TIPOS SERVICIOS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_TIPO_SERVICIO (
    in_descripcion IN VARCHAR2,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO tipos_servicio (descripcion) VALUES (in_descripcion) RETURNING id_tipo INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_TIPO_SERVICIO (
    in_tipo_servicio_id IN VARCHAR2,
    out_tipo_servicio OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_tipo_servicio FOR
        SELECT * FROM tipos_servicio WHERE id_tipo = in_tipo_servicio_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_TIPO_SERVICIO (
    in_id_tipo IN INTEGER,
    in_descripcion IN VARCHAR2,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE tipos_servicio SET descripcion=in_descripcion WHERE id_tipo = in_id_tipo;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_TIPO_SERVICIO (
    in_id_tipo IN NUMBER,
    out_estado OUT NUMBER) AS
BEGIN
    out_estado := 0;
    DELETE FROM tipos_servicio WHERE id_tipo = in_id_tipo;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;

-- PERSONAS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_PERSONA (
    in_rut IN VARCHAR2,
    in_nombre IN VARCHAR2,
    in_apellidos IN VARCHAR2,
    in_telefono IN VARCHAR2,
    in_Users_user_id IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO personas (rut,nombre,apellidos,telefono,Users_user_id) VALUES (in_rut,in_nombre,in_apellidos,in_telefono,in_Users_user_id) RETURNING id_persona INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_PERSONA (
    in_persona_id IN VARCHAR2,
    out_persona OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_persona FOR
        SELECT * FROM personas WHERE id_persona = in_persona_id;
END;

-- UPDATE
-- Note: Removed User ID from the changeable list
CREATE OR REPLACE PROCEDURE SP_UPD_PERSONA (
    in_id_persona IN NUMBER,
    in_rut IN VARCHAR2,
    in_nombre IN VARCHAR2,
    in_apellidos IN VARCHAR2,
    in_telefono IN VARCHAR2,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE personas SET rut=in_rut,nombre=in_nombre,apellidos=in_apellidos,telefono=in_telefono WHERE id_persona = in_id_persona;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_PERSONA (
    in_id_persona IN NUMBER,
    out_estado OUT NUMBER) AS

BEGIN
    out_estado := 0;
    DELETE FROM personas WHERE id_persona = in_id_persona;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;

-- INVENTARIOS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_INVENTARIO (
    in_descripcion IN VARCHAR2,
    in_id_departamento IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO inventarios (descripcion,DEPARTMENTOS_id_departmento) VALUES (in_descripcion,in_id_departamento) RETURNING id_inventario INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_INVENTARIO (
    in_inventario_id IN VARCHAR2,
    out_inventario OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_inventario FOR
        SELECT * FROM inventarios WHERE id_inventario = in_inventario_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_INVENTARIO (
    in_id_inventario IN INTEGER,
    in_descripcion IN VARCHAR2,
    in_id_departamento IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE inventarios SET descripcion=in_descripcion,DEPARTMENTOS_id_departmento=in_id_departamento WHERE id_inventario = in_id_inventario;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_INVENTARIO (
    in_id_inventario IN NUMBER,
    out_estado OUT NUMBER) AS

BEGIN
    out_estado := 0;
    DELETE FROM inventarios WHERE id_inventario = in_id_inventario;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;

-- RESERVAS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_RESERVA (
    in_fecha_entrada IN DATE,
    in_fecha_salida IN DATE,
    in_id_departamento IN INTEGER,
    in_Personas_id_persona IN INTEGER,
    in_Estadias_id_estadia IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO Reservas (fecha_entrada,fecha_salida,DEPARTMENTOS_id_departmento,Personas_id_persona,Estadias_id_estadia) VALUES (in_fecha_entrada,in_fecha_salida,in_id_departamento,in_Personas_id_persona,in_Estadias_id_estadia) RETURNING id_reserva INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_RESERVA (
    in_reserva_id IN VARCHAR2,
    out_reserva OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_reserva FOR
        SELECT * FROM Reservas WHERE id_reserva = in_reserva_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_RESERVA (
    in_id_reserva IN INTEGER,
    in_fecha_entrada IN DATE,
    in_fecha_salida IN DATE,
    in_id_departamento IN INTEGER,
    in_Personas_id_persona IN INTEGER,
    in_Estadias_id_estadia IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE Reservas SET fecha_entrada=in_fecha_entrada,fecha_salida=in_fecha_salida,DEPARTMENTOS_id_departmento=in_id_departamento,Personas_id_persona=in_Personas_id_persona,Estadias_id_estadia=in_Estadias_id_estadia WHERE id_reserva = in_id_reserva;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_RESERVA (
    in_id_reserva IN NUMBER,
    out_estado OUT NUMBER) AS

BEGIN
    out_estado := 0;
    DELETE FROM Reservas WHERE id_reserva = in_id_reserva;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;

-- ESTADIAS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_ESTADIA (
    in_id_checkin IN INTEGER,
    in_id_checkout IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO Estadias (checkin_id_checkin,checkout_id_checkout) VALUES (in_id_checkin,in_id_checkout) RETURNING id_estadia INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_ESTADIA (
    in_estadia_id IN VARCHAR2,
    out_resultado OUT NUMBER,
    out_estadia OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_estadia FOR
        SELECT * FROM Estadias WHERE id_estadia = in_estadia_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_ESTADIA (
    in_id_estadia IN INTEGER,
    in_id_checkin IN INTEGER,
    in_id_checkout IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE Estadias SET checkin_id_checkin=in_id_checkin,checkout_id_checkout=in_id_checkout WHERE id_estadia = in_id_estadia;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_ESTADIA (
    in_id_estadia IN NUMBER,
    out_estado OUT NUMBER) AS

BEGIN
    out_estado := 0;
    DELETE FROM Estadias WHERE id_estadia = in_id_estadia;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;

-- ACOMPAÑANTE
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_ACOMPANANTE (
    in_rut IN VARCHAR2,
    in_nombre IN VARCHAR2,
    in_apellido_pat IN VARCHAR2,
    in_apellido_mat IN VARCHAR2,
    in_Reservas_id_reserva IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO acompanantes (rut,nombre,apellido_pat,apellido_mat,Reservas_id_reserva) VALUES (in_rut,in_nombre,in_apellido_pat,in_apellido_mat,in_Reservas_id_reserva) RETURNING id_acompanante INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_ACOMPANANTE (
    in_acompanante_id IN INTEGER,
    out_acompanante OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_acompanante FOR
        SELECT * FROM acompanantes WHERE id_acompanante = in_acompanante_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_ACOMPANANTE (
    in_id_acompanante IN INTEGER,
    in_rut IN VARCHAR2,
    in_nombre IN VARCHAR2,
    in_apellido_pat IN VARCHAR2,
    in_apellido_mat IN VARCHAR2,
    in_Reservas_id_reserva IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE acompanantes SET rut=in_rut,nombre=in_nombre,apellido_pat=in_apellido_pat,apellido_mat=in_apellido_mat,Reservas_id_reserva=in_Reservas_id_reserva WHERE id_acompanante = in_id_acompanante;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_ACOMPANANTE (
    in_id_acompanante IN NUMBER,
    out_estado OUT NUMBER) AS

BEGIN
    out_estado := 0;
    DELETE FROM acompanantes WHERE id_acompanante = in_id_acompanante;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;

-- Checkin
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_CHECKIN (
    in_fecha IN DATE,
    in_pago IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO Checkin (fecha,pago) VALUES (in_fecha,in_pago) RETURNING id_checkin INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_CHECKIN (
    in_checkin_id IN INTEGER,
    out_checkin OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_checkin FOR
        SELECT * FROM Checkin WHERE id_checkin = in_checkin_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_CHECKIN (
    in_id_checkin IN INTEGER,
    in_fecha IN DATE,
    in_pago IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE Checkin SET fecha=in_fecha,pago=in_pago WHERE id_checkin = in_id_checkin;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_CHECKIN (
    in_id_checkin IN NUMBER,
    out_estado OUT NUMBER) AS
BEGIN
    out_estado := 0;
    DELETE FROM Checkin WHERE id_checkin = in_id_checkin;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;


-- CheckOut
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_CHECKOUT (
    in_fecha IN DATE,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO Checkout (fecha) VALUES (in_fecha) RETURNING id_checkout INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_CHECKOUT (
    in_checkout_id IN INTEGER,
    out_checkout OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_checkout FOR
        SELECT * FROM Checkout WHERE id_checkout = in_checkout_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_CHECKOUT (
    in_id_checkout IN INTEGER,
    in_fecha IN DATE,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE Checkout SET fecha=in_fecha WHERE id_checkout = in_id_checkout;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_CHECKOUT (
    in_id_checkout IN NUMBER,
    out_estado OUT NUMBER) AS

BEGIN
    out_estado := 0;
    DELETE FROM Checkout WHERE id_checkout = in_id_checkout;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;


-- multas
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_MULTAS (
    in_descripcion IN VARCHAR2,
    in_monto_multa IN INTEGER,
    in_idCheckOut IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO multas (descripcion,monto_multa,CheckOut_id_checkout) VALUES (in_descripcion,in_monto_multa,in_idCheckOut) RETURNING id_multas INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_MULTAS (
    in_multa_id IN INTEGER,
    out_multa OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_multa FOR
        SELECT * FROM multas WHERE id_multas = in_multa_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_MULTAS (
    in_id_multas IN INTEGER,
    in_descripcion IN VARCHAR2,
    in_monto_multa IN INTEGER,
    in_idCheckOut IN INTEGER,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE multas SET descripcion=in_descripcion,monto_multa=in_monto_multa,CheckOut_id_checkout=in_idCheckOut WHERE id_multas = in_id_multas;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_MULTAS (
    in_id_multas IN NUMBER,
    out_estado OUT NUMBER) AS

BEGIN
    out_estado := 0;
    DELETE FROM multas WHERE id_multas = in_id_multas;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;

-- mantenciones
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_MANTENCIONES (
    in_fecha_inicio IN DATE,
    in_costo IN INTEGER,
    in_descripcion IN VARCHAR2,
    in_idDepartamento IN INTEGER,
    in_fecha_termino IN DATE,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    INSERT INTO Mantenciones (fecha_inicio,costo,descripcion,DEPARTMENTOS_id_departmento,fecha_termino) VALUES (in_fecha_inicio,in_costo,in_descripcion,in_idDepartamento,in_fecha_termino) RETURNING id_mantencion INTO out_resultado;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_MANTENCIONES (
    in_mantencion_id IN INTEGER,
    out_mantencion OUT SYS_REFCURSOR
) AS BEGIN
    OPEN out_mantencion FOR
        SELECT * FROM Mantenciones WHERE id_mantencion = in_mantencion_id;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_MANTENCIONES (
    in_id_mantencion IN INTEGER,
    in_fecha_inicio IN DATE,
    in_costo IN INTEGER,
    in_descripcion IN VARCHAR2,
    in_idDepartamento IN INTEGER,
    in_fecha_termino IN DATE,
    out_resultado OUT NUMBER)
AS BEGIN
    out_resultado := 0;
    UPDATE Mantenciones SET fecha_inicio=in_fecha_inicio, costo=in_costo, descripcion=in_descripcion,DEPARTMENTOS_id_departmento=in_idDepartamento,fecha_termino=in_fecha_termino WHERE id_mantencion = in_id_mantencion;
    EXCEPTION
        WHEN OTHERS THEN
            out_resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_MANTENCIONES (
    in_id_mantencion IN NUMBER,
    out_estado OUT NUMBER) AS

BEGIN
    out_estado := 0;
    DELETE FROM Mantenciones WHERE id_mantencion = in_id_mantencion;
    EXCEPTION
        WHEN OTHERS THEN
            out_estado := -1;
END;