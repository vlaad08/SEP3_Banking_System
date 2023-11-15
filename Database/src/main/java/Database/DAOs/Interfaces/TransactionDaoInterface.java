package Database.DAOs.Interfaces;

import Database.DTOs.TransferRequestDTO;
import Database.TransferRequest;

public interface TransactionDaoInterface {
    void makeTransfer(TransferRequestDTO transferRequestDTO);
}
