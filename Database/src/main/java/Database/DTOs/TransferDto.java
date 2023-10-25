package Database.DTOs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TransferDto implements Serializable {
    private String id_1;
    private String id_2;
    private double amount;

    @JsonCreator
    public TransferDto(@JsonProperty("id_1") String id_1, @JsonProperty("id_2")String id_2, @JsonProperty("amount")double amount)
    {
        this.id_1 = id_1;
        this.id_2 = id_2;
        this.amount = amount;
    }
    @JsonGetter("id_1")
    public String getId_1() {
        return id_1;
    }
    @JsonGetter("id_2")
    public String getId_2() {
        return id_2;
    }
    @JsonGetter("amount")
    public double getAmount() {
        return amount;
    }

    public void setId_1(String id_1) {
        this.id_1 = id_1;
    }
    public void setId_2(String id_2) {
        this.id_2 = id_2;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
