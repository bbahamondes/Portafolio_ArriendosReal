package com.arriendosreal.webapp.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Multas generated by hbm2java
 */
@Entity
@Table(name = "MULTAS")
public class Multas implements java.io.Serializable {

	private BigDecimal idMultas;
	private Checkout checkout;
	private BigDecimal montoMulta;
	private String descripcion;

	public Multas() {
	}

	public Multas(BigDecimal idMultas, Checkout checkout) {
		this.idMultas = idMultas;
		this.checkout = checkout;
	}

	public Multas(BigDecimal idMultas, Checkout checkout, BigDecimal montoMulta, String descripcion) {
		this.idMultas = idMultas;
		this.checkout = checkout;
		this.montoMulta = montoMulta;
		this.descripcion = descripcion;
	}

	@Id

	@Column(name = "ID_MULTAS", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdMultas() {
		return this.idMultas;
	}

	public void setIdMultas(BigDecimal idMultas) {
		this.idMultas = idMultas;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHECKOUT_ID_CHECKOUT", nullable = false)
	public Checkout getCheckout() {
		return this.checkout;
	}

	public void setCheckout(Checkout checkout) {
		this.checkout = checkout;
	}

	@Column(name = "MONTO_MULTA", precision = 22, scale = 0)
	public BigDecimal getMontoMulta() {
		return this.montoMulta;
	}

	public void setMontoMulta(BigDecimal montoMulta) {
		this.montoMulta = montoMulta;
	}

	@Column(name = "DESCRIPCION", length = 250)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}