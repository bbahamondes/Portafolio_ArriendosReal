--SPS DEPARTAMENTOS
CREATE OR REPLACE PROCEDURE SP_CREAR_DEPARTAMENTO (
  nombre IN VARCHAR2,
  direccion IN VARCHAR2,
  region IN VARCHAR2, 
  ciudad IN VARCHAR2, 
  precio IN NUMBER,
  disponibilidad IN NUMBER) AS
   
   BEGIN
      INSERT INTO departamentos (nombre, direccion, region, ciudad, precio, disponibilidad)
      VALUES (nombre, direccion, region, ciudad, precio, disponibilidad);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_DEPARTAMENTO 
   BEGIN
      SELECT * FROM departamentos;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_DEPARTAMENTO (@id_departamento NUMBER) AS
   
   BEGIN
      DELETE FROM departamentos WHERE id_departamento = @id_departamento;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_DEPARTAMENTO (id_departamento INTEGER, @nombre VARCHAR(50),
@direccion VARCHAR(100), @region VARCHAR(50), @ciudad VARCHAR(50), @precio INTEGER,
@disponibilidad BOOLEAN) AS
   
   BEGIN
     UPDATE departamentos SET nombre = @nombre, direccion = @direccion, region = @region, ciudad = @ciudad, precio = @precio, disponibilidad = @disponibilidad
      WHERE id_departamento = @id_departamento;
   END;
   
--SPS  SERVICIOS-----------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE SP_CREAR_SERVICIOS (@descripcion VARCHAR(50),@tipos_servicio_id_tipo INTEGER, @costo_operacion INTEGER,
 @valor_cliente INTEGER, @departamento_id_departamento NUMBER) AS
   
   BEGIN
      INSERT INTO servicios (descripcion, tipos_servicio_id_tipo, costo_operacion, valor_cliente, departamento_id_departamento)
      VALUES (@descripcion, @tipos_servicio_id_tipo, @costo_operacion, @valor_cliente, @departamento_id_departamento);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_SERVICIOS
   BEGIN
      SELECT * FROM servicios;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_SERVICIOS (@id_servicios NUMBER) AS
   
   BEGIN
      DELETE FROM servicios WHERE id_servicios = @id_servicios;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_SERVICIOS (id_servicios NUMBER, @descripcion VARCHAR(50),@tipos_servicio_id_tipo INTEGER, @costo_operacion INTEGER,
 @valor_cliente INTEGER, @departamento_id_departamento NUMBER) AS
   
   BEGIN
     UPDATE servicios SET descripcion = @descripcion, tipos_servicio_id_tipo = @tipos_servicio_id_tipo, costo_operacion = @costo_operacion, valor_cliente = @valor_cliente, departamento_id_departamento = @departamento_id_departamento
      WHERE id_servicios = @id_servicios;
   END;
--SPS  tipos SERVICIOS-----------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE SP_CREAR_TIPOS_SERVICIOS (@descipcion VARCHAR(250)) AS
   
   BEGIN
      INSERT INTO tipos_servicios (descripcion)
      VALUES (@descripcion);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_TIPOS_SERVICIOS
   BEGIN
      SELECT * FROM tipos_servicios;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_TIPOS_SERVICIOS (@id_tipo NUMBER) AS
   
   BEGIN
      DELETE FROM tipos_servicios WHERE id_tipo = @id_tipo;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_TIPOS_SERVICIOS (@id_tipo NUMBER, @descripcion VARCHAR(250)) AS
   
   BEGIN
     UPDATE tipos_servicios SET descripcion = @descripcion WHERE id_tipo = @id_tipo;
   END;
