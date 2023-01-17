/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.poo.crud;

import co.edu.udec.poo.crud.exceptions.IllegalOrphanException;
import co.edu.udec.poo.crud.exceptions.NonexistentEntityException;
import co.edu.udec.poo.entidades.Clientes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.udec.poo.entidades.Facturacliente;
import co.edu.udec.poo.entidades.Regimenhospedaje;
import co.edu.udec.poo.entidades.Sucursales;
import co.edu.udec.poo.entidades.Vuelos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturacliente facturaClienteidFacturaCliente = clientes.getFacturaClienteidFacturaCliente();
            if (facturaClienteidFacturaCliente != null) {
                facturaClienteidFacturaCliente = em.getReference(facturaClienteidFacturaCliente.getClass(), facturaClienteidFacturaCliente.getIdFacturaCliente());
                clientes.setFacturaClienteidFacturaCliente(facturaClienteidFacturaCliente);
            }
            Regimenhospedaje regimenHospedajeTipoPension = clientes.getRegimenHospedajeTipoPension();
            if (regimenHospedajeTipoPension != null) {
                regimenHospedajeTipoPension = em.getReference(regimenHospedajeTipoPension.getClass(), regimenHospedajeTipoPension.getTipoPension());
                clientes.setRegimenHospedajeTipoPension(regimenHospedajeTipoPension);
            }
            Sucursales sucursalesidSucursal = clientes.getSucursalesidSucursal();
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal = em.getReference(sucursalesidSucursal.getClass(), sucursalesidSucursal.getIdSucursal());
                clientes.setSucursalesidSucursal(sucursalesidSucursal);
            }
            Vuelos vuelosNroVuelo = clientes.getVuelosNroVuelo();
            if (vuelosNroVuelo != null) {
                vuelosNroVuelo = em.getReference(vuelosNroVuelo.getClass(), vuelosNroVuelo.getNroVuelo());
                clientes.setVuelosNroVuelo(vuelosNroVuelo);
            }
            Facturacliente facturacliente = clientes.getFacturacliente();
            if (facturacliente != null) {
                facturacliente = em.getReference(facturacliente.getClass(), facturacliente.getIdFacturaCliente());
                clientes.setFacturacliente(facturacliente);
            }
            em.persist(clientes);
            if (facturaClienteidFacturaCliente != null) {
                facturaClienteidFacturaCliente.getClientesList().add(clientes);
                facturaClienteidFacturaCliente = em.merge(facturaClienteidFacturaCliente);
            }
            if (regimenHospedajeTipoPension != null) {
                regimenHospedajeTipoPension.getClientesList().add(clientes);
                regimenHospedajeTipoPension = em.merge(regimenHospedajeTipoPension);
            }
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal.getClientesList().add(clientes);
                sucursalesidSucursal = em.merge(sucursalesidSucursal);
            }
            if (vuelosNroVuelo != null) {
                vuelosNroVuelo.getClientesList().add(clientes);
                vuelosNroVuelo = em.merge(vuelosNroVuelo);
            }
            if (facturacliente != null) {
                Clientes oldClientesOfFacturacliente = facturacliente.getClientes();
                if (oldClientesOfFacturacliente != null) {
                    oldClientesOfFacturacliente.setFacturacliente(null);
                    oldClientesOfFacturacliente = em.merge(oldClientesOfFacturacliente);
                }
                facturacliente.setClientes(clientes);
                facturacliente = em.merge(facturacliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getIdClientes());
            Facturacliente facturaClienteidFacturaClienteOld = persistentClientes.getFacturaClienteidFacturaCliente();
            Facturacliente facturaClienteidFacturaClienteNew = clientes.getFacturaClienteidFacturaCliente();
            Regimenhospedaje regimenHospedajeTipoPensionOld = persistentClientes.getRegimenHospedajeTipoPension();
            Regimenhospedaje regimenHospedajeTipoPensionNew = clientes.getRegimenHospedajeTipoPension();
            Sucursales sucursalesidSucursalOld = persistentClientes.getSucursalesidSucursal();
            Sucursales sucursalesidSucursalNew = clientes.getSucursalesidSucursal();
            Vuelos vuelosNroVueloOld = persistentClientes.getVuelosNroVuelo();
            Vuelos vuelosNroVueloNew = clientes.getVuelosNroVuelo();
            Facturacliente facturaclienteOld = persistentClientes.getFacturacliente();
            Facturacliente facturaclienteNew = clientes.getFacturacliente();
            List<String> illegalOrphanMessages = null;
            if (facturaclienteOld != null && !facturaclienteOld.equals(facturaclienteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Facturacliente " + facturaclienteOld + " since its clientes field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (facturaClienteidFacturaClienteNew != null) {
                facturaClienteidFacturaClienteNew = em.getReference(facturaClienteidFacturaClienteNew.getClass(), facturaClienteidFacturaClienteNew.getIdFacturaCliente());
                clientes.setFacturaClienteidFacturaCliente(facturaClienteidFacturaClienteNew);
            }
            if (regimenHospedajeTipoPensionNew != null) {
                regimenHospedajeTipoPensionNew = em.getReference(regimenHospedajeTipoPensionNew.getClass(), regimenHospedajeTipoPensionNew.getTipoPension());
                clientes.setRegimenHospedajeTipoPension(regimenHospedajeTipoPensionNew);
            }
            if (sucursalesidSucursalNew != null) {
                sucursalesidSucursalNew = em.getReference(sucursalesidSucursalNew.getClass(), sucursalesidSucursalNew.getIdSucursal());
                clientes.setSucursalesidSucursal(sucursalesidSucursalNew);
            }
            if (vuelosNroVueloNew != null) {
                vuelosNroVueloNew = em.getReference(vuelosNroVueloNew.getClass(), vuelosNroVueloNew.getNroVuelo());
                clientes.setVuelosNroVuelo(vuelosNroVueloNew);
            }
            if (facturaclienteNew != null) {
                facturaclienteNew = em.getReference(facturaclienteNew.getClass(), facturaclienteNew.getIdFacturaCliente());
                clientes.setFacturacliente(facturaclienteNew);
            }
            clientes = em.merge(clientes);
            if (facturaClienteidFacturaClienteOld != null && !facturaClienteidFacturaClienteOld.equals(facturaClienteidFacturaClienteNew)) {
                facturaClienteidFacturaClienteOld.getClientesList().remove(clientes);
                facturaClienteidFacturaClienteOld = em.merge(facturaClienteidFacturaClienteOld);
            }
            if (facturaClienteidFacturaClienteNew != null && !facturaClienteidFacturaClienteNew.equals(facturaClienteidFacturaClienteOld)) {
                facturaClienteidFacturaClienteNew.getClientesList().add(clientes);
                facturaClienteidFacturaClienteNew = em.merge(facturaClienteidFacturaClienteNew);
            }
            if (regimenHospedajeTipoPensionOld != null && !regimenHospedajeTipoPensionOld.equals(regimenHospedajeTipoPensionNew)) {
                regimenHospedajeTipoPensionOld.getClientesList().remove(clientes);
                regimenHospedajeTipoPensionOld = em.merge(regimenHospedajeTipoPensionOld);
            }
            if (regimenHospedajeTipoPensionNew != null && !regimenHospedajeTipoPensionNew.equals(regimenHospedajeTipoPensionOld)) {
                regimenHospedajeTipoPensionNew.getClientesList().add(clientes);
                regimenHospedajeTipoPensionNew = em.merge(regimenHospedajeTipoPensionNew);
            }
            if (sucursalesidSucursalOld != null && !sucursalesidSucursalOld.equals(sucursalesidSucursalNew)) {
                sucursalesidSucursalOld.getClientesList().remove(clientes);
                sucursalesidSucursalOld = em.merge(sucursalesidSucursalOld);
            }
            if (sucursalesidSucursalNew != null && !sucursalesidSucursalNew.equals(sucursalesidSucursalOld)) {
                sucursalesidSucursalNew.getClientesList().add(clientes);
                sucursalesidSucursalNew = em.merge(sucursalesidSucursalNew);
            }
            if (vuelosNroVueloOld != null && !vuelosNroVueloOld.equals(vuelosNroVueloNew)) {
                vuelosNroVueloOld.getClientesList().remove(clientes);
                vuelosNroVueloOld = em.merge(vuelosNroVueloOld);
            }
            if (vuelosNroVueloNew != null && !vuelosNroVueloNew.equals(vuelosNroVueloOld)) {
                vuelosNroVueloNew.getClientesList().add(clientes);
                vuelosNroVueloNew = em.merge(vuelosNroVueloNew);
            }
            if (facturaclienteNew != null && !facturaclienteNew.equals(facturaclienteOld)) {
                Clientes oldClientesOfFacturacliente = facturaclienteNew.getClientes();
                if (oldClientesOfFacturacliente != null) {
                    oldClientesOfFacturacliente.setFacturacliente(null);
                    oldClientesOfFacturacliente = em.merge(oldClientesOfFacturacliente);
                }
                facturaclienteNew.setClientes(clientes);
                facturaclienteNew = em.merge(facturaclienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientes.getIdClientes();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getIdClientes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Facturacliente facturaclienteOrphanCheck = clientes.getFacturacliente();
            if (facturaclienteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Facturacliente " + facturaclienteOrphanCheck + " in its facturacliente field has a non-nullable clientes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Facturacliente facturaClienteidFacturaCliente = clientes.getFacturaClienteidFacturaCliente();
            if (facturaClienteidFacturaCliente != null) {
                facturaClienteidFacturaCliente.getClientesList().remove(clientes);
                facturaClienteidFacturaCliente = em.merge(facturaClienteidFacturaCliente);
            }
            Regimenhospedaje regimenHospedajeTipoPension = clientes.getRegimenHospedajeTipoPension();
            if (regimenHospedajeTipoPension != null) {
                regimenHospedajeTipoPension.getClientesList().remove(clientes);
                regimenHospedajeTipoPension = em.merge(regimenHospedajeTipoPension);
            }
            Sucursales sucursalesidSucursal = clientes.getSucursalesidSucursal();
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal.getClientesList().remove(clientes);
                sucursalesidSucursal = em.merge(sucursalesidSucursal);
            }
            Vuelos vuelosNroVuelo = clientes.getVuelosNroVuelo();
            if (vuelosNroVuelo != null) {
                vuelosNroVuelo.getClientesList().remove(clientes);
                vuelosNroVuelo = em.merge(vuelosNroVuelo);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
