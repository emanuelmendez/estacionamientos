package ar.com.gbem.istea.estacionamientos.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import ar.com.gbem.istea.estacionamientos.core.services.UserService;

@Component
public class OAuth2Filter extends GenericFilterBean {

	private static final Logger LOG = LoggerFactory.getLogger(OAuth2Filter.class);
	private static final String API_TOKEN = "api_token";
	private static final String CLIENT_ID = "349020659959-ah8n75k13u1ekbgu59tfioqkgipc46mv.apps.googleusercontent.com";

	@Autowired
	private UserService userService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String token = httpRequest.getHeader(API_TOKEN);

		if (token == null) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client must provide an api_token");
			return;
		}

		HttpTransport httpTransport;
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		} catch (GeneralSecurityException e) {
			LOG.error("Error on new Trusted Transport instance", e);
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not verify Google token");
			return;
		}

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport,
				JacksonFactory.getDefaultInstance()).setAudience(Collections.singletonList(CLIENT_ID)).build();

		GoogleIdToken idToken;
		try {
			idToken = verifier.verify(token);
		} catch (Exception e) {
			LOG.error("Error on token verify", e);
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
			return;
		}

		if (idToken == null) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is no longer valid");
			return;
		}

		Payload payload = idToken.getPayload();
		String subject = payload.getSubject();

		if (!userService.exists(subject)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "User needs to signup to continue");
			return;
		}

		// TODO ver para obtener estos datos del payload en el alta de usuario
//		boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//		String name = (String) payload.get("name");
//		String familyName = (String) payload.get("family_name");
//		String givenName = (String) payload.get("given_name");

		chain.doFilter(request, response);
	}

}
