package app.service;

import app.entity.Fruit;
import app.repository.FruitRepository;

public class FruitService {

    private FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public Fruit getFruitByName(String name){
        return fruitRepository.getFruitByName(name);
    }
}
