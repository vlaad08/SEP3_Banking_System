package Database.DTOs;

import com.google.protobuf.Timestamp;

public class LoanRequestDTO {
    private String accountId;
    private double remainingAmount;
    private double interestRate;
    private double monthlyPayment;
    private Timestamp endDate;
    private double loanAmount;

    public LoanRequestDTO(String accountId, double remainingAmount, double interestRate, double monthlyPayment, Timestamp endDate, double loanAmount) {
        this.accountId = accountId;
        this.remainingAmount = remainingAmount;
        this.interestRate = interestRate;
        this.monthlyPayment = monthlyPayment;
        this.endDate = endDate;
        this.loanAmount = loanAmount;
    }

    public String getAccountId() {
        return accountId;
    }
    public double getRemainingAmount() {
        return remainingAmount;
    }
    public double getInterestRate() {
        return interestRate;
    }
    public double getMonthlyPayment() {
        return monthlyPayment;
    }
    public Timestamp getEndDate() {
        return endDate;
    }
    public double getLoanAmount() {
        return loanAmount;
    }

}
