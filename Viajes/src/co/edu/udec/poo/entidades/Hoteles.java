/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.poo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JEIFER ALCALA
 */
@Entity
@Table(name = "hoteles", catalog = "mydb", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idHoteles"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hoteles.findAll", query = "SELECT h FROM Hoteles h"),
    @NamedQuery(name = "Hoteles.findByIdHoteles", query = "SELECT h FROM Hoteles h WHERE h.hotelesPK.idHoteles = :idHoteles"),
    @NamedQuery(name = "Hoteles.findByNombre", query = "SELECT h FROM Hoteles h WHERE h.nombre = :nombre"),
    @NamedQuery(name = "Hoteles.findByDireccion", query = "SELECT h FROM Hoteles h WHERE h.direccion = :direccion"),
    @NamedQuery(name = "Hoteles.findByNroTelefonico", query = "SELECT h FROM Hoteles h WHERE h.nroTelefonico = :nroTelefonico"),
    @NamedQuery(name = "Hoteles.findByPlazaDisponibleFechaDeRefencia", query = "SELECT h FROM Hoteles h WHERE h.hotelesPK.plazaDisponibleFechaDeRefencia = :plazaDisponibleFechaDeRefencia")})
public class Hoteles implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HotelesPK hotelesPK;
    @Column(name = "Nombre", length = 45)
    private String nombre;
    @Column(name = "Direccion", length = 45)
    private String direccion;
    @Column(name = "NroTelefonico", length = 45)
    private String nroTelefonico;
    @JoinColumn(name = "PlazaDisponible_FechaDeRefencia1", referencedColumnName = "FechaDeRefencia", nullable = false)
    @ManyToOne(optional = false)
    private Plazadisponible plazaDisponibleFechaDeRefencia1;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "hoteles")
    private Facturacliente facturacliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hoteles")
    private List<Regimenhospedaje> regimenhospedajeList;

    public Hoteles() {
    }

    public Hoteles(HotelesPK hotelesPK) {
        this.hotelesPK = hotelesPK;
    }

    public Hoteles(int idHoteles, Date plazaDisponibleFechaDeRefencia) {
        this.hotelesPK = new HotelesPK(idHoteles, plazaDisponibleFechaDeRefencia);
    }

    public HotelesPK getHotelesPK() {
        return hotelesPK;
    }

    public void setHotelesPK(HotelesPK hotelesPK) {
        this.hotelesPK = hotelesPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNroTelefonico() {
        return nroTelefonico;
    }

    public void setNroTelefonico(String nroTelefonico) {
        this.nroTelefonico = nroTelefonico;
    }

    public Plazadisponible getPlazaDisponibleFechaDeRefencia1() {
        return plazaDisponibleFechaDeRefencia1;
    }

    public void setPlazaDisponibleFechaDeRefencia1(Plazadisponible plazaDisponibleFechaDeRefencia1) {
        this.plazaDisponibleFechaDeRefencia1 = plazaDisponibleFechaDeRefencia1;
    }

    public Facturacliente getFacturacliente() {
        return facturacliente;
    }

    public void setFacturacliente(Facturacliente facturacliente) {
        this.facturacliente = facturacliente;
    }

    @XmlTransient
    public List<Regimenhospedaje> getRegimenhospedajeList() {
        return regimenhospedajeList;
    }

    public void setRegimenhospedajeList(List<Regimenhospedaje> regimenhospedajeList) {
        this.regimenhospedajeList = regimenhospedajeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hotelesPK != null ? hotelesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hoteles)) {
            return false;
        }
        Hoteles other = (Hoteles) object;
        if ((this.hotelesPK == null && other.hotelesPK != null) || (this.hotelesPK != null && !this.hotelesPK.equals(other.hotelesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udec.poo.entidades.Hoteles[ hotelesPK=" + hotelesPK + " ]";
    }
    
}
