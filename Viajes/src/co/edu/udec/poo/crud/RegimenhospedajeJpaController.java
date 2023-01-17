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
import co.edu.udec.poo.entidades.Hoteles;
import co.edu.udec.poo.entidades.Clientes;
import co.edu.udec.poo.entidades.Regimenhospedaje;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class RegimenhospedajeJpaController implements Serializable {

    public RegimenhospedajeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Regimenhospedaje regimenhospedaje) throws PreexistingEntityException, Exception {
        if (regimenhospedaje.getClientesList() == null) {
            regimenhospedaje.setClientesList(new ArrayList<Clientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hoteles hoteles = regimenhospedaje.getHoteles();
            if (hoteles != null) {
                hoteles = em.getReference(hoteles.getClass(), hoteles.getHotelesPK());
                regimenhospedaje.setHoteles(hoteles);
            }
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : regimenhospedaje.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getIdClientes());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            regimenhospedaje.setClientesList(attachedClientesList);
            em.persist(regimenhospedaje);
            if (hoteles != null) {
                hoteles.getRegimenhospedajeList().add(regimenhospedaje);
                hoteles = em.merge(hoteles);
            }
            for (Clientes clientesListClientes : regimenhospedaje.getClientesList()) {
                Regimenhospedaje oldRegimenHospedajeTipoPensionOfClientesListClientes = clientesListClientes.getRegimenHospedajeTipoPension();
                clientesListClientes.setRegimenHospedajeTipoPension(regimenhospedaje);
                clientesListClientes = em.merge(clientesListClientes);
                if (oldRegimenHospedajeTipoPensionOfClientesListClientes != null) {
                    oldRegimenHospedajeTipoPensionOfClientesListClientes.getClientesList().remove(clientesListClientes);
                    oldRegimenHospedajeTipoPensionOfClientesListClientes = em.merge(oldRegimenHospedajeTipoPensionOfClientesListClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegimenhospedaje(regimenhospedaje.getTipoPension()) != null) {
                throw new PreexistingEntityException("Regimenhospedaje " + regimenhospedaje + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Regimenhospedaje regimenhospedaje) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regimenhospedaje persistentRegimenhospedaje = em.find(Regimenhospedaje.class, regimenhospedaje.getTipoPension());
            Hoteles hotelesOld = persistentRegimenhospedaje.getHoteles();
            Hoteles hotelesNew = regimenhospedaje.getHoteles();
            List<Clientes> clientesListOld = persistentRegimenhospedaje.getClientesList();
            List<Clientes> clientesListNew = regimenhospedaje.getClientesList();
            List<String> illegalOrphanMessages = null;
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clientes " + clientesListOldClientes + " since its regimenHospedajeTipoPension field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (hotelesNew != null) {
                hotelesNew = em.getReference(hotelesNew.getClass(), hotelesNew.getHotelesPK());
                regimenhospedaje.setHoteles(hotelesNew);
            }
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getIdClientes());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            regimenhospedaje.setClientesList(clientesListNew);
            regimenhospedaje = em.merge(regimenhospedaje);
            if (hotelesOld != null && !hotelesOld.equals(hotelesNew)) {
                hotelesOld.getRegimenhospedajeList().remove(regimenhospedaje);
                hotelesOld = em.merge(hotelesOld);
            }
            if (hotelesNew != null && !hotelesNew.equals(hotelesOld)) {
                hotelesNew.getRegimenhospedajeList().add(regimenhospedaje);
                hotelesNew = em.merge(hotelesNew);
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    Regimenhospedaje oldRegimenHospedajeTipoPensionOfClientesListNewClientes = clientesListNewClientes.getRegimenHospedajeTipoPension();
                    clientesListNewClientes.setRegimenHospedajeTipoPension(regimenhospedaje);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                    if (oldRegimenHospedajeTipoPensionOfClientesListNewClientes != null && !oldRegimenHospedajeTipoPensionOfClientesListNewClientes.equals(regimenhospedaje)) {
                        oldRegimenHospedajeTipoPensionOfClientesListNewClientes.getClientesList().remove(clientesListNewClientes);
                        oldRegimenHospedajeTipoPensionOfClientesListNewClientes = em.merge(oldRegimenHospedajeTipoPensionOfClientesListNewClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = regimenhospedaje.getTipoPension();
                if (findRegimenhospedaje(id) == null) {
                    throw new NonexistentEntityException("The regimenhospedaje with id " + id + " no longer exists.");
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
            Regimenhospedaje regimenhospedaje;
            try {
                regimenhospedaje = em.getReference(Regimenhospedaje.class, id);
                regimenhospedaje.getTipoPension();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regimenhospedaje with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Clientes> clientesListOrphanCheck = regimenhospedaje.getClientesList();
            for (Clientes clientesListOrphanCheckClientes : clientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Regimenhospedaje (" + regimenhospedaje + ") cannot be destroyed since the Clientes " + clientesListOrphanCheckClientes + " in its clientesList field has a non-nullable regimenHospedajeTipoPension field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Hoteles hoteles = regimenhospedaje.getHoteles();
            if (hoteles != null) {
                hoteles.getRegimenhospedajeList().remove(regimenhospedaje);
                hoteles = em.merge(hoteles);
            }
            em.remove(regimenhospedaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Regimenhospedaje> findRegimenhospedajeEntities() {
        return findRegimenhospedajeEntities(true, -1, -1);
    }

    public List<Regimenhospedaje> findRegimenhospedajeEntities(int maxResults, int firstResult) {
        return findRegimenhospedajeEntities(false, maxResults, firstResult);
    }

    private List<Regimenhospedaje> findRegimenhospedajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Regimenhospedaje.class));
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

    public Regimenhospedaje findRegimenhospedaje(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Regimenhospedaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegimenhospedajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regimenhospedaje> rt = cq.from(Regimenhospedaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
