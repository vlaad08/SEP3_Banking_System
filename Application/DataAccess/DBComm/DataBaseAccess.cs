using System.Net.Http.Json;
using System.Text;
using System.Text.Json;
using Application.DaoInterfaces;
using Domain.DTOs;

namespace DataAccess.DBComm;

public class DataBaseAccess : IDataBaseAccess
{
    private HttpClient rest=new HttpClient();

    public DataBaseAccess()
    {
        rest.BaseAddress = new Uri("http://localhost:8080");
    }

    public async Task MakeTransfer(TransferRequestDTO request)
    {
        String json = JsonSerializer.Serialize(request);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        HttpResponseMessage message = await rest.PostAsync("transactions/",content);
        
        if (message.IsSuccessStatusCode)
        {
            // Request was successful, handle the response if needed
            string responseContent = await message.Content.ReadAsStringAsync();
            Console.WriteLine("Response Content: " + responseContent);
        }
        else
        {
            // Handle errors here
            Console.WriteLine("Error: " + message.StatusCode);
        }
    }
    
}