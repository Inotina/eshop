package by.enot.eshop.entity;

import javax.persistence.*;

@Entity
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Product_Id")
    private Product product;

    @Column(name = "Count")
    private long count;

    @Column(name = "Price")
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Purchase_num")
    private Purchase purchaseOwner;

    public PurchaseItem(Product product, long count, double price, Purchase purchaseId) {
        this.product = product;
        this.count = count;
        this.price = price;
        this.purchaseOwner = purchaseId;
    }

    public PurchaseItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Purchase getPurchaseOwner() {
        return purchaseOwner;
    }

    public void setPurchaseOwner(Purchase purchaseId) {
        this.purchaseOwner = purchaseId;
    }

}
