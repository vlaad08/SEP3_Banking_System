namespace Application.Server;

using System;
using System.Net;
using System.Net.Sockets;
using System.Text;

class TCPServer
{
    static void Main()
    {
        string ipAddress = "127.0.0.1"; 
        int port = 8080; 
        
        IPEndPoint endPoint = new IPEndPoint(IPAddress.Parse(ipAddress), port);
        
        TcpListener listener = new TcpListener(endPoint);

        try
        {
            listener.Start();
            Console.WriteLine("Server is listening on {0}:{1}", ipAddress, port);

            while (true)
            {
                Console.WriteLine("Waiting for a client to connect...");
                
                TcpClient client = listener.AcceptTcpClient();
                Console.WriteLine("Client connected: {0}", client.Client.RemoteEndPoint);

                // Handle client communication on a separate thread
                System.Threading.Tasks.Task.Factory.StartNew(() =>
                {
                    // Get the network stream for reading and writing
                    NetworkStream stream = client.GetStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = stream.Read(buffer, 0, buffer.Length)) > 0)
                    {
                        // Convert the received data to a string
                        string data = Encoding.ASCII.GetString(buffer, 0, bytesRead);
                        Console.WriteLine("Received: {0}", data);

                        // Process the data (you can replace this with your own logic)
                        string response = "Server: " + data.ToUpper(); //IMPLEMENT LOGIC

                        // Send a response back to the client
                        byte[] responseData = Encoding.ASCII.GetBytes(response); //IMPLEMENT RESPONSE
                        stream.Write(responseData, 0, responseData.Length);
                    }

                    // Close the client connection when done
                    client.Close();
                });
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error: " + ex.Message);
        }
        finally
        {
            // Stop listening and close the listener when done
            listener.Stop();
        }
    }
}