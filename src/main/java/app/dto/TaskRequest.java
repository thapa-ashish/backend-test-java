package app.dto;

import app.entity.Fruit;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TaskRequest {

    @JsonProperty("fruits")
    private List<FruitRequest> fruits;

    public List<FruitRequest> getFruits() {
        return fruits;
    }

    public void setFruits(List<FruitRequest> fruits) {
        this.fruits = fruits;
    }
}
