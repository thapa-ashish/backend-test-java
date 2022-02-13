package app.service;


import app.entity.FruitStand;
import app.repository.FruitStandRepository;

import java.util.List;


public class FruitStandService {

    private FruitStandRepository fruitStandRepository;

    public FruitStandService(FruitStandRepository fruitStandRepository) {
        this.fruitStandRepository = fruitStandRepository;
    }

    public FruitStand getFruitsByStand(Integer order) {
        return fruitStandRepository.getFruitsByStandOrder(order);
    }

    public List<FruitStand> getAllFruitStand() {
        return fruitStandRepository.getAllFruitStands();
    }

    public FruitStand getFruitByNameAtStand(String fruitName, Integer order) {
        return fruitStandRepository.getFruitByNameAtStand(fruitName, order);
    }

}