----SPS  personas-----------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE SP_CREAR_PERSONA (@rut VARCHAR(50),@nombre VARCHAR(50),@apellidos VARCHAR(50),@telefono VARCHAR(50),@Users_user_id INTEGER) AS
   
   BEGIN
      INSERT INTO personas (rut,nombre,apellidos,telefono,Users_user_id)
      VALUES (@rut,@nombre,@apellidos,@telefono,@Users_user_id);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_PERSONA
   BEGIN
      SELECT * FROM personas;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_PERSONA (@id_persona NUMBER) AS
   
   BEGIN
      DELETE FROM personas WHERE id_persona = @id_persona;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_PERSONA (@id_persona NUMBER, @rut VARCHAR(50),@nombre VARCHAR(50),@apellidos VARCHAR(50),@telefono VARCHAR(50),@Users_user_id INTEGER) AS
   
   BEGIN
     UPDATE personas SET rut=@rut,nombre=@nombre,apellidos=@apellidos,telefono=@telefono,Users_user_id=@Users_user_id WHERE id_persona = @id_persona;
   END;
 ---SPS  inventarios-----------------------------------------------------------------------------------------
 CREATE OR REPLACE PROCEDURE SP_CREAR_INVENTARIO (@descripcion VARCHAR(50),@Departamentos_id_departamento INTEGER) AS
   
   BEGIN
      INSERT INTO inventarios (descripcion,Departamentos_id_departamento)
      VALUES (@descripcion,@Departamentos_id_departamento);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_INVENTARIO
   BEGIN
      SELECT * FROM inventarios;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_INVENTARIO (@id_inventario NUMBER) AS
   
   BEGIN
      DELETE FROM inventarios WHERE id_inventario = @id_inventario;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_INVENTARIO (@id_inventario NUMBER, @descripcion VARCHAR(50),@Departamentos_id_departamento INTEGER) AS
   
   BEGIN
     UPDATE inventarios SET descripcion=@descripcion,Departamentos_id_departamento=@Departamentos_id_departamento WHERE id_inventario = @id_inventario;
   END;
   
 --SPS Reservas-----------------------------------------------------------------------------------------
 CREATE OR REPLACE PROCEDURE SP_CREAR_RESERVA (@fecha_entrada DATETIME,@fecha_salida DATETIME,@Departamentos_id_departamento INTEGER,@Personas_id_persona INTEGER,@Estadias_id_estadia INTEGER) AS
   
   BEGIN
      INSERT INTO Reservas (fecha_entrada,fecha_salida,Departamentos_id_departamento,Personas_id_persona,Estadias_id_estadia)
      VALUES (@fecha_entrada,@fecha_salida,@Departamentos_id_departamento,@Personas_id_persona,@Estadias_id_estadia);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_reserva 
   BEGIN
      SELECT * FROM Reservas;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_RESERVA (@id_reserva NUMBER) AS
   
   BEGIN
      DELETE FROM Reservas WHERE id_reserva = @id_reserva;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_RESERVA (@id_reserva NUMBER, @fecha_entrada DATETIME,@fecha_salida DATETIME,@Departamentos_id_departamento INTEGER,@Personas_id_persona INTEGER,@Estadias_id_estadia INTEGER) AS
   
   BEGIN
     UPDATE Reservas SET fecha_entrada=@fecha_entrada,fecha_salida=@fecha_salida,Departamentos_id_departamento,@Departamentos_id_departamento,
	 Personas_id_persona = @Personas_id_persona, Estadias_id_estadia =@Estadias_id_estadia WHERE id_reserva = @id_reserva;
   END;
   
  --SPS Reservas-----------------------------------------------------------------------------------------
 CREATE OR REPLACE PROCEDURE SP_CREAR_ESTADIA (@id_checkin INTEGER,@id_checkout INTEGER) AS
   
   BEGIN
      INSERT INTO Estadia (id_checkin,id_checkout)
      VALUES (@id_checkin,@id_checkout);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_ESTADIA
   BEGIN
      SELECT * FROM Estadia;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_ESTADIA (@id_estadia NUMBER) AS
   
   BEGIN
      DELETE FROM Estadia WHERE id_estadia = @id_estadia;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_ESTADIA (@id_estadia INTEGER,@id_checkin INTEGER,@id_checkout INTEGER) AS
   
   BEGIN
     UPDATE Estadia SET id_checkin=@id_checkin,id_checkout=@id_checkout WHERE id_estadia = @id_estadia;
	 END;
	 
  --SPS Acompañante-----------------------------------------------------------------------------------------
 CREATE OR REPLACE PROCEDURE SP_CREAR_ACOMPANANTE (@rut VARCHAR(50),@nombre VARCHAR(50),@apellido_pat VARCHAR(50),@apellido_mat VARCHAR(50),@Reservas_id_reserva INTEGER) AS
   
   BEGIN
      INSERT INTO Acompanante (rut,nombre,apellido_pat,apellido_mat,Reservas_id_reserva)
      VALUES (@rut VARCHAR(50),@nombre VARCHAR(50),@apellido_pat VARCHAR(50),@apellido_mat VARCHAR(50),@Reservas_id_reserva INTEGER);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_ACOMPANANTE
   BEGIN
      SELECT * FROM Acompanante;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_ACOMPANANTE (@id_acompanante NUMBER) AS
   
   BEGIN
      DELETE FROM Acompanante WHERE id_acompanante = @id_acompanante;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_ACOMPANANTE (@id_acompanante INTEGER,@rut VARCHAR(50),@nombre VARCHAR(50),@apellido_pat VARCHAR(50),@apellido_mat VARCHAR(50),@Reservas_id_reserva INTEGER) AS
   
   BEGIN
     UPDATE Acompanante SET rut=@rut,nombre=@nombre,apellido_pat=@apellido_pat,apellido_mat=@apellido_mat,Reservas_id_reserva=@Reservas_id_reserva WHERE id_acompanante = @id_acompanante;
