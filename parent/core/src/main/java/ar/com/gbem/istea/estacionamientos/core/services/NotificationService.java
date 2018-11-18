package ar.com.gbem.istea.estacionamientos.core.services;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class NotificationService {

	public final void send(final String title, final String body, final String deviceToken) {
		if (deviceToken == null) {
			// no se puede notificar sin saber a quién
			return;
		}

		final Notification notification = new Notification(title, body);
		final Message message = Message.builder().setToken(deviceToken).setNotification(notification).build();

		FirebaseMessaging.getInstance(FirebaseComponent.app()).sendAsync(message);
	}

}
