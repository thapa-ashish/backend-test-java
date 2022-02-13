package app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FruitRequest {

    @JsonProperty("fruit_name")
    private String fruitName;

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }
}
