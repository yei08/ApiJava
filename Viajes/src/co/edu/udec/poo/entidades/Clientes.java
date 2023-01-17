/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.poo.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JEIFER ALCALA
 */
@Entity
@Table(name = "clientes", catalog = "mydb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
    @NamedQuery(name = "Clientes.findByIdClientes", query = "SELECT c FROM Clientes c WHERE c.idClientes = :idClientes"),
    @NamedQuery(name = "Clientes.findByNombre", query = "SELECT c FROM Clientes c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Clientes.findByApellido", query = "SELECT c FROM Clientes c WHERE c.apellido = :apellido"),
    @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Clientes.findByNroContacto", query = "SELECT c FROM Clientes c WHERE c.nroContacto = :nroContacto")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idClientes", nullable = false)
    private Integer idClientes;
    @Column(name = "Nombre", length = 30)
    private String nombre;
    @Column(name = "Apellido", length = 45)
    private String apellido;
    @Column(name = "Direccion", length = 50)
    private String direccion;
    @Column(name = "NroContacto", length = 45)
    private String nroContacto;
    @JoinColumn(name = "FacturaCliente_idFacturaCliente", referencedColumnName = "idFacturaCliente", nullable = false)
    @ManyToOne(optional = false)
    private Facturacliente facturaClienteidFacturaCliente;
    @JoinColumn(name = "RegimenHospedaje_TipoPension", referencedColumnName = "TipoPension", nullable = false)
    @ManyToOne(optional = false)
    private Regimenhospedaje regimenHospedajeTipoPension;
    @JoinColumn(name = "Sucursales_idSucursal", referencedColumnName = "idSucursal", nullable = false)
    @ManyToOne(optional = false)
    private Sucursales sucursalesidSucursal;
    @JoinColumn(name = "Vuelos_NroVuelo", referencedColumnName = "NroVuelo", nullable = false)
    @ManyToOne(optional = false)
    private Vuelos vuelosNroVuelo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clientes")
    private Facturacliente facturacliente;

    public Clientes() {
    }

    public Clientes(Integer idClientes) {
        this.idClientes = idClientes;
    }

    public Integer getIdClientes() {
        return idClientes;
    }

    public void setIdClientes(Integer idClientes) {
        this.idClientes = idClientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNroContacto() {
        return nroContacto;
    }

    public void setNroContacto(String nroContacto) {
        this.nroContacto = nroContacto;
    }

    public Facturacliente getFacturaClienteidFacturaCliente() {
        return facturaClienteidFacturaCliente;
    }

    public void setFacturaClienteidFacturaCliente(Facturacliente facturaClienteidFacturaCliente) {
        this.facturaClienteidFacturaCliente = facturaClienteidFacturaCliente;
    }

    public Regimenhospedaje getRegimenHospedajeTipoPension() {
        return regimenHospedajeTipoPension;
    }

    public void setRegimenHospedajeTipoPension(Regimenhospedaje regimenHospedajeTipoPension) {
        this.regimenHospedajeTipoPension = regimenHospedajeTipoPension;
    }

    public Sucursales getSucursalesidSucursal() {
        return sucursalesidSucursal;
    }

    public void setSucursalesidSucursal(Sucursales sucursalesidSucursal) {
        this.sucursalesidSucursal = sucursalesidSucursal;
    }

    public Vuelos getVuelosNroVuelo() {
        return vuelosNroVuelo;
    }

    public void setVuelosNroVuelo(Vuelos vuelosNroVuelo) {
        this.vuelosNroVuelo = vuelosNroVuelo;
    }

    public Facturacliente getFacturacliente() {
        return facturacliente;
    }

    public void setFacturacliente(Facturacliente facturacliente) {
        this.facturacliente = facturacliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClientes != null ? idClientes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.idClientes == null && other.idClientes != null) || (this.idClientes != null && !this.idClientes.equals(other.idClientes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udec.poo.entidades.Clientes[ idClientes=" + idClientes + " ]";
    }
    
}
