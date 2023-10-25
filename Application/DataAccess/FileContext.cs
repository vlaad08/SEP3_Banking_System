using System.Text.Json;
using Domain.Models;

namespace DataAccess;

public class FileContext
{
    //not gonna do it dont think we need this might be stupid af
    //nvm i dont have a db but i need to do asyncs
    //view this as temporary
    
    private const string filePath = "data.json";
    private DataContainer? dataContainer;
    
    public ICollection<TransferInfo> Transfers
    {
        get
        {
            LoadData();
            return dataContainer!.Transfers;
        }
    }
    
    public ICollection<User> Users
    {
        get
        {
            LoadData();
            return dataContainer!.Users;
        }
    }
    
    private void LoadData()
    {
        if (dataContainer != null) return;
        
        if (!File.Exists(filePath))
        {
            dataContainer = new ()
            {
                Users = new List<User>(),
                Transfers = new List<TransferInfo>(),
            };
            return;
        }
        string content = File.ReadAllText(filePath);
        dataContainer = JsonSerializer.Deserialize<DataContainer>(content);
    }
    
    public void SaveChanges()
    {
        string serialized = JsonSerializer.Serialize(dataContainer, new JsonSerializerOptions
        {
            WriteIndented = true
        });
        File.WriteAllText(filePath, serialized);
        dataContainer = null;
    }
    
    //it was practically copy paste something should get fucked later ig
}