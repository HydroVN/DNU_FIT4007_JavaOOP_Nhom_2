package main.java.model;

public class Customer {
    private int customerId;
    private String fullName;
    private String phone;
    private String email;
    private int maLoaiTV;

    public Customer(int customerId, String fullName, String phone, String email, int maLoaiTV) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.maLoaiTV = maLoaiTV;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMaLoaiTV() {
        return maLoaiTV;
    }

    public void setMaLoaiTV(int maLoaiTV) {
        this.maLoaiTV = maLoaiTV;
    }

    @Override
    public String toString() {
        return "Khách hàng: " + fullName + " (Loại: " + maLoaiTV + ")";
    }
}