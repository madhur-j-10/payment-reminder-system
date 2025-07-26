package com.system.payment_reminder_system.events;


import com.system.payment_reminder_system.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;


    //constructor
    public RegistrationCompleteEvent(User user) {
        super(user);
        this.user = user;
    }
}
