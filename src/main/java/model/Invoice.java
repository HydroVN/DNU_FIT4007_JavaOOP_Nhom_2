package main.java.model;

import java.time.LocalDateTime;
import java.util.List;

public class Invoice {
    private int invoiceId;
    private int customerId;
    private LocalDateTime createdDate;
    private List<Ticket> tickets;

    public Invoice(int invoiceId, int customerId, LocalDateTime createdDate, List<Ticket> tickets) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.createdDate = createdDate;
        this.tickets = tickets;
    }

    public int getInvoiceId() {
        return invoiceId;
    }
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", customerId=" + customerId +
                ", createdDate=" + createdDate +
                ", tickets=" + (tickets != null ? tickets.size() : 0) +
                '}';
    }
}