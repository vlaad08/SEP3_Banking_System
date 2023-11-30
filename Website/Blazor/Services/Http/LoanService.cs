using System.Text;
using System.Text.Json;
using Shared.DTOs;

namespace Blazor.Services.Http;

public class LoanService : ILoanService
{
    private readonly HttpClient client = new ();
    
    public async Task<string> LoanCalculation(double Principle, int Tenure)
    {
        LoanDto loan = new LoanDto()
        {
            Principle = Principle,
            Tenure = Tenure
        };

        try
        {
            string loanJson = JsonSerializer.Serialize(loan);
            Console.WriteLine(loanJson);
            StringContent content = new(loanJson, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await client.PostAsync("http://localhost:5054/api/Transaction/Loan/calculation", content);
            string responseBody = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }

            return responseBody;
        }
        catch (Exception e)
        { 
            throw new Exception($"Transfer failed: {e.Message}");
        }
    }
}