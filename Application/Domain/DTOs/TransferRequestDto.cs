using System.Runtime.Serialization;

namespace Domain.DTOs
{
    public class TransferRequestDTO
    {
        public double Amount { get; set; }
        public string RecipientAccountNumber { get; set; }
        public string Message { get; set; }
        public string SenderAccountNumber { get; set; }
    }
}
