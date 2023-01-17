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
import co.edu.udec.poo.entidades.Plazadisponible;
import co.edu.udec.poo.entidades.Facturacliente;
import co.edu.udec.poo.entidades.Hoteles;
import co.edu.udec.poo.entidades.HotelesPK;
import co.edu.udec.poo.entidades.Regimenhospedaje;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class HotelesJpaController implements Serializable {

    public HotelesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hoteles hoteles) throws PreexistingEntityException, Exception {
        if (hoteles.getHotelesPK() == null) {
            hoteles.setHotelesPK(new HotelesPK());
        }
        if (hoteles.getRegimenhospedajeList() == null) {
            hoteles.setRegimenhospedajeList(new ArrayList<Regimenhospedaje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plazadisponible plazaDisponibleFechaDeRefencia1 = hoteles.getPlazaDisponibleFechaDeRefencia1();
            if (plazaDisponibleFechaDeRefencia1 != null) {
                plazaDisponibleFechaDeRefencia1 = em.getReference(plazaDisponibleFechaDeRefencia1.getClass(), plazaDisponibleFechaDeRefencia1.getFechaDeRefencia());
                hoteles.setPlazaDisponibleFechaDeRefencia1(plazaDisponibleFechaDeRefencia1);
            }
            Facturacliente facturacliente = hoteles.getFacturacliente();
            if (facturacliente != null) {
                facturacliente = em.getReference(facturacliente.getClass(), facturacliente.getIdFacturaCliente());
                hoteles.setFacturacliente(facturacliente);
            }
            List<Regimenhospedaje> attachedRegimenhospedajeList = new ArrayList<Regimenhospedaje>();
            for (Regimenhospedaje regimenhospedajeListRegimenhospedajeToAttach : hoteles.getRegimenhospedajeList()) {
                regimenhospedajeListRegimenhospedajeToAttach = em.getReference(regimenhospedajeListRegimenhospedajeToAttach.getClass(), regimenhospedajeListRegimenhospedajeToAttach.getTipoPension());
                attachedRegimenhospedajeList.add(regimenhospedajeListRegimenhospedajeToAttach);
            }
            hoteles.setRegimenhospedajeList(attachedRegimenhospedajeList);
            em.persist(hoteles);
            if (plazaDisponibleFechaDeRefencia1 != null) {
                plazaDisponibleFechaDeRefencia1.getHotelesList().add(hoteles);
                plazaDisponibleFechaDeRefencia1 = em.merge(plazaDisponibleFechaDeRefencia1);
            }
            if (facturacliente != null) {
                Hoteles oldHotelesOfFacturacliente = facturacliente.getHoteles();
                if (oldHotelesOfFacturacliente != null) {
                    oldHotelesOfFacturacliente.setFacturacliente(null);
                    oldHotelesOfFacturacliente = em.merge(oldHotelesOfFacturacliente);
                }
                facturacliente.setHoteles(hoteles);
                facturacliente = em.merge(facturacliente);
            }
            for (Regimenhospedaje regimenhospedajeListRegimenhospedaje : hoteles.getRegimenhospedajeList()) {
                Hoteles oldHotelesOfRegimenhospedajeListRegimenhospedaje = regimenhospedajeListRegimenhospedaje.getHoteles();
                regimenhospedajeListRegimenhospedaje.setHoteles(hoteles);
                regimenhospedajeListRegimenhospedaje = em.merge(regimenhospedajeListRegimenhospedaje);
                if (oldHotelesOfRegimenhospedajeListRegimenhospedaje != null) {
                    oldHotelesOfRegimenhospedajeListRegimenhospedaje.getRegimenhospedajeList().remove(regimenhospedajeListRegimenhospedaje);
                    oldHotelesOfRegimenhospedajeListRegimenhospedaje = em.merge(oldHotelesOfRegimenhospedajeListRegimenhospedaje);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHoteles(hoteles.getHotelesPK()) != null) {
                throw new PreexistingEntityException("Hoteles " + hoteles + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hoteles hoteles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hoteles persistentHoteles = em.find(Hoteles.class, hoteles.getHotelesPK());
            Plazadisponible plazaDisponibleFechaDeRefencia1Old = persistentHoteles.getPlazaDisponibleFechaDeRefencia1();
            Plazadisponible plazaDisponibleFechaDeRefencia1New = hoteles.getPlazaDisponibleFechaDeRefencia1();
            Facturacliente facturaclienteOld = persistentHoteles.getFacturacliente();
            Facturacliente facturaclienteNew = hoteles.getFacturacliente();
            List<Regimenhospedaje> regimenhospedajeListOld = persistentHoteles.getRegimenhospedajeList();
            List<Regimenhospedaje> regimenhospedajeListNew = hoteles.getRegimenhospedajeList();
            List<String> illegalOrphanMessages = null;
            if (facturaclienteOld != null && !facturaclienteOld.equals(facturaclienteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Facturacliente " + facturaclienteOld + " since its hoteles field is not nullable.");
            }
            for (Regimenhospedaje regimenhospedajeListOldRegimenhospedaje : regimenhospedajeListOld) {
                if (!regimenhospedajeListNew.contains(regimenhospedajeListOldRegimenhospedaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Regimenhospedaje " + regimenhospedajeListOldRegimenhospedaje + " since its hoteles field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (plazaDisponibleFechaDeRefencia1New != null) {
                plazaDisponibleFechaDeRefencia1New = em.getReference(plazaDisponibleFechaDeRefencia1New.getClass(), plazaDisponibleFechaDeRefencia1New.getFechaDeRefencia());
                hoteles.setPlazaDisponibleFechaDeRefencia1(plazaDisponibleFechaDeRefencia1New);
            }
            if (facturaclienteNew != null) {
                facturaclienteNew = em.getReference(facturaclienteNew.getClass(), facturaclienteNew.getIdFacturaCliente());
                hoteles.setFacturacliente(facturaclienteNew);
            }
            List<Regimenhospedaje> attachedRegimenhospedajeListNew = new ArrayList<Regimenhospedaje>();
            for (Regimenhospedaje regimenhospedajeListNewRegimenhospedajeToAttach : regimenhospedajeListNew) {
                regimenhospedajeListNewRegimenhospedajeToAttach = em.getReference(regimenhospedajeListNewRegimenhospedajeToAttach.getClass(), regimenhospedajeListNewRegimenhospedajeToAttach.getTipoPension());
                attachedRegimenhospedajeListNew.add(regimenhospedajeListNewRegimenhospedajeToAttach);
            }
            regimenhospedajeListNew = attachedRegimenhospedajeListNew;
            hoteles.setRegimenhospedajeList(regimenhospedajeListNew);
            hoteles = em.merge(hoteles);
            if (plazaDisponibleFechaDeRefencia1Old != null && !plazaDisponibleFechaDeRefencia1Old.equals(plazaDisponibleFechaDeRefencia1New)) {
                plazaDisponibleFechaDeRefencia1Old.getHotelesList().remove(hoteles);
                plazaDisponibleFechaDeRefencia1Old = em.merge(plazaDisponibleFechaDeRefencia1Old);
            }
            if (plazaDisponibleFechaDeRefencia1New != null && !plazaDisponibleFechaDeRefencia1New.equals(plazaDisponibleFechaDeRefencia1Old)) {
                plazaDisponibleFechaDeRefencia1New.getHotelesList().add(hoteles);
                plazaDisponibleFechaDeRefencia1New = em.merge(plazaDisponibleFechaDeRefencia1New);
            }
            if (facturaclienteNew != null && !facturaclienteNew.equals(facturaclienteOld)) {
                Hoteles oldHotelesOfFacturacliente = facturaclienteNew.getHoteles();
                if (oldHotelesOfFacturacliente != null) {
                    oldHotelesOfFacturacliente.setFacturacliente(null);
                    oldHotelesOfFacturacliente = em.merge(oldHotelesOfFacturacliente);
                }
                facturaclienteNew.setHoteles(hoteles);
                facturaclienteNew = em.merge(facturaclienteNew);
            }
            for (Regimenhospedaje regimenhospedajeListNewRegimenhospedaje : regimenhospedajeListNew) {
                if (!regimenhospedajeListOld.contains(regimenhospedajeListNewRegimenhospedaje)) {
                    Hoteles oldHotelesOfRegimenhospedajeListNewRegimenhospedaje = regimenhospedajeListNewRegimenhospedaje.getHoteles();
                    regimenhospedajeListNewRegimenhospedaje.setHoteles(hoteles);
                    regimenhospedajeListNewRegimenhospedaje = em.merge(regimenhospedajeListNewRegimenhospedaje);
                    if (oldHotelesOfRegimenhospedajeListNewRegimenhospedaje != null && !oldHotelesOfRegimenhospedajeListNewRegimenhospedaje.equals(hoteles)) {
                        oldHotelesOfRegimenhospedajeListNewRegimenhospedaje.getRegimenhospedajeList().remove(regimenhospedajeListNewRegimenhospedaje);
                        oldHotelesOfRegimenhospedajeListNewRegimenhospedaje = em.merge(oldHotelesOfRegimenhospedajeListNewRegimenhospedaje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                HotelesPK id = hoteles.getHotelesPK();
                if (findHoteles(id) == null) {
                    throw new NonexistentEntityException("The hoteles with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(HotelesPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hoteles hoteles;
            try {
                hoteles = em.getReference(Hoteles.class, id);
                hoteles.getHotelesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hoteles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Facturacliente facturaclienteOrphanCheck = hoteles.getFacturacliente();
            if (facturaclienteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hoteles (" + hoteles + ") cannot be destroyed since the Facturacliente " + facturaclienteOrphanCheck + " in its facturacliente field has a non-nullable hoteles field.");
            }
            List<Regimenhospedaje> regimenhospedajeListOrphanCheck = hoteles.getRegimenhospedajeList();
            for (Regimenhospedaje regimenhospedajeListOrphanCheckRegimenhospedaje : regimenhospedajeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hoteles (" + hoteles + ") cannot be destroyed since the Regimenhospedaje " + regimenhospedajeListOrphanCheckRegimenhospedaje + " in its regimenhospedajeList field has a non-nullable hoteles field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Plazadisponible plazaDisponibleFechaDeRefencia1 = hoteles.getPlazaDisponibleFechaDeRefencia1();
            if (plazaDisponibleFechaDeRefencia1 != null) {
                plazaDisponibleFechaDeRefencia1.getHotelesList().remove(hoteles);
                plazaDisponibleFechaDeRefencia1 = em.merge(plazaDisponibleFechaDeRefencia1);
            }
            em.remove(hoteles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hoteles> findHotelesEntities() {
        return findHotelesEntities(true, -1, -1);
    }

    public List<Hoteles> findHotelesEntities(int maxResults, int firstResult) {
        return findHotelesEntities(false, maxResults, firstResult);
    }

    private List<Hoteles> findHotelesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hoteles.class));
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

    public Hoteles findHoteles(HotelesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hoteles.class, id);
        } finally {
            em.close();
        }
    }

    public int getHotelesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hoteles> rt = cq.from(Hoteles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
