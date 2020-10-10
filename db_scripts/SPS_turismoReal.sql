--USUARIO
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


--DEPARTAMENTOS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_DEPARTAMENTO (
    nombre IN VARCHAR2,
    direccion IN VARCHAR2,
    region IN VARCHAR2,
    ciudad IN VARCHAR2,
	precio IN INTEGER,
	disponibilidad IN BOOLEAN,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO departamentos (nombre,direccion,region,ciudad,precio,disponibilidad) VALUES (nombre,direccion,region,ciudad,precio,disponibilidad) RETURNING id_departamento INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_DEPARTAMENTO (
    in_departamento_id IN VARCHAR2,
    resultado OUT NUMBER,
    out_departamento OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_departamento FOR 
            SELECT * FROM departamentos WHERE id_departamento = in_departamento_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_DEPARTAMENTO (
    id_departamento IN INTEGER,
    nombre IN VARCHAR2,
    direccion IN VARCHAR2,
    region IN VARCHAR2,
    ciudad IN VARCHAR2,
	precio IN INTEGER,
	disponibilidad IN BOOLEAN,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE departamentos SET nombre=nombre,direccion=direccion,region=region,ciudad=ciudad,precio=precio,disponibilidad=disponibilidad WHERE id_departamento = id_departamento; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_DEPARTAMENTO (
    id_departamento IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM departamentos WHERE id_departamento = id_departamento;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;

--SERVICIOS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_SERVICIO (
    descripcion IN VARCHAR2,
    tipos_servicio_id_tipo IN INTEGER,
	costo_operacion IN INTEGER,
    valor_cliente IN INTEGER,
    departamento_id_departamento IN INTEGER,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO servicios (descripcion, tipos_servicio_id_tipo, costo_operacion, valor_cliente, departamento_id_departamento) VALUES (descripcion, tipos_servicio_id_tipo, costo_operacion, valor_cliente, departamento_id_departamento) RETURNING id_servicios INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_SERVICIO (
    in_servicio_id IN VARCHAR2,
    resultado OUT NUMBER,
    out_servicio OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_servicio FOR 
            SELECT * FROM servicios WHERE id_servicios = in_servicio_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_SERVICIO (
    id_servicios IN INTEGER,
    descripcion IN VARCHAR2,
    tipos_servicio_id_tipo IN INTEGER,
	costo_operacion IN INTEGER,
    valor_cliente IN INTEGER,
    departamento_id_departamento IN INTEGER,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE servicios SET descripcion=descripcion,tipos_servicio_id_tipo=tipos_servicio_id_tipo,costo_operacion=costo_operacion,valor_cliente=valor_cliente,departamento_id_departamento=departamento_id_departamento WHERE id_servicios = id_servicios; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_SERVICIO (
    id_servicios IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM servicios WHERE id_servicios = id_servicios;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;

--TIPOS SERVICIOS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_TIPO_SERVICIO (
    descripcion IN VARCHAR2,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO tipos_servicios (descripcion) VALUES (descripcion) RETURNING id_tipo INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_TIPO_SERVICIO (
    in_tipo_servicio_id IN VARCHAR2,
    resultado OUT NUMBER,
    out_tipo_servicio OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_tipo_servicio FOR 
            SELECT * FROM tipos_servicios WHERE id_tipo = in_tipo_servicio_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_TIPO_SERVICIO (
    id_tipo IN INTEGER,
    descripcion IN VARCHAR2,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE tipos_servicios SET descripcion=descripcion WHERE id_tipo = id_tipo; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_TIPO_SERVICIO (
    id_tipo IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM tipos_servicios WHERE id_tipo = id_tipo;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;

-- PERSONAS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_PERSONA (
    rut IN VARCHAR2,
	nombre IN VARCHAR2,
	apellidos IN VARCHAR2,
	telefono IN VARCHAR2,
	Users_user_id IN INTEGER,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO personas (rut,nombre,apellidos,telefono,Users_user_id) VALUES (rut,nombre,apellidos,telefono,Users_user_id) RETURNING id_persona INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_PERSONA (
    in_persona_id IN VARCHAR2,
    resultado OUT NUMBER,
    out_persona OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_persona FOR 
            SELECT * FROM personas WHERE id_persona = in_persona_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_PERSONA (
    id_persona IN INTEGER,
    descripcion IN VARCHAR2,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE personas SET rut=rut,nombre=nombre,apellidos=apellidos,telefono=telefono,Users_user_id=Users_user_id WHERE id_persona = id_persona; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_PERSONA (
    id_persona IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM personas WHERE id_persona = id_persona;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;

-- INVENTARIOS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_INVENTARIO (
    descripcion IN VARCHAR2,
	Departamentos_id_departamento IN INTEGER,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO inventarios (descripcion,Departamentos_id_departamento) VALUES (descripcion,Departamentos_id_departamento) RETURNING id_inventario INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_INVENTARIO (
    in_inventario_id IN VARCHAR2,
    resultado OUT NUMBER,
    out_inventario OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_inventario FOR 
            SELECT * FROM inventarios WHERE id_inventario = in_inventario_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_INVENTARIO (
    id_inventario IN INTEGER,
    descripcion IN VARCHAR2,
	Departamentos_id_departamento IN INTEGER,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE inventarios SET descripcion=descripcion,Departamentos_id_departamento=Departamentos_id_departamento WHERE id_inventario = id_inventario; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_INVENTARIO (
    id_inventario IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM inventarios WHERE id_inventario = id_inventario;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;

-- RESERVAS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_RESERVA (
    fecha_entrada IN DATETIME,
	fecha_salida IN DATETIME,
	Departamentos_id_departamento IN INTEGER,
	Personas_id_persona IN INTEGER,
	Estadias_id_estadia IN INTEGER,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO Reservas (fecha_entrada,fecha_salida,Departamentos_id_departamento,Personas_id_persona,Estadias_id_estadia) VALUES (fecha_entrada,fecha_salida,Departamentos_id_departamento,Personas_id_persona,Estadias_id_estadia) RETURNING id_reserva INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_RESERVA (
    in_reserva_id IN VARCHAR2,
    resultado OUT NUMBER,
    out_reserva OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_reserva FOR 
            SELECT * FROM Reservas WHERE id_reserva = in_reserva_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_RESERVA (
    id_reserva IN INTEGER,
    fecha_entrada IN DATETIME,
	fecha_salida IN DATETIME,
	Departamentos_id_departamento IN INTEGER,
	Personas_id_persona IN INTEGER,
	Estadias_id_estadia IN INTEGER,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE Reservas SET fecha_entrada=fecha_entrada,fecha_salida=fecha_salida,Departamentos_id_departamento=Departamentos_id_departamento,Personas_id_persona=Personas_id_persona,Estadias_id_estadia=Estadias_id_estadia WHERE id_reserva = id_reserva; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_RESERVA (
    id_reserva IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM Reservas WHERE id_reserva = id_reserva;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;

-- ESTADIAS
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_ESTADIA (
    id_checkin IN INTEGER,
	id_checkout IN INTEGER,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO Estadia (id_checkin,id_checkout) VALUES (id_checkin,id_checkout) RETURNING id_estadia INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_ESTADIA (
    in_estadia_id IN VARCHAR2,
    resultado OUT NUMBER,
    out_estadia OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_estadia FOR 
            SELECT * FROM Estadia WHERE id_estadia = in_estadia_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_ESTADIA (
    id_estadia IN INTEGER,
    id_checkin IN INTEGER,
	id_checkout IN INTEGER,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE Estadia SET id_checkin=id_checkin,id_checkout=id_checkout WHERE id_estadia = id_estadia; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_ESTADIA (
    id_estadia IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM Estadia WHERE id_estadia = id_estadia;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;

-- ACOMPAÑANTE
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_ACOMPANANTE (
	rut IN VARCHAR2,
	nombre IN VARCHAR2,
	apellido_pat IN VARCHAR2,
	apellido_mat IN VARCHAR2,
	Reservas_id_reserva IN INTEGER,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO acompanante (rut,nombre,apellido_pat,apellido_mat,Reservas_id_reserva) VALUES (rut,nombre,apellido_pat,apellido_mat,Reservas_id_reserva) RETURNING id_acompanante INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_ACOMPANANTE (
    in_acompanante_id IN INTEGER,
    resultado OUT NUMBER,
    out_acompanante OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_acompanante FOR 
            SELECT * FROM acompanante WHERE id_acompanante = in_acompanante_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_ACOMPANANTE (
    id_acompanante IN INTEGER,
    rut IN VARCHAR2,
	nombre IN VARCHAR2,
	apellido_pat IN VARCHAR2,
	apellido_mat IN VARCHAR2,
	Reservas_id_reserva IN INTEGER,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE acompanante SET rut=rut,nombre=nombre,apellido_pat=apellido_pat,apellido_mat=apellido_mat,Reservas_id_reserva=Reservas_id_reserva WHERE id_acompanante = id_acompanante; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_ACOMPANANTE (
    id_acompanante IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM acompanante WHERE id_acompanante = id_acompanante;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;

-- Checkin
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_CHECKIN (
	fecha IN DATETIME,
	pago IN INTEGER,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO Checkin (fecha,pago) VALUES (fecha,pago) RETURNING id_checkin INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_CHECKIN (
    in_checkin_id IN INTEGER,
    resultado OUT NUMBER,
    out_checkin OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_checkin FOR 
            SELECT * FROM Checkin WHERE id_checkin = in_checkin_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_CHECKIN (
    id_checkin IN INTEGER,
    fecha IN DATETIME,
	pago IN INTEGER,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE Checkin SET fecha=fecha,pago=pago WHERE id_checkin = id_checkin; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_CHECKIN (
    id_checkin IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM Checkin WHERE id_checkin = id_checkin;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;


-- CheckOut
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_CHECKOUT (
	fecha IN DATETIME,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO Checkout (fecha) VALUES (fecha) RETURNING id_checkout INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_CHECKOUT (
    in_checkout_id IN INTEGER,
    resultado OUT NUMBER,
    out_checkout OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_checkout FOR 
            SELECT * FROM Checkout WHERE id_checkout = in_checkout_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_CHECKOUT (
    id_checkout IN INTEGER,
    fecha IN DATETIME,	
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE Checkout SET fecha=fecha WHERE id_checkout = id_checkout; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_CHECKOUT (
    id_checkout IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM Checkout WHERE id_checkout = id_checkout;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;


-- multas
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_MULTAS (
	descripcion IN VARCHAR2,
	monto_multa IN INTEGER,
	idCheckOut IN INTEGER,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO multas (descripcion,monto_multa,idCheckOut) VALUES (descripcion,monto_multa,idCheckOut) RETURNING id_multas INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_MULTAS (
    in_multa_id IN INTEGER,
    resultado OUT NUMBER,
    out_multa OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_multa FOR 
            SELECT * FROM multas WHERE id_multas = in_multa_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_MULTAS (
    id_multas IN INTEGER,
    descripcion IN VARCHAR2,
	monto_multa IN INTEGER,
	idCheckOut IN INTEGER,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE multas SET descripcion=descripcion,monto_multa=monto_multa,idCheckOut=idCheckOut WHERE id_multas = id_multas; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_MULTAS (
    id_multas IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM multas WHERE id_multas = id_multas;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;

-- mantenciones
-- CREATE

CREATE OR REPLACE PROCEDURE SP_CREAR_MANTENCIONES (
	idDepartamento IN INTEGER,
	Descripción IN VARCHAR2,
	fecha IN DATETIME,
	costo IN INTEGER,
    resultado OUT NUMBER)
AS BEGIN
    resultado := 0;
    INSERT INTO Mantenciones (idDepartamento,Descripcion,fecha,costo) VALUES (idDepartamento,Descripcion,fecha,costo) RETURNING id_mantencion INTO resultado;
    EXCEPTION
        WHEN OTHERS THEN
            resultado := -1;
END;

-- READ

CREATE OR REPLACE PROCEDURE SP_GET_MANTENCIONES (
    in_mantencion_id IN INTEGER,
    resultado OUT NUMBER,
    out_mantencion OUT SYS_REFCURSOR
) AS BEGIN
        resultado := 0;
        OPEN out_mantencion FOR 
            SELECT * FROM Mantenciones WHERE id_mantencion = in_mantencion_id;
        EXCEPTION WHEN OTHERS THEN
            resultado := -1;
END;

-- UPDATE

CREATE OR REPLACE PROCEDURE SP_UPD_MANTENCIONES (
    id_mantencion IN INTEGER,
    idDepartamento IN INTEGER,
	Descripcion IN VARCHAR2,
	fecha IN DATETIME,
	costo IN INTEGER,
    resultado OUT NUMBER) 
AS BEGIN
    resultado := 0;
    UPDATE Mantenciones SET idDepartamento=idDepartamento,Descripcion=Descripcion,fecha=fecha,costo=costo WHERE id_mantencion = id_mantencion; 
    EXCEPTION
        WHEN OTHERS THEN
            resultado :=-1;
END;

-- DELETE

CREATE OR REPLACE PROCEDURE SP_DEL_MANTENCIONES (
    id_mantencion IN NUMBER,
    estado OUT NUMBER) AS

BEGIN
    estado := 0;
    DELETE FROM Mantenciones WHERE id_mantencion = id_mantencion;
    EXCEPTION
        WHEN OTHERS THEN
            estado := -1;
END;