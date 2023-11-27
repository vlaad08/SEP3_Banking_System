﻿using Domain.Models;
using Shared.DTOs;

namespace Application.DaoInterfaces;

public interface IUserLoginDao
{
    Task<List<User>> GetAllUserDataForValidation();
}