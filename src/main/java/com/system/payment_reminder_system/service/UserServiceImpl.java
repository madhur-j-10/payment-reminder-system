package com.system.payment_reminder_system.service;


import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.events.RegistrationCompleteEvent;
import com.system.payment_reminder_system.model.UserModel;
import com.system.payment_reminder_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public User registerUser(UserModel userModel) {

        User user = new User();
        user.setUserName(userModel.getUserName());
        user.setUserEmail(userModel.getUserEmail());

        userRepository.save(user);

        System.out.println("\n******* user saved User Service*********\n");


        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(user));

        return user;
    }
}
