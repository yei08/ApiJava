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
import co.edu.udec.poo.entidades.Plazadisponible;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class PlazadisponibleJpaController implements Serializable {

    public PlazadisponibleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Plazadisponible plazadisponible) throws PreexistingEntityException, Exception {
        if (plazadisponible.getHotelesList() == null) {
            plazadisponible.setHotelesList(new ArrayList<Hoteles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Hoteles> attachedHotelesList = new ArrayList<Hoteles>();
            for (Hoteles hotelesListHotelesToAttach : plazadisponible.getHotelesList()) {
                hotelesListHotelesToAttach = em.getReference(hotelesListHotelesToAttach.getClass(), hotelesListHotelesToAttach.getHotelesPK());
                attachedHotelesList.add(hotelesListHotelesToAttach);
            }
            plazadisponible.setHotelesList(attachedHotelesList);
            em.persist(plazadisponible);
            for (Hoteles hotelesListHoteles : plazadisponible.getHotelesList()) {
                Plazadisponible oldPlazaDisponibleFechaDeRefencia1OfHotelesListHoteles = hotelesListHoteles.getPlazaDisponibleFechaDeRefencia1();
                hotelesListHoteles.setPlazaDisponibleFechaDeRefencia1(plazadisponible);
                hotelesListHoteles = em.merge(hotelesListHoteles);
                if (oldPlazaDisponibleFechaDeRefencia1OfHotelesListHoteles != null) {
                    oldPlazaDisponibleFechaDeRefencia1OfHotelesListHoteles.getHotelesList().remove(hotelesListHoteles);
                    oldPlazaDisponibleFechaDeRefencia1OfHotelesListHoteles = em.merge(oldPlazaDisponibleFechaDeRefencia1OfHotelesListHoteles);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlazadisponible(plazadisponible.getFechaDeRefencia()) != null) {
                throw new PreexistingEntityException("Plazadisponible " + plazadisponible + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Plazadisponible plazadisponible) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plazadisponible persistentPlazadisponible = em.find(Plazadisponible.class, plazadisponible.getFechaDeRefencia());
            List<Hoteles> hotelesListOld = persistentPlazadisponible.getHotelesList();
            List<Hoteles> hotelesListNew = plazadisponible.getHotelesList();
            List<String> illegalOrphanMessages = null;
            for (Hoteles hotelesListOldHoteles : hotelesListOld) {
                if (!hotelesListNew.contains(hotelesListOldHoteles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hoteles " + hotelesListOldHoteles + " since its plazaDisponibleFechaDeRefencia1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Hoteles> attachedHotelesListNew = new ArrayList<Hoteles>();
            for (Hoteles hotelesListNewHotelesToAttach : hotelesListNew) {
                hotelesListNewHotelesToAttach = em.getReference(hotelesListNewHotelesToAttach.getClass(), hotelesListNewHotelesToAttach.getHotelesPK());
                attachedHotelesListNew.add(hotelesListNewHotelesToAttach);
            }
            hotelesListNew = attachedHotelesListNew;
            plazadisponible.setHotelesList(hotelesListNew);
            plazadisponible = em.merge(plazadisponible);
            for (Hoteles hotelesListNewHoteles : hotelesListNew) {
                if (!hotelesListOld.contains(hotelesListNewHoteles)) {
                    Plazadisponible oldPlazaDisponibleFechaDeRefencia1OfHotelesListNewHoteles = hotelesListNewHoteles.getPlazaDisponibleFechaDeRefencia1();
                    hotelesListNewHoteles.setPlazaDisponibleFechaDeRefencia1(plazadisponible);
                    hotelesListNewHoteles = em.merge(hotelesListNewHoteles);
                    if (oldPlazaDisponibleFechaDeRefencia1OfHotelesListNewHoteles != null && !oldPlazaDisponibleFechaDeRefencia1OfHotelesListNewHoteles.equals(plazadisponible)) {
                        oldPlazaDisponibleFechaDeRefencia1OfHotelesListNewHoteles.getHotelesList().remove(hotelesListNewHoteles);
                        oldPlazaDisponibleFechaDeRefencia1OfHotelesListNewHoteles = em.merge(oldPlazaDisponibleFechaDeRefencia1OfHotelesListNewHoteles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Date id = plazadisponible.getFechaDeRefencia();
                if (findPlazadisponible(id) == null) {
                    throw new NonexistentEntityException("The plazadisponible with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Date id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plazadisponible plazadisponible;
            try {
                plazadisponible = em.getReference(Plazadisponible.class, id);
                plazadisponible.getFechaDeRefencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plazadisponible with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Hoteles> hotelesListOrphanCheck = plazadisponible.getHotelesList();
            for (Hoteles hotelesListOrphanCheckHoteles : hotelesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Plazadisponible (" + plazadisponible + ") cannot be destroyed since the Hoteles " + hotelesListOrphanCheckHoteles + " in its hotelesList field has a non-nullable plazaDisponibleFechaDeRefencia1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(plazadisponible);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Plazadisponible> findPlazadisponibleEntities() {
        return findPlazadisponibleEntities(true, -1, -1);
    }

    public List<Plazadisponible> findPlazadisponibleEntities(int maxResults, int firstResult) {
        return findPlazadisponibleEntities(false, maxResults, firstResult);
    }

    private List<Plazadisponible> findPlazadisponibleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Plazadisponible.class));
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

    public Plazadisponible findPlazadisponible(Date id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Plazadisponible.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlazadisponibleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Plazadisponible> rt = cq.from(Plazadisponible.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
