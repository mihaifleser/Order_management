package org.example.Model;

/** @author Mihai Fleser
 * Models the product table from the Database. It has only getters and setters
 */

public class Product {
    private Integer id;
    private String name;
    private Integer price;
    private Integer quantity;

    public Product()
    {

    }

    public Product(Integer id, String name, Integer price, Integer quantity)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String name, Integer price, Integer quantity)
    {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
