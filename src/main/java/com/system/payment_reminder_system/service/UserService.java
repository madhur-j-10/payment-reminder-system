package com.system.payment_reminder_system.service;


import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);
}
