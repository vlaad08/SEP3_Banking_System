using Application.DaoInterfaces;
using Domain.DTOs;

namespace Grpc.DAOs;

public class LoanDao : ILoanDAO
{
    private readonly IGrpcClient grpcClient;
    public LoanDao(IGrpcClient grpcClient)
    {
        this.grpcClient = grpcClient;
    }

    public async Task RequestLoan(LoanRequestDTO dto)
    {
        await grpcClient.RequestLoan(dto);
    }

    public async Task<double> GetBalanceByAccountNumber(DepositRequestDTO dto)
    {
        GetBalanceDTO getBalanceDto = new GetBalanceDTO()
        {
            AccountId = dto.ToppedUpAccountNumber
        };
        double balance = await grpcClient.GetBalanceByAccountNumber(getBalanceDto);
        return balance;
    }
}