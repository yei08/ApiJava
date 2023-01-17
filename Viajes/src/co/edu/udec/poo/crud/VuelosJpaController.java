/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.poo.crud;

import co.edu.udec.poo.crud.exceptions.IllegalOrphanException;
import co.edu.udec.poo.crud.exceptions.NonexistentEntityException;
import co.edu.udec.poo.crud.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.udec.poo.entidades.Sucursales;
import co.edu.udec.poo.entidades.Clientes;
import co.edu.udec.poo.entidades.Vuelos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class VuelosJpaController implements Serializable {

    public VuelosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vuelos vuelos) throws PreexistingEntityException, Exception {
        if (vuelos.getClientesList() == null) {
            vuelos.setClientesList(new ArrayList<Clientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursales sucursalesidSucursal = vuelos.getSucursalesidSucursal();
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal = em.getReference(sucursalesidSucursal.getClass(), sucursalesidSucursal.getIdSucursal());
                vuelos.setSucursalesidSucursal(sucursalesidSucursal);
            }
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : vuelos.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getIdClientes());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            vuelos.setClientesList(attachedClientesList);
            em.persist(vuelos);
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal.getVuelosList().add(vuelos);
                sucursalesidSucursal = em.merge(sucursalesidSucursal);
            }
            for (Clientes clientesListClientes : vuelos.getClientesList()) {
                Vuelos oldVuelosNroVueloOfClientesListClientes = clientesListClientes.getVuelosNroVuelo();
                clientesListClientes.setVuelosNroVuelo(vuelos);
                clientesListClientes = em.merge(clientesListClientes);
                if (oldVuelosNroVueloOfClientesListClientes != null) {
                    oldVuelosNroVueloOfClientesListClientes.getClientesList().remove(clientesListClientes);
                    oldVuelosNroVueloOfClientesListClientes = em.merge(oldVuelosNroVueloOfClientesListClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVuelos(vuelos.getNroVuelo()) != null) {
                throw new PreexistingEntityException("Vuelos " + vuelos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vuelos vuelos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vuelos persistentVuelos = em.find(Vuelos.class, vuelos.getNroVuelo());
            Sucursales sucursalesidSucursalOld = persistentVuelos.getSucursalesidSucursal();
            Sucursales sucursalesidSucursalNew = vuelos.getSucursalesidSucursal();
            List<Clientes> clientesListOld = persistentVuelos.getClientesList();
            List<Clientes> clientesListNew = vuelos.getClientesList();
            List<String> illegalOrphanMessages = null;
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clientes " + clientesListOldClientes + " since its vuelosNroVuelo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sucursalesidSucursalNew != null) {
                sucursalesidSucursalNew = em.getReference(sucursalesidSucursalNew.getClass(), sucursalesidSucursalNew.getIdSucursal());
                vuelos.setSucursalesidSucursal(sucursalesidSucursalNew);
            }
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getIdClientes());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            vuelos.setClientesList(clientesListNew);
            vuelos = em.merge(vuelos);
            if (sucursalesidSucursalOld != null && !sucursalesidSucursalOld.equals(sucursalesidSucursalNew)) {
                sucursalesidSucursalOld.getVuelosList().remove(vuelos);
                sucursalesidSucursalOld = em.merge(sucursalesidSucursalOld);
            }
            if (sucursalesidSucursalNew != null && !sucursalesidSucursalNew.equals(sucursalesidSucursalOld)) {
                sucursalesidSucursalNew.getVuelosList().add(vuelos);
                sucursalesidSucursalNew = em.merge(sucursalesidSucursalNew);
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    Vuelos oldVuelosNroVueloOfClientesListNewClientes = clientesListNewClientes.getVuelosNroVuelo();
                    clientesListNewClientes.setVuelosNroVuelo(vuelos);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                    if (oldVuelosNroVueloOfClientesListNewClientes != null && !oldVuelosNroVueloOfClientesListNewClientes.equals(vuelos)) {
                        oldVuelosNroVueloOfClientesListNewClientes.getClientesList().remove(clientesListNewClientes);
                        oldVuelosNroVueloOfClientesListNewClientes = em.merge(oldVuelosNroVueloOfClientesListNewClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vuelos.getNroVuelo();
                if (findVuelos(id) == null) {
                    throw new NonexistentEntityException("The vuelos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vuelos vuelos;
            try {
                vuelos = em.getReference(Vuelos.class, id);
                vuelos.getNroVuelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vuelos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Clientes> clientesListOrphanCheck = vuelos.getClientesList();
            for (Clientes clientesListOrphanCheckClientes : clientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vuelos (" + vuelos + ") cannot be destroyed since the Clientes " + clientesListOrphanCheckClientes + " in its clientesList field has a non-nullable vuelosNroVuelo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sucursales sucursalesidSucursal = vuelos.getSucursalesidSucursal();
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal.getVuelosList().remove(vuelos);
                sucursalesidSucursal = em.merge(sucursalesidSucursal);
            }
            em.remove(vuelos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vuelos> findVuelosEntities() {
        return findVuelosEntities(true, -1, -1);
    }

    public List<Vuelos> findVuelosEntities(int maxResults, int firstResult) {
        return findVuelosEntities(false, maxResults, firstResult);
    }

    private List<Vuelos> findVuelosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vuelos.class));
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

    public Vuelos findVuelos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vuelos.class, id);
        } finally {
            em.close();
        }
    }

    public int getVuelosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vuelos> rt = cq.from(Vuelos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
