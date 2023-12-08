package Database.DAOs;

import Database.DAOs.Interfaces.TransactionDaoInterface;
import Database.DTOs.*;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import Database.Transactions;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class TransactionDao implements TransactionDaoInterface {
    SQLConnectionInterface connection;
    {
        try {
            connection = SQLConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void makeTransfer(TransferRequestDTO transferRequestDTO) {
        connection.transfer(transferRequestDTO.getSenderAccount_id(), transferRequestDTO.getRecipientAccount_id(),
                transferRequestDTO.getAmount(), transferRequestDTO.getMessage());
    }

    @Override
    public String checkAccountId(CheckAccountDTO checkAccountIdDTO) throws SQLException {
        return connection.checkAccountId(checkAccountIdDTO.getRecipientAccount_id());
    }

    @Override
    public double checkBalance(CheckAccountDTO checkAccountDTO) throws SQLException {
        return connection.checkBalance(checkAccountDTO.getRecipientAccount_id());
    }

    @Override
    public double dailyCheck(CheckAccountDTO checkAccountDTO) throws SQLException {
        return connection.dailyCheck(checkAccountDTO.getRecipientAccount_id());
    }

    @Override
    public void makeDeposit(DepositRequestDTO depositRequestDTO) throws SQLException {
        connection.deposit(depositRequestDTO.getAccount_id(), depositRequestDTO.getAmount());
    }

    @Override
    public boolean creditInterest(UserInfoAccNumDTO userInfoAccNumDTO) throws SQLException {
        return connection.creditInterest(userInfoAccNumDTO);
    }

    @Override
    public Timestamp lastInterest(UserInfoAccNumDTO userInfoAccNumDTO) throws SQLException {
        return connection.lastInterest(userInfoAccNumDTO);
    }

    @Override
    public void logLoan(LoanRequestDTO loanRequestDTO) throws SQLException {
        connection.logLoan(loanRequestDTO);
    }

    @Override
    public List<Transactions> getAllTransactions(UserInfoEmailDTO userInfoEmailDTO) throws SQLException {
        return connection.getAllTransactions(userInfoEmailDTO);
    }

    @Override
    public List<Transactions> getAllTransactionsForEmployee() throws SQLException {
        return connection.getAllTransactionsForEmployee();
    }

    @Override
    public void flagUser(FlagUserDTO flagUserDTO) {
        connection.flagUser(flagUserDTO);
    }

    @Override
    public List<Transactions> getAllSubscriptions(
            UserInfoEmailDTO userInfoEmailDTO) throws SQLException {
        return connection.getAllSubscriptions(userInfoEmailDTO);
    }

}
