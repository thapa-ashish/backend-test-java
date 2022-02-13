package app.repository;

import app.entity.Fruit;

import javax.persistence.EntityManager;

public class FruitRepository {

    private final EntityManager entityManager;

    public FruitRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Fruit getFruitByName(String name) {
        return entityManager.find(Fruit.class, name);
    }

}
