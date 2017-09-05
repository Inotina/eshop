package by.enot.eshop.entity;

import javax.persistence.*;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PurchaseID")
    private int id;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Date")
    private String date;

    @Column(name = "Adress")
    private String adress;

    @Column(name = "ClientId")
    private int clientId;

    @Column(name = "Products")
    private String products;

    public Purchase(){

    }

    public Purchase(String phone, String date, String adress, int clientID, String products) {
        this.phone = phone;
        this.date = date;
        this.adress = adress;
        this.clientId = clientID;
        this.products = products;
    }

    public Purchase(Purchase purchase) {
        this.phone = purchase.getPhone();
        this.date = purchase.getDate();
        this.adress = purchase.getAdress();
        this.clientId = purchase.getClientId();
        this.products = purchase.getProducts();
        this.id = purchase.getId();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientID) {
        this.clientId = clientID;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", date='" + date + '\'' +
                ", adress='" + adress + '\'' +
                ", clientId=" + clientId +
                ", products='" + products + '\'' +
                '}';
    }
}
