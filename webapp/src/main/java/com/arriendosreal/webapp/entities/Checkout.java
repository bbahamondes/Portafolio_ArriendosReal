package com.arriendosreal.webapp.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Checkout generated by hbm2java
 */
@Entity
@Table(name = "CHECKOUT")
public class Checkout implements java.io.Serializable {

	private BigDecimal idCheckout;
	private Date fecha;
	private Set<Multas> multases = new HashSet<Multas>(0);
	private Set<Estadias> estadiases = new HashSet<Estadias>(0);

	public Checkout() {
	}

	public Checkout(BigDecimal idCheckout) {
		this.idCheckout = idCheckout;
	}

	public Checkout(BigDecimal idCheckout, Date fecha, Set<Multas> multases, Set<Estadias> estadiases) {
		this.idCheckout = idCheckout;
		this.fecha = fecha;
		this.multases = multases;
		this.estadiases = estadiases;
	}

	@Id

	@Column(name = "ID_CHECKOUT", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdCheckout() {
		return this.idCheckout;
	}

	public void setIdCheckout(BigDecimal idCheckout) {
		this.idCheckout = idCheckout;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA", length = 7)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "checkout")
	public Set<Multas> getMultases() {
		return this.multases;
	}

	public void setMultases(Set<Multas> multases) {
		this.multases = multases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "checkout")
	public Set<Estadias> getEstadiases() {
		return this.estadiases;
	}

	public void setEstadiases(Set<Estadias> estadiases) {
		this.estadiases = estadiases;
	}

}