END;	 
	 
 --SPS Checkin-----------------------------------------------------------------------------------------
 CREATE OR REPLACE PROCEDURE SP_CREAR_CHECKIN (@fecha DATETIME,@pago INTEGER) AS
   
   BEGIN
      INSERT INTO Checkin (fecha,pago)
      VALUES (@fecha,@pago);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_CHECKIN
   BEGIN
      SELECT * FROM Checkin;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_CHECKIN (@id_checkin NUMBER) AS
   
   BEGIN
      DELETE FROM Checkin WHERE id_checkin = @id_checkin;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_CHECKIN (@id_checkin INTEGER,@fecha DATETIME,@pago INTEGER) AS
   
   BEGIN
     UPDATE Checkin SET fecha=@fecha,pago=@pago WHERE id_checkin = @id_checkin; 
END;

 --SPS CheckOUT-----------------------------------------------------------------------------------------
 CREATE OR REPLACE PROCEDURE SP_CREAR_CHECKOUT (@fecha DATETIME) AS
   
   BEGIN
      INSERT INTO Checkout (fecha)
      VALUES (@fecha);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_CHECKOUT
   BEGIN
      SELECT * FROM Checkout;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_CHECKOUT (@id_checkout NUMBER) AS
   
   BEGIN
      DELETE FROM Checkout WHERE id_checkout = @id_checkout;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_CHECKOUT (@id_checkout INTEGER,@fecha DATETIME) AS
   
   BEGIN
     UPDATE Checkout SET fecha=@fecha WHERE id_checkout = @id_checkout; 
END;

 --SPS Multas-----------------------------------------------------------------------------------------
 CREATE OR REPLACE PROCEDURE SP_CREAR_MULTAS (@descripcion VARCHAR(50),@monto_multa INTEGER,@idCheckOut INTEGER) AS
   
   BEGIN
      INSERT INTO Multas (descripcion,monto_multa,idCheckOut)
      VALUES (@descripcion,@monto_multa,@idCheckOut);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_MULTAS
   BEGIN
      SELECT * FROM Multas;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_MULTAS (@id_multas NUMBER) AS
   
   BEGIN
      DELETE FROM Multas WHERE id_multas = @id_multas;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_MULTAS (@id_multas INTEGER,@descripcion VARCHAR(50),@monto_multa INTEGER,@idCheckOut INTEGER) AS
   
   BEGIN
     UPDATE Multas SET descripcion=@descripcion,monto_multa=@monto_multa,idCheckOut=@idCheckOut WHERE id_multas = @id_multas; 
