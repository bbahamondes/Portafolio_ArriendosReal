package com.arriendosreal.webapp.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mantenciones generated by hbm2java
 */
@Entity
@Table(name = "MANTENCIONES")
public class Mantenciones implements java.io.Serializable {

	private BigDecimal idMantencion;
	private Departmentos departmentos;
	private Date fechaInicio;
	private BigDecimal costo;
	private String descripcion;
	private Date fechaTermino;

	public Mantenciones() {
	}

	public Mantenciones(BigDecimal idMantencion, Departmentos departmentos) {
		this.idMantencion = idMantencion;
		this.departmentos = departmentos;
	}

	public Mantenciones(BigDecimal idMantencion, Departmentos departmentos, Date fechaInicio, BigDecimal costo,
			String descripcion, Date fechaTermino) {
		this.idMantencion = idMantencion;
		this.departmentos = departmentos;
		this.fechaInicio = fechaInicio;
		this.costo = costo;
		this.descripcion = descripcion;
		this.fechaTermino = fechaTermino;
	}

	@Id

	@Column(name = "ID_MANTENCION", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdMantencion() {
		return this.idMantencion;
	}

	public void setIdMantencion(BigDecimal idMantencion) {
		this.idMantencion = idMantencion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENTOS_ID_DEPARTMENTO", nullable = false)
	public Departmentos getDepartmentos() {
		return this.departmentos;
	}

	public void setDepartmentos(Departmentos departmentos) {
		this.departmentos = departmentos;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INICIO", length = 7)
	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Column(name = "COSTO", precision = 22, scale = 0)
	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	@Column(name = "DESCRIPCION", length = 250)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_TERMINO", length = 7)
	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

}
