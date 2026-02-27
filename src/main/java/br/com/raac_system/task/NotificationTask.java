package br.com.raac_system.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationTask {

    // Cron expression: At 08:00 AM every day
    @Scheduled(cron = "0 0 8 * * *")
    public void notifyAppointmentReminder() {
        // Query tomorrow's appointments and send email / WhatsApp
        System.out.println("Running notifyAppointmentReminder task...");
    }
}
