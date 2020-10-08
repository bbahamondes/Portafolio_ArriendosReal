-- Generado por Oracle SQL Developer Data Modeler 20.2.0.167.1538
--   en:        2020-09-26 20:12:52 CLST
--   sitio:      Oracle Database 12cR2
--   tipo:      Oracle Database 12cR2



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE acompanantes (
    id_acompanante       INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    rut                  VARCHAR2(50),
    nombre               VARCHAR2(50),
    apellido_pat         VARCHAR2(50),
    apellido_mat         VARCHAR2(50),
    reservas_id_reserva  INTEGER NOT NULL
);

ALTER TABLE acompanantes ADD CONSTRAINT acompanantes_pk PRIMARY KEY ( id_acompanante );

CREATE TABLE checkin (
    id_checkin  INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    fecha       DATE,
    pago        INTEGER
);

ALTER TABLE checkin ADD CONSTRAINT checkin_pk PRIMARY KEY ( id_checkin );

CREATE TABLE checkout (
    id_checkout  INTEGER NOT NULL,
    fecha        DATE
);

ALTER TABLE checkout ADD CONSTRAINT checkout_pk PRIMARY KEY ( id_checkout );

CREATE TABLE departmentos (
    id_departmento  INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    nombre          VARCHAR2(50) NOT NULL,
    direccion       VARCHAR2(100),
    region          VARCHAR2(50),
    ciudad          VARCHAR2(50),
    precio          INTEGER,
    disponibilidad  NUMBER
);

ALTER TABLE departmentos ADD CONSTRAINT departmentos_pk PRIMARY KEY ( id_departmento );

CREATE TABLE estadias (
    id_estadia            INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    checkin_id_checkin    INTEGER NOT NULL,
    checkout_id_checkout  INTEGER NOT NULL
);

CREATE UNIQUE INDEX estadias__idx ON
    estadias (
        checkin_id_checkin
    ASC );

CREATE UNIQUE INDEX estadias__idxv1 ON
    estadias (
        checkout_id_checkout
    ASC );

ALTER TABLE estadias ADD CONSTRAINT estadias_pk PRIMARY KEY ( id_estadia );

CREATE TABLE inventarios (
    id_inventario                INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    descripcion                  VARCHAR2(50),
    departmentos_id_departmento  INTEGER NOT NULL
);

ALTER TABLE inventarios ADD CONSTRAINT inventarios_pk PRIMARY KEY ( id_inventario );

CREATE TABLE mantenciones (
    id_mantencion                INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    fecha_inicio                 DATE,
    costo                        INTEGER,
    descripcion                  VARCHAR2(250),
    departmentos_id_departmento  INTEGER NOT NULL,
    fecha_termino                DATE
);

ALTER TABLE mantenciones ADD CONSTRAINT mantenciones_pk PRIMARY KEY ( id_mantencion );

CREATE TABLE multas (
    id_multas             INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    checkout_id_checkout  INTEGER NOT NULL,
    monto_multa           INTEGER,
    descripcion           VARCHAR2(250)
);

ALTER TABLE multas ADD CONSTRAINT multas_pk PRIMARY KEY ( id_multas );

CREATE TABLE personas (
    id_persona     INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    rut            VARCHAR2(50) NOT NULL,
    nombre         VARCHAR2(50),
    apellidos      VARCHAR2(50),
    telefono       VARCHAR2(50),
    users_user_id  INTEGER NOT NULL
);

CREATE UNIQUE INDEX personas__idx ON
    personas (
        users_user_id
    ASC );

ALTER TABLE personas ADD CONSTRAINT personas_pk PRIMARY KEY ( id_persona );

CREATE TABLE profiles (
    profile_id    INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    profile_name  VARCHAR2(50)
);

ALTER TABLE profiles ADD CONSTRAINT profiles_pk PRIMARY KEY ( profile_id );

CREATE TABLE reservas (
    id_reserva                   INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    fecha_entrada                DATE,
    fecha_salida                 DATE,
    departmentos_id_departmento  INTEGER NOT NULL,
    personas_id_persona          INTEGER NOT NULL,
    estadias_id_estadia          INTEGER NOT NULL
);

