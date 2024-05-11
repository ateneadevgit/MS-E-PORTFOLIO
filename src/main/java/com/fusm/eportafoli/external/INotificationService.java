package com.fusm.eportafoli.external;

import com.fusm.eportafoli.model.external.NotificationRequest;
import com.fusm.eportafoli.model.external.Template;
import org.springframework.stereotype.Service;

@Service
public interface INotificationService {

    String sendNotification(NotificationRequest notificationRequest);
    Template getTemplate(Integer templateId);

}
