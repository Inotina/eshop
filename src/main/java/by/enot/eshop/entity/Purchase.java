package by.enot.eshop.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PurchaseID")
    private long id;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Date")
    private String date;

    @Column(name = "Adress")
    private String adress;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ClientId")
    private User client;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "purchaseOwner")
    private List<PurchaseItem> products;

    public Purchase(){

    }

    public Purchase(String phone, String date, String adress, User client, List<PurchaseItem> products) {
        this.phone = phone;
        this.date = date;
        this.adress = adress;
        this.client = client;
        this.products = products;
    }

    public Purchase(Purchase purchase) {
        this.phone = purchase.getPhone();
        this.date = purchase.getDate();
        this.adress = purchase.getAdress();
        this.client = purchase.getClient();
        this.products = purchase.getProducts();
        this.id = purchase.getId();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public List<PurchaseItem> getProducts() {
        return products;
    }

    public void setProducts(List<PurchaseItem> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", date='" + date + '\'' +
                ", adress='" + adress + '\'' +
                ", clientId=" + client +
                ", products='" + products + '\'' +
                '}';
    }
}
