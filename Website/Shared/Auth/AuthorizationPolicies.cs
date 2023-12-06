using Microsoft.Extensions.DependencyInjection;

namespace Shared.Auth;

public class AuthorizationPolicies
{
    public static void AddPolicies(IServiceCollection services)
    {
        services.AddAuthorizationCore(options =>
        {
            options.AddPolicy("MustBeCorrectUser", a => 
                a.RequireAuthenticatedUser().RequireClaim("Role", "Client", "Employee"));
            
            options.AddPolicy("MustBeEmployee",e=>e.RequireRole("Employee"));
            options.AddPolicy("MustBeClient",e=>e.RequireRole("Client"));
        });
    }
}