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
import co.edu.udec.poo.entidades.Clientes;
import co.edu.udec.poo.entidades.Facturacliente;
import co.edu.udec.poo.entidades.Hoteles;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class FacturaclienteJpaController implements Serializable {

    public FacturaclienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Facturacliente facturacliente) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (facturacliente.getClientesList() == null) {
            facturacliente.setClientesList(new ArrayList<Clientes>());
        }
        List<String> illegalOrphanMessages = null;
        Clientes clientesOrphanCheck = facturacliente.getClientes();
        if (clientesOrphanCheck != null) {
            Facturacliente oldFacturaClienteidFacturaClienteOfClientes = clientesOrphanCheck.getFacturaClienteidFacturaCliente();
            if (oldFacturaClienteidFacturaClienteOfClientes != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Clientes " + clientesOrphanCheck + " already has an item of type Facturacliente whose clientes column cannot be null. Please make another selection for the clientes field.");
            }
        }
        Hoteles hotelesOrphanCheck = facturacliente.getHoteles();
        if (hotelesOrphanCheck != null) {
            Facturacliente oldFacturaclienteOfHoteles = hotelesOrphanCheck.getFacturacliente();
            if (oldFacturaclienteOfHoteles != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Hoteles " + hotelesOrphanCheck + " already has an item of type Facturacliente whose hoteles column cannot be null. Please make another selection for the hoteles field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes = facturacliente.getClientes();
            if (clientes != null) {
                clientes = em.getReference(clientes.getClass(), clientes.getIdClientes());
                facturacliente.setClientes(clientes);
            }
            Hoteles hoteles = facturacliente.getHoteles();
            if (hoteles != null) {
                hoteles = em.getReference(hoteles.getClass(), hoteles.getHotelesPK());
                facturacliente.setHoteles(hoteles);
            }
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : facturacliente.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getIdClientes());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            facturacliente.setClientesList(attachedClientesList);
            em.persist(facturacliente);
            if (clientes != null) {
                clientes.setFacturaClienteidFacturaCliente(facturacliente);
                clientes = em.merge(clientes);
            }
            if (hoteles != null) {
                hoteles.setFacturacliente(facturacliente);
                hoteles = em.merge(hoteles);
            }
            for (Clientes clientesListClientes : facturacliente.getClientesList()) {
                Facturacliente oldFacturaClienteidFacturaClienteOfClientesListClientes = clientesListClientes.getFacturaClienteidFacturaCliente();
                clientesListClientes.setFacturaClienteidFacturaCliente(facturacliente);
                clientesListClientes = em.merge(clientesListClientes);
                if (oldFacturaClienteidFacturaClienteOfClientesListClientes != null) {
                    oldFacturaClienteidFacturaClienteOfClientesListClientes.getClientesList().remove(clientesListClientes);
                    oldFacturaClienteidFacturaClienteOfClientesListClientes = em.merge(oldFacturaClienteidFacturaClienteOfClientesListClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturacliente(facturacliente.getIdFacturaCliente()) != null) {
                throw new PreexistingEntityException("Facturacliente " + facturacliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facturacliente facturacliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturacliente persistentFacturacliente = em.find(Facturacliente.class, facturacliente.getIdFacturaCliente());
            Clientes clientesOld = persistentFacturacliente.getClientes();
            Clientes clientesNew = facturacliente.getClientes();
            Hoteles hotelesOld = persistentFacturacliente.getHoteles();
            Hoteles hotelesNew = facturacliente.getHoteles();
            List<Clientes> clientesListOld = persistentFacturacliente.getClientesList();
            List<Clientes> clientesListNew = facturacliente.getClientesList();
            List<String> illegalOrphanMessages = null;
            if (clientesOld != null && !clientesOld.equals(clientesNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Clientes " + clientesOld + " since its facturaClienteidFacturaCliente field is not nullable.");
            }
            if (clientesNew != null && !clientesNew.equals(clientesOld)) {
                Facturacliente oldFacturaClienteidFacturaClienteOfClientes = clientesNew.getFacturaClienteidFacturaCliente();
                if (oldFacturaClienteidFacturaClienteOfClientes != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Clientes " + clientesNew + " already has an item of type Facturacliente whose clientes column cannot be null. Please make another selection for the clientes field.");
                }
            }
            if (hotelesNew != null && !hotelesNew.equals(hotelesOld)) {
                Facturacliente oldFacturaclienteOfHoteles = hotelesNew.getFacturacliente();
                if (oldFacturaclienteOfHoteles != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Hoteles " + hotelesNew + " already has an item of type Facturacliente whose hoteles column cannot be null. Please make another selection for the hoteles field.");
                }
            }
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clientes " + clientesListOldClientes + " since its facturaClienteidFacturaCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientesNew != null) {
                clientesNew = em.getReference(clientesNew.getClass(), clientesNew.getIdClientes());
                facturacliente.setClientes(clientesNew);
            }
            if (hotelesNew != null) {
                hotelesNew = em.getReference(hotelesNew.getClass(), hotelesNew.getHotelesPK());
                facturacliente.setHoteles(hotelesNew);
            }
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getIdClientes());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            facturacliente.setClientesList(clientesListNew);
            facturacliente = em.merge(facturacliente);
            if (clientesNew != null && !clientesNew.equals(clientesOld)) {
                clientesNew.setFacturaClienteidFacturaCliente(facturacliente);
                clientesNew = em.merge(clientesNew);
            }
            if (hotelesOld != null && !hotelesOld.equals(hotelesNew)) {
                hotelesOld.setFacturacliente(null);
                hotelesOld = em.merge(hotelesOld);
            }
            if (hotelesNew != null && !hotelesNew.equals(hotelesOld)) {
                hotelesNew.setFacturacliente(facturacliente);
                hotelesNew = em.merge(hotelesNew);
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    Facturacliente oldFacturaClienteidFacturaClienteOfClientesListNewClientes = clientesListNewClientes.getFacturaClienteidFacturaCliente();
                    clientesListNewClientes.setFacturaClienteidFacturaCliente(facturacliente);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                    if (oldFacturaClienteidFacturaClienteOfClientesListNewClientes != null && !oldFacturaClienteidFacturaClienteOfClientesListNewClientes.equals(facturacliente)) {
                        oldFacturaClienteidFacturaClienteOfClientesListNewClientes.getClientesList().remove(clientesListNewClientes);
                        oldFacturaClienteidFacturaClienteOfClientesListNewClientes = em.merge(oldFacturaClienteidFacturaClienteOfClientesListNewClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturacliente.getIdFacturaCliente();
                if (findFacturacliente(id) == null) {
                    throw new NonexistentEntityException("The facturacliente with id " + id + " no longer exists.");
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
            Facturacliente facturacliente;
            try {
                facturacliente = em.getReference(Facturacliente.class, id);
                facturacliente.getIdFacturaCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturacliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Clientes clientesOrphanCheck = facturacliente.getClientes();
            if (clientesOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Facturacliente (" + facturacliente + ") cannot be destroyed since the Clientes " + clientesOrphanCheck + " in its clientes field has a non-nullable facturaClienteidFacturaCliente field.");
            }
            List<Clientes> clientesListOrphanCheck = facturacliente.getClientesList();
            for (Clientes clientesListOrphanCheckClientes : clientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Facturacliente (" + facturacliente + ") cannot be destroyed since the Clientes " + clientesListOrphanCheckClientes + " in its clientesList field has a non-nullable facturaClienteidFacturaCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Hoteles hoteles = facturacliente.getHoteles();
            if (hoteles != null) {
                hoteles.setFacturacliente(null);
                hoteles = em.merge(hoteles);
            }
            em.remove(facturacliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facturacliente> findFacturaclienteEntities() {
        return findFacturaclienteEntities(true, -1, -1);
    }

    public List<Facturacliente> findFacturaclienteEntities(int maxResults, int firstResult) {
        return findFacturaclienteEntities(false, maxResults, firstResult);
    }

    private List<Facturacliente> findFacturaclienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facturacliente.class));
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

    public Facturacliente findFacturacliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facturacliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaclienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facturacliente> rt = cq.from(Facturacliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
