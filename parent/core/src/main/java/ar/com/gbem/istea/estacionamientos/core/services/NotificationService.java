package ar.com.gbem.istea.estacionamientos.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class NotificationService {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

	public final void send(final String title, final String body, final String deviceToken) {
		if (deviceToken == null) {
			// no se puede notificar sin saber a quién
			return;
		}
		new Thread(() -> {
			final Notification notification = new Notification(title, body);
			final Message message = Message.builder().setToken(deviceToken).setNotification(notification).build();
			try {
				FirebaseMessaging.getInstance().send(message);
			} catch (final FirebaseMessagingException e) {
				LOG.error("Message exception" + e.getMessage(), e);
			}
		}).start();
	}

}
