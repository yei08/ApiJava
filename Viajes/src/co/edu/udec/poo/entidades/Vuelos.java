/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.poo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JEIFER ALCALA
 */
@Entity
@Table(name = "vuelos", catalog = "mydb", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"NroVuelo"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vuelos.findAll", query = "SELECT v FROM Vuelos v"),
    @NamedQuery(name = "Vuelos.findByNroVuelo", query = "SELECT v FROM Vuelos v WHERE v.nroVuelo = :nroVuelo"),
    @NamedQuery(name = "Vuelos.findByHora", query = "SELECT v FROM Vuelos v WHERE v.hora = :hora"),
    @NamedQuery(name = "Vuelos.findByOrigen", query = "SELECT v FROM Vuelos v WHERE v.origen = :origen"),
    @NamedQuery(name = "Vuelos.findByDestino", query = "SELECT v FROM Vuelos v WHERE v.destino = :destino"),
    @NamedQuery(name = "Vuelos.findByPlazasDisponibles", query = "SELECT v FROM Vuelos v WHERE v.plazasDisponibles = :plazasDisponibles"),
    @NamedQuery(name = "Vuelos.findByPlazasTuristaDisponible", query = "SELECT v FROM Vuelos v WHERE v.plazasTuristaDisponible = :plazasTuristaDisponible"),
    @NamedQuery(name = "Vuelos.findByFecha", query = "SELECT v FROM Vuelos v WHERE v.fecha = :fecha")})
public class Vuelos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NroVuelo", nullable = false, length = 45)
    private String nroVuelo;
    @Column(name = "Hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Column(name = "Origen", length = 45)
    private String origen;
    @Column(name = "Destino", length = 45)
    private String destino;
    @Column(name = "PlazasDisponibles")
    private Integer plazasDisponibles;
    @Column(name = "PlazasTuristaDisponible")
    private Integer plazasTuristaDisponible;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "Sucursales_idSucursal", referencedColumnName = "idSucursal", nullable = false)
    @ManyToOne(optional = false)
    private Sucursales sucursalesidSucursal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vuelosNroVuelo")
    private List<Clientes> clientesList;

    public Vuelos() {
    }

    public Vuelos(String nroVuelo) {
        this.nroVuelo = nroVuelo;
    }

    public String getNroVuelo() {
        return nroVuelo;
    }

    public void setNroVuelo(String nroVuelo) {
        this.nroVuelo = nroVuelo;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getPlazasDisponibles() {
        return plazasDisponibles;
    }

    public void setPlazasDisponibles(Integer plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }

    public Integer getPlazasTuristaDisponible() {
        return plazasTuristaDisponible;
    }

    public void setPlazasTuristaDisponible(Integer plazasTuristaDisponible) {
        this.plazasTuristaDisponible = plazasTuristaDisponible;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Sucursales getSucursalesidSucursal() {
        return sucursalesidSucursal;
    }

    public void setSucursalesidSucursal(Sucursales sucursalesidSucursal) {
        this.sucursalesidSucursal = sucursalesidSucursal;
    }

    @XmlTransient
    public List<Clientes> getClientesList() {
        return clientesList;
    }

    public void setClientesList(List<Clientes> clientesList) {
        this.clientesList = clientesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nroVuelo != null ? nroVuelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vuelos)) {
            return false;
        }
        Vuelos other = (Vuelos) object;
        if ((this.nroVuelo == null && other.nroVuelo != null) || (this.nroVuelo != null && !this.nroVuelo.equals(other.nroVuelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udec.poo.entidades.Vuelos[ nroVuelo=" + nroVuelo + " ]";
    }
    
}
