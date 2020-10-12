package com.arriendosreal.webapp.entities;

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Reservas generated by hbm2java
 */
@Entity
@Table(name = "RESERVAS", uniqueConstraints = @UniqueConstraint(columnNames = "ESTADIAS_ID_ESTADIA"))
public class Reservas implements java.io.Serializable {

    private BigDecimal idReserva;
    private Departmentos departmentos;
    private Estadias estadias;
    private Personas personas;
    private Date fechaEntrada;
    private Date fechaSalida;
    private Set<Acompanantes> acompananteses = new HashSet<Acompanantes>(0);

    public Reservas() {
    }

    public Reservas(BigDecimal idReserva, Departmentos departmentos, Estadias estadias, Personas personas) {
        this.idReserva = idReserva;
        this.departmentos = departmentos;
        this.estadias = estadias;
        this.personas = personas;
    }

    public Reservas(BigDecimal idReserva, Departmentos departmentos, Estadias estadias, Personas personas,
            Date fechaEntrada, Date fechaSalida, Set<Acompanantes> acompananteses) {
        this.idReserva = idReserva;
        this.departmentos = departmentos;
        this.estadias = estadias;
        this.personas = personas;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.acompananteses = acompananteses;
    }

    @Id

    @Column(name = "ID_RESERVA", unique = true, nullable = false, precision = 22, scale = 0)
    public BigDecimal getIdReserva() {
        return this.idReserva;
    }

    public void setIdReserva(BigDecimal idReserva) {
        this.idReserva = idReserva;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENTOS_ID_DEPARTMENTO", nullable = false)
    public Departmentos getDepartmentos() {
        return this.departmentos;
    }

    public void setDepartmentos(Departmentos departmentos) {
        this.departmentos = departmentos;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADIAS_ID_ESTADIA", unique = true, nullable = false)
    public Estadias getEstadias() {
        return this.estadias;
    }

    public void setEstadias(Estadias estadias) {
        this.estadias = estadias;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSONAS_ID_PERSONA", nullable = false)
    public Personas getPersonas() {
        return this.personas;
    }

    public void setPersonas(Personas personas) {
        this.personas = personas;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_ENTRADA", length = 7)
    public Date getFechaEntrada() {
        return this.fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_SALIDA", length = 7)
    public Date getFechaSalida() {
        return this.fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservas")
    public Set<Acompanantes> getAcompananteses() {
        return this.acompananteses;
    }

    public void setAcompananteses(Set<Acompanantes> acompananteses) {
        this.acompananteses = acompananteses;
    }

}
