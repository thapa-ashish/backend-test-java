package app.entity;

import javax.persistence.*;

public class FruitStand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "stand", unique = true, nullable = false)
    private String name;
    @Column(name = "order", nullable = false)
    private Integer order;
    @Column(name = "fruits", nullable = false)
    private String fruit;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "unit", nullable = false)
    private String unit;
    @Column(name = "cost_per_unit", nullable = false)
    private Float costPerUnit;

    public FruitStand(){

    }

    public FruitStand(String name,Integer order,String fruit,Integer quantity, String unit, Float costPerUnit){
        this.name =name;
        this.order=order;
        this.fruit =fruit;
        this.unit=unit;
        this.costPerUnit = costPerUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
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
