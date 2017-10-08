package br.com.spakoapi.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    
    private static EntityManagerFactory em;
    
    public static EntityManager getEntityManager(){
        if (em == null) {
            em = Persistence.createEntityManagerFactory("spakoUP");
        }
        return em.createEntityManager();
    }
}
