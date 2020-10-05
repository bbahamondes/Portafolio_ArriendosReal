package com.arriendosreal.webapp.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Personas generated by hbm2java
 */
@Entity
@Table(name = "PERSONAS", uniqueConstraints = @UniqueConstraint(columnNames = "USERS_USER_ID"))
public class Personas implements java.io.Serializable {

	private BigDecimal idPersona;
	private Users users;
	private String rut;
	private String nombre;
	private String apellidos;
	private String telefono;
	private Set<Reservas> reservases = new HashSet<Reservas>(0);

	public Personas() {
	}

	public Personas(BigDecimal idPersona, Users users, String rut) {
		this.idPersona = idPersona;
		this.users = users;
		this.rut = rut;
	}

	public Personas(BigDecimal idPersona, Users users, String rut, String nombre, String apellidos, String telefono,
			Set<Reservas> reservases) {
		this.idPersona = idPersona;
		this.users = users;
		this.rut = rut;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.reservases = reservases;
	}

	@Id

	@Column(name = "ID_PERSONA", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(BigDecimal idPersona) {
		this.idPersona = idPersona;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERS_USER_ID", unique = true, nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "RUT", nullable = false, length = 50)
	public String getRut() {
		return this.rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	@Column(name = "NOMBRE", length = 50)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "APELLIDOS", length = 50)
	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Column(name = "TELEFONO", length = 50)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "personas")
	public Set<Reservas> getReservases() {
		return this.reservases;
	}

	public void setReservases(Set<Reservas> reservases) {
		this.reservases = reservases;
	}

}