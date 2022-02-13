package app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FruitStandRequest {

    private Integer order;
    private String name;
    @JsonProperty("fruit")
    private FruitRequest fruitRequest;
    private Integer quantity;
    private String unit;
    @JsonProperty("cost_per_unit")
    private Float costPerUnit;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FruitRequest getFruitRequest() {
        return fruitRequest;
    }

    public void setFruitRequest(FruitRequest fruitRequest) {
        this.fruitRequest = fruitRequest;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(Float costPerUnit) {
        this.costPerUnit = costPerUnit;
    }
}
