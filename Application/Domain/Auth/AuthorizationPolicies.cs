using Microsoft.Extensions.DependencyInjection;

namespace Domain.Auth;

public class AuthorizationPolicies
{
    public static void AddPolicies(IServiceCollection services)
    {
        services.AddAuthorizationCore(options =>
        {
            options.AddPolicy("MustBeCorrectUser", a => 
                a.RequireAuthenticatedUser().RequireClaim("Role", "Client", "Employee"));
        });
    }
}