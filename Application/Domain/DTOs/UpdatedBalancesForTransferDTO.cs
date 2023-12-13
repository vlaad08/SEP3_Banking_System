namespace Domain.DTOs;

public class UpdatedBalancesForTransferDTO
{
    public double newSenderBalance { get; set; }
    public double newReceiverBalance { get; set; }
    public string Message { get; set; }
    public string senderId { get; set; }
    public string receiverId { set; get; }
    public double amount { set; get; }
}