using Application.DaoInterfaces;
using Application.Logic;
using Application.LogicInterfaces;
using DataAccess.DAOs;
using Grpc;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddScoped<ITransferLogic, TransferLogic>();
builder.Services.AddScoped<ITransferDAO, TransferDAO>();
builder.Services.AddScoped<IGrpcClient, ProtoClient>();
builder.Services.AddScoped<IDepositLogic, DepositLogic>();
builder.Services.AddScoped<IDepositDAO, DepositDao>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseCors(x => x
    .AllowAnyMethod()
    .AllowAnyHeader()
    .SetIsOriginAllowed(origin => true) // allow any origin
    .AllowCredentials());


app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();