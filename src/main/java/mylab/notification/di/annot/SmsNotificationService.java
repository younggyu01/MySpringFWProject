package mylab.notification.di.annot;

public class SmsNotificationService implements NotificationService {
    private final String provider;

    public SmsNotificationService(String provider) {
        this.provider = provider;
    }

    public String getProvider() { return provider; }

    @Override
    public void sendNotification(String message) {
        System.out.println("SMS 알림 전송: " + message + " (제공업체: " + provider + ")");
    }
}