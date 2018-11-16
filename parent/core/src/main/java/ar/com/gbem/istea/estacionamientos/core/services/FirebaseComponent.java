package ar.com.gbem.istea.estacionamientos.core.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Component
public class FirebaseComponent {

	private static final Logger LOG = LoggerFactory.getLogger(FirebaseComponent.class);

	FirebaseComponent(@Value("${firebase.server.json.path}") String jsonServerKeyPath,
			@Value("${firebase.app.url}") String firebaseAppUrl) {

		final String path = getClass().getResource(jsonServerKeyPath).getPath();

		final GoogleCredentials credentials;
		try (FileInputStream serviceAccount = new FileInputStream(path)) {
			credentials = GoogleCredentials.fromStream(serviceAccount);
		} catch (FileNotFoundException e) {
			LOG.error("Firebase Service account key not found!", e);
			throw new IllegalStateException();
		} catch (IOException e) {
			LOG.error("Firebase Service account key error!", e);
			throw new IllegalStateException();
		}

		FirebaseApp.initializeApp(
				new FirebaseOptions.Builder().setCredentials(credentials).setDatabaseUrl(firebaseAppUrl).build());
	}

}
