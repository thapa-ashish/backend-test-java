package app.entity;


import javax.persistence.*;

@Entity
@Table(name = "fruit", schema = "PUBLIC")
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;


    protected Fruit() {

    }

    public Fruit(String name) {
        this.name = name;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}
