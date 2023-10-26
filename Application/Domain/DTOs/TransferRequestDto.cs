using System.Runtime.Serialization;

namespace Domain.DTOs
{
    public class TransferRequestDTO
    {
        public decimal Amount { get; set; }
        public string RecipientName { get; set; }
        public int RecipientAccountNumber { get; set; }
        public string Message { get; set; }
        public int SenderAccountNumber { get; set; }
    }
}
