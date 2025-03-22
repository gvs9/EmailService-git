package com.example.emailservice.consumers;

import com.example.emailservice.dtos.EmailDto;
import com.example.emailservice.utils.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Service

public class SendEmailConsumer {
    @Autowired
    private ObjectMapper objectMapper;

@KafkaListener(topics= "signup",groupId = "emailService")





    public void sendEmail(String message) {
    System.out.println("Received Kafka message: " + message);
        try {
            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);
            System.out.println("Processing email for: " + emailDto.getTo());
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailDto.getFrom(), "bxrglomftfcdkvzy");
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, emailDto.getTo(),emailDto.getSubject(),emailDto.getBody());
            System.out.println("Email sent successfully!");
        }catch(JsonProcessingException exception){
            System.err.println("Error processing Kafka message: " + exception.getMessage());
            throw new RuntimeException(exception);
        }
    }
}
