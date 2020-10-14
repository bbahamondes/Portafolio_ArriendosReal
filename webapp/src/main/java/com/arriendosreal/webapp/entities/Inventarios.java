package com.arriendosreal.webapp.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

/**
 * Inventarios generated by hbm2java
 */
@Entity
@Table(name = "INVENTARIOS")

@NamedStoredProcedureQuery(name = "Inventarios.createInventario", procedureName = "SP_CREAR_INVENTARIO", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_descripcion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_Departamentos_id_departamento", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_resultado", type = Integer.class) })

@NamedStoredProcedureQuery(name = "Inventarios.updateInventario", procedureName = "SP_UPD_INVENTARIO", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_id_inventario", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_descripcion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_Departamentos_id_departamento", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_resultado", type = Integer.class) })

@NamedStoredProcedureQuery(name = "Inventarios.deleteInventario", procedureName = "SP_DEL_INVENTARIO", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_id_inventario", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_estado", type = Integer.class) })

public class Inventarios implements java.io.Serializable {

    private BigDecimal idInventario;
    private Departmentos departmentos;
    private String descripcion;

    public Inventarios() {
    }

    public Inventarios(BigDecimal idInventario, Departmentos departmentos) {
        this.idInventario = idInventario;
        this.departmentos = departmentos;
    }

    public Inventarios(BigDecimal idInventario, Departmentos departmentos, String descripcion) {
        this.idInventario = idInventario;
        this.departmentos = departmentos;
        this.descripcion = descripcion;
    }

    @Id

    @Column(name = "ID_INVENTARIO", unique = true, nullable = false, precision = 22, scale = 0)
    public BigDecimal getIdInventario() {
        return this.idInventario;
    }

    public void setIdInventario(BigDecimal idInventario) {
        this.idInventario = idInventario;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENTOS_ID_DEPARTMENTO", nullable = false)
    public Departmentos getDepartmentos() {
        return this.departmentos;
    }

    public void setDepartmentos(Departmentos departmentos) {
        this.departmentos = departmentos;
    }

    @Column(name = "DESCRIPCION", length = 50)
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
