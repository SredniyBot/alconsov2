package com.pavi.alconsov2.service;

import com.pavi.alconsov2.entity.ResultInfo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationService implements ResultObserver{

    private final SimpMessagingTemplate brokerMessagingTemplate;

    public UserNotificationService(SimpMessagingTemplate brokerMessagingTemplate) {
        this.brokerMessagingTemplate = brokerMessagingTemplate;
    }

    @Async
    public void notifyUser(ResultInfo resultInfo){
        brokerMessagingTemplate.convertAndSend("/topic/status",resultInfo);
    }


    @Override
    public void update(ResultInfo resultInfo) {
        notifyUser(resultInfo);
    }
}
