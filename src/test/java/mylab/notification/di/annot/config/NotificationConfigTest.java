package mylab.notification.di.annot.config;

import mylab.notification.di.annot.EmailNotificationService;
import mylab.notification.di.annot.NotificationManager;
import mylab.notification.di.annot.SmsNotificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = NotificationConfig.class)
class NotificationConfigTest {

    @Autowired
    private NotificationManager notificationManager;

    @Test
    @DisplayName("Java Config DI 및 기능 검증")
    void testNotificationManager() {
        assertNotNull(notificationManager);

        assertNotNull(notificationManager.getEmailService());
        EmailNotificationService email = (EmailNotificationService) notificationManager.getEmailService();
        assertEquals("smtp.gmail.com", email.getSmtpServer());
        assertEquals(587, email.getPort());

        assertNotNull(notificationManager.getSmsService());
        SmsNotificationService sms = (SmsNotificationService) notificationManager.getSmsService();
        assertEquals("SKT", sms.getProvider());

        notificationManager.sendNotificationByEmail("테스트 이메일");
        notificationManager.sendNotificationBySms("테스트 SMS");
    }
}
