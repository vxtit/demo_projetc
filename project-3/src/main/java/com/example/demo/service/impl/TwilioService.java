package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.service.UserService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
//@Value("${twilio.accountSid}")
//@Value("${twilio.authToken}")
//@Value("${twilio.phoneNumber}")
@Service
public class TwilioService {
    
	@Value("${twilio.accountSid}")
    private String TWILIO_ACCOUNT_SID;

    @Value("${twilio.authToken}")
    private String TWILIO_AUTH_TOKEN;

    @Value("${twilio.phoneNumber}")
    private String TWILIO_PHONE_NUMBER;

    public void sendSMS(String phoneNumber, String messageBody) {
        // Kiểm tra messageBody có null hoặc rỗng không
        if (messageBody == null || messageBody.isEmpty()) {
            throw new IllegalArgumentException("Message body is required.");
        }

        // Tiếp tục gửi tin nhắn nếu messageBody hợp lệ
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                messageBody
        ).create();

        System.out.println(message.getSid()); // In case you want to print the message SID
    }
}
