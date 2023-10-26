
namespace Client;

public class ClientTest
{
    HttpClient javaApiClient = new HttpClient("http://localhost:8080");
    string result = await javaApiClient.CallJavaApiEndpointAsync("transactions/1");
}