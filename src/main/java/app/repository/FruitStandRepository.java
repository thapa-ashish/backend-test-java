package app.repository;

import app.entity.Fruit;
import app.entity.FruitStand;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitStandRepository {

    private final EntityManager entityManager;

    public FruitStandRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public FruitStand getFruitsByStandOrder(Integer order) {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("order", order);
        return entityManager.find(FruitStand.class, properties);
    }

    public List<FruitStand> getAllFruitStands() {
        return entityManager.createQuery("select fs from FruitStand fs", FruitStand.class).getResultList();
    }

    public FruitStand getFruitByNameAtStand(String fruitName, Integer order) {
        List<FruitStand> fruitStands = entityManager.createQuery("select fs from FruitStand fs where fs.name = :name and order = :order")
                .setParameter("name", fruitName)
                .setParameter("order", order)
                .getResultList();

        return fruitStands.size() > 0 ? fruitStands.get(0) : null;
    }
}
