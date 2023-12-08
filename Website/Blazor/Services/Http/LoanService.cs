using System.Text;
using System.Text.Json;
using Shared.DTOs;

namespace Blazor.Services.Http;

public class LoanService : ILoanService
{
    private readonly HttpClient client = new ();
    
    public async Task<string> LoanCalculation(LoanRequestDto dto)
    {
        try
        {
            Console.WriteLine(dto.AccountNumber+" "+dto.Principal+" "+dto.Tenure);
            HttpResponseMessage response = await client.PostAsJsonAsync("http://localhost:5054/api/Transaction/Loan/calculation", dto);
            string responseBody = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }
            return responseBody;
        }
        catch (Exception e)
        { 
            throw new Exception($"Loan request failed: {e.Message}");
        }
    }

    public async Task RequestLoan(LoanRequestDto dto)
    {
        try
        {
            HttpResponseMessage response = await client.PostAsJsonAsync("http://localhost:5054/api/Transaction/Loan", dto);
            string responseBody = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.Message);
        }
    }
}