package main.java.model;

/**
 * Lớp đại diện cho đối tượng Khách Hàng.
 */
public class Customer {
    private int customerId;
    private String fullName;
    private String phone;
    private String email;
    private String memberType;

    public Customer(int customerId, String fullName, String phone, String email, String memberType) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.memberType = memberType;
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

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    @Override
    public String toString() {
        return "Khách hàng: " + fullName + " (" + memberType + ")";
    }
}
