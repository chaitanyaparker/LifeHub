package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Notification.NotificationEvent;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

//    @KafkaListener(
//            topics = "notification-topic",
//            groupId = "notification-service",
//            containerFactory = "kafkaListenerContainerFactory"   // use your custom factory
//    )
@KafkaListener(
        topics = "notification-topic",
        groupId = "notification-service",
        containerFactory = "kafkaListenerContainerFactory",
        autoStartup = "false"
)
    public void consume(NotificationEvent event, Acknowledgment ack) {

        try {
            User user = userRepository.findById(event.getUserId())
                    .orElseThrow(() ->
                            new RuntimeException("User not found: " + event.getUserId()));

            notificationService.createNotification(
                    user,
                    event.getTitle(),
                    event.getMessage(),
                    event.getType()
            );

            ack.acknowledge();   // commit offset only after successful processing
            log.info("Notification processed for userId={}", event.getUserId());

        } catch (Exception e) {
            log.error("Failed to process notification event: {}", event, e);
            // do NOT acknowledge → message can be retried
            throw e;   // or handle with a Dead Letter Topic later
        }
    }
}