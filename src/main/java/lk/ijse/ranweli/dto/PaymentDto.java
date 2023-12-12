package lk.ijse.ranweli.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;


public class PaymentDto {
    private String payId;
    private Double amount;
    private String status;
    private LocalDate date;
    private String method;
    private byte[] receipt;

    public PaymentDto() {}

    public PaymentDto(String payId, Double amount, String status, LocalDate date, String method, byte[] receipt) {
        this.payId = payId;
        this.amount = amount;
        this.status = status;
        this.date = date;
        this.method = method;
        this.receipt = receipt;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "payId='" + payId + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", date=" + date +
                ", method='" + method + '\'' +
                ", receipt=" + Arrays.toString(receipt) +
                '}';
    }
}
