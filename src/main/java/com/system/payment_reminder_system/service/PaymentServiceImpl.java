package com.system.payment_reminder_system.service;

import com.system.payment_reminder_system.entity.Payment;
import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.model.PaymentModel;
import com.system.payment_reminder_system.model.PaymentStatus;
import com.system.payment_reminder_system.repository.PaymentRepository;
import com.system.payment_reminder_system.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private EmailService emailService;

    @Override
    public void createPayment(PaymentModel paymentModel) {

        Payment payment = new Payment();

        payment.setPaymentName(paymentModel.getPaymentName());
        payment.setUserId(paymentModel.getUserId());
        payment.setDescription(paymentModel.getDescription());
        payment.setAmount(paymentModel.getAmount());
        payment.setDeadline(paymentModel.getDeadline());
        payment.setCategory(paymentModel.getCategory());

        paymentRepository.save(payment);

    }

    @Override
    public List<Payment> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Override
    public void updateStatus(Long id, PaymentStatus status, Long userId) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment Not Found!!"));

        //optional double authentication
        if(!payment.getUserId().equals(userId)){
            throw new RuntimeException("UNAUTHORIZED!!");
        }

        payment.setStatus(status);
        paymentRepository.save(payment);

    }

    @Override
    public void deletePayment(Long id, Long userId) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment Not Found!!"));

        //optional double authentication
        if(!payment.getUserId().equals(userId)){
            throw new RuntimeException("UNAUTHORIZED!!");
        }

        paymentRepository.deleteById(id);
    }

    @Override
    public void sendPaymentReminder() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfTomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime startOfDayAfterTomorrow = LocalDate.now().plusDays(2).atStartOfDay();
        LocalDateTime startOfThreeDaysLater = LocalDate.now().plusDays(3).atStartOfDay();

        List<Payment> upcoming = paymentRepository.findByDeadlineBetween(startOfDayAfterTomorrow,startOfThreeDaysLater);
        List<Payment> dueToday = paymentRepository.findByDeadlineBetween(today,startOfTomorrow);
        //retrieves all Payment records where the dueDate field is before the given today date and time
        List<Payment> overDue = paymentRepository.findByDeadlineBefore(today);


//        for(Payment payment : upcoming){
//            if(payment.getStatus() == PaymentStatus.PENDING){
//                sendReminder(payment, "upcoming");
//            }
//        }
        //OR
        upcoming.stream()
                .filter(payment -> payment.getStatus() == PaymentStatus.PENDING) //lemdah function
                .forEach(payment -> sendReminder(payment,"upcoming"));

        dueToday.stream()
                .filter(payment -> payment.getStatus() == PaymentStatus.PENDING)
                .forEach(payment -> sendReminder(payment,"dueToday"));
        overDue
                .forEach(payment -> sendReminder(payment,"upcoming"));


    }

    private void sendReminder(Payment payment, String type) {


        User user = userRepository.findById(payment.getUserId())
                .orElseThrow(() -> new RuntimeException("user not found!!"));

        Context context = new Context();

        context.setVariable("name", user.getUsername());
        context.setVariable("amount", payment.getAmount());
        context.setVariable("dueDate", payment.getDeadline());
        context.setVariable("category", payment.getCategory());

        String message;
        switch (type) {
            case "upcoming":
                message = "This is a friendly reminder: your payment is due in 2 days.";
                break;
            case "dueToday":
                message = "Your payment is due today!";
                break;
            case "overdue":
                message = "Your payment is now overdue. Please take action.";
                break;
            default:
                message = "";
        }

        context.setVariable("message", message);


        String url = buildDynamicUrl();


//        System.out.println("***************");
//        System.out.println(url);
//        System.out.println("***************");

        context.setVariable("url", url);

        //templateEngine provided by Thymleaf(autowired)
        String htmlContent = templateEngine.process("emailTemplate.html", context);

        emailService.sendHtmlEmail(user.getEmail(), "Payment Reminder", htmlContent);

    }

    public String buildDynamicUrl() {

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) {
            throw new IllegalStateException("No request context available");
        }

        HttpServletRequest request = attrs.getRequest();

        String scheme = request.getScheme();         // http or https
        String serverName = request.getServerName(); // domain or IP
        int port = request.getServerPort();          // port

        return scheme + "://" + serverName + ":" + port + "/auth/register";
    }
}










