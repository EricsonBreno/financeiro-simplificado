package com.financeirosimplificado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.financeirosimplificado.domain.user.User;
import com.financeirosimplificado.dto.NotificationDTO;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {

        String email = user.getEmail();

        NotificationDTO notification = new NotificationDTO(email, message);

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notification, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK ) {
            throw new Exception("Error sending notification");
        }


    }

}
