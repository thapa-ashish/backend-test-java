package app.repository;

import app.entity.AppUser;

import javax.persistence.EntityManager;


public class AppUserRepository {

    private final EntityManager entityManager;

    public AppUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Obtain user by username from the embedded H2 database
     *
     * @param username the username for user
     * @return user
     */
    public AppUser getUserByUsername(String username) {
        return entityManager.find(AppUser.class, username);
    }

    public AppUser saveAppUser(AppUser user){
        return entityManager.merge(user);
    }
}
