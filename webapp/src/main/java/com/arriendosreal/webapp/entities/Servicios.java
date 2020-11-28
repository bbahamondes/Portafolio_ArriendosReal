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
import javax.persistence.UniqueConstraint;

/**
 * Servicios generated by hbm2java
 */
@Entity
@Table(name = "SERVICIOS", uniqueConstraints = @UniqueConstraint(columnNames = "TIPOS_SERVICIO_ID_TIPO"))

@NamedStoredProcedureQuery(name = "Servicios.createServicio", procedureName = "SP_CREAR_SERVICIO", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_descripcion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_tipos_servicio_id_tipo", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_costo_operacion", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_valor_cliente", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_id_departamento", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_resultado", type = Integer.class)})

@NamedStoredProcedureQuery(name = "Servicios.updateServicio", procedureName = "SP_UPD_SERVICIO", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_id_servicios", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_descripcion", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_tipos_servicio_id_tipo", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_costo_operacion", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_valor_cliente", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_id_departamento", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_resultado", type = Integer.class)})

@NamedStoredProcedureQuery(name = "Servicios.deleteServicio", procedureName = "SP_DEL_SERVICIO", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_id_servicios", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_estado", type = Integer.class)})

public class Servicios implements java.io.Serializable {

    private BigDecimal idServicio;
    private Departmentos departmentos;
    
    @ManyToOne(targetEntity = TiposServicio.class)
    private TiposServicio tiposServicio;
    private String descripcion;
    private BigDecimal costoOperacion;
    private BigDecimal valorCliente;

    public Servicios() {
    }

    public Servicios(BigDecimal idServicio, Departmentos departmentos, TiposServicio tiposServicio) {
        this.idServicio = idServicio;
        this.departmentos = departmentos;
        this.tiposServicio = tiposServicio;
    }

    public Servicios(BigDecimal idServicio, Departmentos departmentos, TiposServicio tiposServicio, String descripcion,
            BigDecimal costoOperacion, BigDecimal valorCliente) {
        this.idServicio = idServicio;
        this.departmentos = departmentos;
        this.tiposServicio = tiposServicio;
        this.descripcion = descripcion;
        this.costoOperacion = costoOperacion;
        this.valorCliente = valorCliente;
    }

    @Id

    @Column(name = "ID_SERVICIO", unique = true, nullable = false, precision = 22, scale = 0)
    public BigDecimal getIdServicio() {
        return this.idServicio;
    }

    public void setIdServicio(BigDecimal idServicio) {
        this.idServicio = idServicio;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENTOS_ID_DEPARTMENTO", nullable = false)
    public Departmentos getDepartmentos() {
        return this.departmentos;
    }

    public void setDepartmentos(Departmentos departmentos) {
        this.departmentos = departmentos;
    }
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TiposServicio.class)
    @JoinColumn(name = "TIPOS_SERVICIO_ID_TIPO", unique = true, nullable = false)
    public TiposServicio getTiposServicio() {
        return this.tiposServicio;
    }

    public void setTiposServicio(TiposServicio tiposServicio) {
        this.tiposServicio = tiposServicio;
    }

    @Column(name = "DESCRIPCION", length = 250)
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "COSTO_OPERACION", precision = 22, scale = 0)
    public BigDecimal getCostoOperacion() {
        return this.costoOperacion;
    }

    public void setCostoOperacion(BigDecimal costoOperacion) {
        this.costoOperacion = costoOperacion;
    }

    @Column(name = "VALOR_CLIENTE", precision = 22, scale = 0)
    public BigDecimal getValorCliente() {
        return this.valorCliente;
    }

    public void setValorCliente(BigDecimal valorCliente) {
        this.valorCliente = valorCliente;
    }

}
