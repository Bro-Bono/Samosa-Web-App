package com.brobono.samosawebapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.brobono.samosawebapp.models.Order;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender emailSender;
	
    @Autowired
    private TemplateEngine templateEngine;
	
    public void sendNewOrderEmail(String to, Order order) {
        Context context = new Context();
        context.setVariable("customerName", order.getCustomerName());
        context.setVariable("orderDetails", order.getOrderDetails());

        String processHtml = templateEngine.process("new-order-email", context);

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setText(processHtml, true); // true indicates HTML
            helper.setTo(to);
            helper.setSubject("Thank you for your order!");
            helper.setFrom("postmaster@sandboxbe8a1c2de468497c947cff2018c938a2.mailgun.org	");
            emailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