END;

 --SPS Mantenciones-----------------------------------------------------------------------------------------
 CREATE OR REPLACE PROCEDURE SP_CREAR_MANTENCIONES (@idDepartamento INTEGER,@Descripcion VARCHAR(50),@fecha_inicio DATETIME,@fecha_termino DATETIME,@costo INTEGER) AS
   
   BEGIN
      INSERT INTO Mantenciones (Departamentos_id_departamento, descripcion, fecha_inicio, fecha_termino, costo)
      VALUES (@idDepartamento,@Descripcion,@fecha_inicio,@fecha_termino,@costo);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_MANTENCIONES
   BEGIN
      SELECT * FROM Mantenciones;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_MANTENCIONES (@id_mantencion NUMBER) AS
   
   BEGIN
      DELETE FROM Mantenciones WHERE id_mantencion = @id_mantencion;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_MANTENCIONES (@id_mantencion INTEGER,@fecha_inicio DATETIME, @costo INTEGER, @descripcion VARCHAR2(250), @Departamentos_id_departamento INTEGER, @fecha_termino DATETIME) AS
   
   BEGIN
     UPDATE Mantenciones SET fecha_inicio=@fecha_inicio,costo=@costo,descripcion=@descripcion,Departamentos_id_departamento=@Departamentos_id_departamento,fecha_termino=@fecha_termino WHERE id_mantencion = @id_mantencion; 
END;

 --SPS Resumen-----------------------------------------------------------------------------------------
 /*CREATE OR REPLACE PROCEDURE SP_CREAR_RESUMEN (@ingreso INTEGER,@egreso INTEGER,@fecha DATETIME) AS
   
   BEGIN
      INSERT INTO Resumen (ingreso,egreso,fecha)
      VALUES (@ingreso,@egreso,@fecha);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_resumen
   BEGIN
      SELECT * FROM Resumen;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_RESUMEN (@id_resumen NUMBER) AS
   
   BEGIN
      DELETE FROM Resumen WHERE id_resumen = @id_resumen;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_RESUMEN (@id_resumen INTEGER,@idDepartamento INTEGER,@Descripción VARCHAR(50),@fecha DATETIME,@costo INTEGER) AS
   
   BEGIN
     UPDATE Resumen SET ingreso=@ingreso,egreso=@egreso,fecha=@fecha WHERE id_resumen = @id_resumen; 
END;*/

 --SPS Users-----------------------------------------------------------------------------------------
 CREATE OR REPLACE PROCEDURE SP_CREAR_USERS (@username VARCHAR2(50), @email VARCHAR2(50), @password VARCHAR2(50), @profile_id INTEGER) AS
   
   BEGIN
      INSERT INTO Users (username,email,password,Profile_profile_id)
      VALUES (@username,@email,@password,@profile_id);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_USERS
   BEGIN
      SELECT * FROM Users;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_USERS (@user_id NUMBER) AS
   
   BEGIN
      DELETE FROM Users WHERE user_id = @user_id;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_USERS (@user_id INTEGER,@username VARCHAR2(50), @email VARCHAR2(50), @password VARCHAR2(50), @profile_id INTEGER) AS
   
   BEGIN
     UPDATE Users SET username=@username,email=@email,password=@password,Profile_profile_id=@profile_id WHERE user_id = @user_id; 
END;

 --SPS Profiles-----------------------------------------------------------------------------------------
 CREATE OR REPLACE PROCEDURE SP_CREAR_PROFILES (@profile_name VARCHAR(50)) AS
   
   BEGIN
      INSERT INTO Profiles (profile_name)
      VALUES (@profile_name);
   END;
   
 CREATE OR REPLACE PROCEDURE SP_GET_PROFILES
   BEGIN
      SELECT * FROM Profiles;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_DEL_PROFILES (@profile_id NUMBER) AS
   
   BEGIN
      DELETE FROM Profiles WHERE profile_id = @profile_id;
   END;
   
 CREATE OR REPLACE PROCEDURE SP_UPD_PROFILES (@profile_id INTEGER,@profile_name VARCHAR(50)) AS
   
   BEGIN
     UPDATE Profiles SET profile_name=@profile_name WHERE profile_id = profile_id; 
END;