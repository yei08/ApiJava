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
import co.edu.udec.poo.entidades.Vuelos;
import java.util.ArrayList;
import java.util.List;
import co.edu.udec.poo.entidades.Clientes;
import co.edu.udec.poo.entidades.Sucursales;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class SucursalesJpaController implements Serializable {

    public SucursalesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sucursales sucursales) throws PreexistingEntityException, Exception {
        if (sucursales.getVuelosList() == null) {
            sucursales.setVuelosList(new ArrayList<Vuelos>());
        }
        if (sucursales.getClientesList() == null) {
            sucursales.setClientesList(new ArrayList<Clientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Vuelos> attachedVuelosList = new ArrayList<Vuelos>();
            for (Vuelos vuelosListVuelosToAttach : sucursales.getVuelosList()) {
                vuelosListVuelosToAttach = em.getReference(vuelosListVuelosToAttach.getClass(), vuelosListVuelosToAttach.getNroVuelo());
                attachedVuelosList.add(vuelosListVuelosToAttach);
            }
            sucursales.setVuelosList(attachedVuelosList);
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : sucursales.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getIdClientes());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            sucursales.setClientesList(attachedClientesList);
            em.persist(sucursales);
            for (Vuelos vuelosListVuelos : sucursales.getVuelosList()) {
                Sucursales oldSucursalesidSucursalOfVuelosListVuelos = vuelosListVuelos.getSucursalesidSucursal();
                vuelosListVuelos.setSucursalesidSucursal(sucursales);
                vuelosListVuelos = em.merge(vuelosListVuelos);
                if (oldSucursalesidSucursalOfVuelosListVuelos != null) {
                    oldSucursalesidSucursalOfVuelosListVuelos.getVuelosList().remove(vuelosListVuelos);
                    oldSucursalesidSucursalOfVuelosListVuelos = em.merge(oldSucursalesidSucursalOfVuelosListVuelos);
                }
            }
            for (Clientes clientesListClientes : sucursales.getClientesList()) {
                Sucursales oldSucursalesidSucursalOfClientesListClientes = clientesListClientes.getSucursalesidSucursal();
                clientesListClientes.setSucursalesidSucursal(sucursales);
                clientesListClientes = em.merge(clientesListClientes);
                if (oldSucursalesidSucursalOfClientesListClientes != null) {
                    oldSucursalesidSucursalOfClientesListClientes.getClientesList().remove(clientesListClientes);
                    oldSucursalesidSucursalOfClientesListClientes = em.merge(oldSucursalesidSucursalOfClientesListClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSucursales(sucursales.getIdSucursal()) != null) {
                throw new PreexistingEntityException("Sucursales " + sucursales + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sucursales sucursales) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursales persistentSucursales = em.find(Sucursales.class, sucursales.getIdSucursal());
            List<Vuelos> vuelosListOld = persistentSucursales.getVuelosList();
            List<Vuelos> vuelosListNew = sucursales.getVuelosList();
            List<Clientes> clientesListOld = persistentSucursales.getClientesList();
            List<Clientes> clientesListNew = sucursales.getClientesList();
            List<String> illegalOrphanMessages = null;
            for (Vuelos vuelosListOldVuelos : vuelosListOld) {
                if (!vuelosListNew.contains(vuelosListOldVuelos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vuelos " + vuelosListOldVuelos + " since its sucursalesidSucursal field is not nullable.");
                }
            }
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clientes " + clientesListOldClientes + " since its sucursalesidSucursal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Vuelos> attachedVuelosListNew = new ArrayList<Vuelos>();
            for (Vuelos vuelosListNewVuelosToAttach : vuelosListNew) {
                vuelosListNewVuelosToAttach = em.getReference(vuelosListNewVuelosToAttach.getClass(), vuelosListNewVuelosToAttach.getNroVuelo());
                attachedVuelosListNew.add(vuelosListNewVuelosToAttach);
            }
            vuelosListNew = attachedVuelosListNew;
            sucursales.setVuelosList(vuelosListNew);
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getIdClientes());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            sucursales.setClientesList(clientesListNew);
            sucursales = em.merge(sucursales);
            for (Vuelos vuelosListNewVuelos : vuelosListNew) {
                if (!vuelosListOld.contains(vuelosListNewVuelos)) {
                    Sucursales oldSucursalesidSucursalOfVuelosListNewVuelos = vuelosListNewVuelos.getSucursalesidSucursal();
                    vuelosListNewVuelos.setSucursalesidSucursal(sucursales);
                    vuelosListNewVuelos = em.merge(vuelosListNewVuelos);
                    if (oldSucursalesidSucursalOfVuelosListNewVuelos != null && !oldSucursalesidSucursalOfVuelosListNewVuelos.equals(sucursales)) {
                        oldSucursalesidSucursalOfVuelosListNewVuelos.getVuelosList().remove(vuelosListNewVuelos);
                        oldSucursalesidSucursalOfVuelosListNewVuelos = em.merge(oldSucursalesidSucursalOfVuelosListNewVuelos);
                    }
                }
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    Sucursales oldSucursalesidSucursalOfClientesListNewClientes = clientesListNewClientes.getSucursalesidSucursal();
                    clientesListNewClientes.setSucursalesidSucursal(sucursales);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                    if (oldSucursalesidSucursalOfClientesListNewClientes != null && !oldSucursalesidSucursalOfClientesListNewClientes.equals(sucursales)) {
                        oldSucursalesidSucursalOfClientesListNewClientes.getClientesList().remove(clientesListNewClientes);
                        oldSucursalesidSucursalOfClientesListNewClientes = em.merge(oldSucursalesidSucursalOfClientesListNewClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sucursales.getIdSucursal();
                if (findSucursales(id) == null) {
                    throw new NonexistentEntityException("The sucursales with id " + id + " no longer exists.");
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
            Sucursales sucursales;
            try {
                sucursales = em.getReference(Sucursales.class, id);
                sucursales.getIdSucursal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursales with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Vuelos> vuelosListOrphanCheck = sucursales.getVuelosList();
            for (Vuelos vuelosListOrphanCheckVuelos : vuelosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursales (" + sucursales + ") cannot be destroyed since the Vuelos " + vuelosListOrphanCheckVuelos + " in its vuelosList field has a non-nullable sucursalesidSucursal field.");
            }
            List<Clientes> clientesListOrphanCheck = sucursales.getClientesList();
            for (Clientes clientesListOrphanCheckClientes : clientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursales (" + sucursales + ") cannot be destroyed since the Clientes " + clientesListOrphanCheckClientes + " in its clientesList field has a non-nullable sucursalesidSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sucursales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sucursales> findSucursalesEntities() {
        return findSucursalesEntities(true, -1, -1);
    }

    public List<Sucursales> findSucursalesEntities(int maxResults, int firstResult) {
        return findSucursalesEntities(false, maxResults, firstResult);
    }

    private List<Sucursales> findSucursalesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursales.class));
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

    public Sucursales findSucursales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursales.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursales> rt = cq.from(Sucursales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
