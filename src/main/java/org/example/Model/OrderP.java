package org.example.Model;

/** @author Mihai Fleser
 * Models the Order table from the Database. It has only getters and setters.
 */

public class OrderP {
    private Integer id;
    private Integer clientId;
    private Integer productId;
    private Integer quantity;

    public OrderP()
    {

    }

    public OrderP(Integer id, Integer clientId, Integer productId, Integer quantity)
    {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderP(Integer clientId, Integer productId, Integer quantity)
    {
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
