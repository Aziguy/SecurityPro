/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author KENGNI Hippolyte
 */
@Stateless
public class UtilisateurFacade extends AbstractFacade<Utilisateur> implements UtilisateurFacadeLocal {

    @PersistenceContext(unitName = "SecurityPro-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    @Override
    public Integer nextId() {
        try {
            Query query = em.createNamedQuery("Utilisateur.nextId");
            return ((Integer) query.getSingleResult()) + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public Utilisateur findByLoginMdp(String login, String mdp) {
        try {
            Query query = em.createNamedQuery("Utilisateur.findByLoginMdp");
            query.setParameter("login", login).setParameter("mdp", mdp);
            return (Utilisateur) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Utilisateur> findByLogin(String login){
        try {
            Query query = em.createNamedQuery("Utilisateur.findByLogin");
            query.setParameter("login", login);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
