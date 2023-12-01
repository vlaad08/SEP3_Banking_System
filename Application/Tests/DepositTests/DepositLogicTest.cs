using Application.DaoInterfaces;
using Application.Logic;
using Domain.DTOs;
using Moq;

namespace Tests.DepositTests;

public class DepositLogicTest
{
    [Fact]
    public async void DepositMoney_Calls_For_DepositDao()
    {
        DepositRequestDTO dto = new DepositRequestDTO
        {
            Amount = 10,
            ToppedUpAccountNumber = "1111111111111111"
        };
        var dao = new Mock<IDepositDAO>();
        var depositLogic = new DepositLogic(dao.Object);

        await depositLogic.DepositMoney(dto);
        
        dao.Verify(d => d.DepositMoney(dto));
    }
}