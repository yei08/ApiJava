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
import co.edu.udec.poo.entidades.Tienda;
import co.edu.udec.poo.entidades.Cliente;
import co.edu.udec.poo.entidades.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getClienteList() == null) {
            producto.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tienda tiendaNombre = producto.getTiendaNombre();
            if (tiendaNombre != null) {
                tiendaNombre = em.getReference(tiendaNombre.getClass(), tiendaNombre.getNombre());
                producto.setTiendaNombre(tiendaNombre);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : producto.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCLIENTE());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            producto.setClienteList(attachedClienteList);
            em.persist(producto);
            if (tiendaNombre != null) {
                tiendaNombre.getProductoList().add(producto);
                tiendaNombre = em.merge(tiendaNombre);
            }
            for (Cliente clienteListCliente : producto.getClienteList()) {
                Producto oldProductoIdproductoOfClienteListCliente = clienteListCliente.getProductoIdproducto();
                clienteListCliente.setProductoIdproducto(producto);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldProductoIdproductoOfClienteListCliente != null) {
                    oldProductoIdproductoOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldProductoIdproductoOfClienteListCliente = em.merge(oldProductoIdproductoOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducto(producto.getIdproducto()) != null) {
                throw new PreexistingEntityException("Producto " + producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdproducto());
            Tienda tiendaNombreOld = persistentProducto.getTiendaNombre();
            Tienda tiendaNombreNew = producto.getTiendaNombre();
            List<Cliente> clienteListOld = persistentProducto.getClienteList();
            List<Cliente> clienteListNew = producto.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its productoIdproducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tiendaNombreNew != null) {
                tiendaNombreNew = em.getReference(tiendaNombreNew.getClass(), tiendaNombreNew.getNombre());
                producto.setTiendaNombre(tiendaNombreNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCLIENTE());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            producto.setClienteList(clienteListNew);
            producto = em.merge(producto);
            if (tiendaNombreOld != null && !tiendaNombreOld.equals(tiendaNombreNew)) {
                tiendaNombreOld.getProductoList().remove(producto);
                tiendaNombreOld = em.merge(tiendaNombreOld);
            }
            if (tiendaNombreNew != null && !tiendaNombreNew.equals(tiendaNombreOld)) {
                tiendaNombreNew.getProductoList().add(producto);
                tiendaNombreNew = em.merge(tiendaNombreNew);
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Producto oldProductoIdproductoOfClienteListNewCliente = clienteListNewCliente.getProductoIdproducto();
                    clienteListNewCliente.setProductoIdproducto(producto);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldProductoIdproductoOfClienteListNewCliente != null && !oldProductoIdproductoOfClienteListNewCliente.equals(producto)) {
                        oldProductoIdproductoOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldProductoIdproductoOfClienteListNewCliente = em.merge(oldProductoIdproductoOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdproducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdproducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = producto.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable productoIdproducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tienda tiendaNombre = producto.getTiendaNombre();
            if (tiendaNombre != null) {
                tiendaNombre.getProductoList().remove(producto);
                tiendaNombre = em.merge(tiendaNombre);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
