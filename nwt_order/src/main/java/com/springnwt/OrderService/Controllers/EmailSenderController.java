package com.springnwt.OrderService.Controllers;

import com.springnwt.OrderService.Model.Orders;
import com.springnwt.OrderService.Service.EmailService;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/mail")
public class EmailSenderController {
    private EmailService emailService;

    @GetMapping("")
    String sendMail(@RequestBody String to, @RequestBody String subject, @RequestBody String body) {
        emailService.sendEmail(to,subject, body);
        return body;
    }
}