CREATE UNIQUE INDEX reservas__idx ON
    reservas (
        estadias_id_estadia
    ASC );

ALTER TABLE reservas ADD CONSTRAINT reservas_pk PRIMARY KEY ( id_reserva );

CREATE TABLE resumen (
    id_resumen  INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    ingreso     INTEGER,
    egreso      INTEGER,
    fecha       DATE
);

ALTER TABLE resumen ADD CONSTRAINT resumen_pk PRIMARY KEY ( id_resumen );

CREATE TABLE servicios (
    id_servicio                  INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    departmentos_id_departmento  INTEGER NOT NULL,
    descripcion                  VARCHAR2(250),
    costo_operacion              INTEGER,
    valor_cliente                INTEGER,
    tipos_servicio_id_tipo       INTEGER NOT NULL
);

CREATE UNIQUE INDEX servicios__idx ON
    servicios (
        tipos_servicio_id_tipo
    ASC );

ALTER TABLE servicios ADD CONSTRAINT servicios_pk PRIMARY KEY ( id_servicio );

CREATE TABLE tipos_servicio (
    id_tipo      INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    descripcion  VARCHAR2(250)
);

ALTER TABLE tipos_servicio ADD CONSTRAINT tipos_servicio_pk PRIMARY KEY ( id_tipo );

CREATE TABLE users (
    user_id              INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    username             VARCHAR2(50) NOT NULL,
    email                VARCHAR2(50) NOT NULL,
    password             VARCHAR2(50) NOT NULL,
    profiles_profile_id  INTEGER NOT NULL
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( user_id );

ALTER TABLE acompanantes
    ADD CONSTRAINT acompanantes_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE estadias
    ADD CONSTRAINT estadias_checkin_fk FOREIGN KEY ( checkin_id_checkin )
        REFERENCES checkin ( id_checkin );

ALTER TABLE estadias
    ADD CONSTRAINT estadias_checkout_fk FOREIGN KEY ( checkout_id_checkout )
        REFERENCES checkout ( id_checkout );

ALTER TABLE inventarios
    ADD CONSTRAINT inventarios_departmentos_fk FOREIGN KEY ( departmentos_id_departmento )
        REFERENCES departmentos ( id_departmento );

ALTER TABLE mantenciones
    ADD CONSTRAINT mantenciones_departmentos_fk FOREIGN KEY ( departmentos_id_departmento )
        REFERENCES departmentos ( id_departmento );

ALTER TABLE multas
    ADD CONSTRAINT multas_checkout_fk FOREIGN KEY ( checkout_id_checkout )
        REFERENCES checkout ( id_checkout );

ALTER TABLE personas
    ADD CONSTRAINT personas_users_fk FOREIGN KEY ( users_user_id )
        REFERENCES users ( user_id );

ALTER TABLE reservas
    ADD CONSTRAINT reservas_departmentos_fk FOREIGN KEY ( departmentos_id_departmento )
        REFERENCES departmentos ( id_departmento );

ALTER TABLE reservas
    ADD CONSTRAINT reservas_estadias_fk FOREIGN KEY ( estadias_id_estadia )
        REFERENCES estadias ( id_estadia );

ALTER TABLE reservas
    ADD CONSTRAINT reservas_personas_fk FOREIGN KEY ( personas_id_persona )
        REFERENCES personas ( id_persona );

ALTER TABLE servicios
    ADD CONSTRAINT servicios_departmentos_fk FOREIGN KEY ( departmentos_id_departmento )
        REFERENCES departmentos ( id_departmento );

ALTER TABLE servicios
    ADD CONSTRAINT servicios_tipos_servicio_fk FOREIGN KEY ( tipos_servicio_id_tipo )
        REFERENCES tipos_servicio ( id_tipo );

ALTER TABLE users
    ADD CONSTRAINT users_profiles_fk FOREIGN KEY ( profiles_profile_id )
        REFERENCES profiles ( profile_id );



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            15
-- CREATE INDEX                             5
-- ALTER TABLE                             28
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
