using Application.DaoInterfaces;
using Domain.DTOs;

namespace Grpc.DAOs;

public class InterestDao : IInterestDAO
{
    private readonly IGrpcClient grpcClient;
    public InterestDao(IGrpcClient grpcClient)
    {
        this.grpcClient = grpcClient;
    }
    public async Task<bool> CreditInterest(CreditInterestDTO dto)
    {
        return await grpcClient.CreditInterest(dto);
    }

    public async Task<DateTime?> CheckInterest(InterestCheckDTO dto)
    {
        DateTime? dateTime = await grpcClient.CheckInterest(dto);
        return dateTime;
    }
    
    public async Task<double> GetBalanceByAccountNumber(InterestCheckDTO interestCheckDto)
    {
        GetBalanceDTO getBalanceDto = new GetBalanceDTO()
        {
            AccountId = interestCheckDto.AccountID
        };
        double balance = await grpcClient.GetBalanceByAccountNumber(getBalanceDto);
        return balance;
    }

    public async Task<double> GetInterestRateByAccountNumber(InterestCheckDTO interestCheckDto)
    {
        GetBalanceDTO getBalanceDto = new GetBalanceDTO()
        {
            AccountId = interestCheckDto.AccountID
        };
        double interestRate = await grpcClient.GetInterestRateByAccountNumber(getBalanceDto);
        return interestRate;
    }
}