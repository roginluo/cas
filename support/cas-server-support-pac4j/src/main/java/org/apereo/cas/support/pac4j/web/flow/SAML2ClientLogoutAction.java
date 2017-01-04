package org.apereo.cas.support.pac4j.web.flow;

import org.apereo.cas.util.http.HttpClient;
import org.apereo.cas.util.http.HttpMessage;
import org.apereo.cas.web.support.WebUtils;
import org.pac4j.core.client.Clients;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.redirect.RedirectAction;
import org.pac4j.saml.client.SAML2Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;

/**
 * This is {@link SAML2ClientLogoutAction}.
 *
 * @author Misagh Moayyed
 * @since 5.1.0
 */
public class SAML2ClientLogoutAction extends AbstractAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(SAML2ClientLogoutAction.class);

    private final Clients clients;
    private final HttpClient httpClient;

    public SAML2ClientLogoutAction(final Clients clients, final HttpClient httpClient) {
        this.clients = clients;
        this.httpClient = httpClient;
    }

    @Override
    protected Event doExecute(final RequestContext requestContext) throws Exception {
        try {
            final HttpServletRequest request = WebUtils.getHttpServletRequest(requestContext);
            final HttpServletResponse response = WebUtils.getHttpServletResponse(requestContext);
            final J2EContext context = new J2EContext(request, response);
            final SAML2Client client = clients.findClient(SAML2Client.class);
            if (client != null) {
                LOGGER.debug("Located SAML2 client [{}]", client);
                final RedirectAction action = client.getLogoutAction(context, null, null);
                LOGGER.debug("Preparing logout message to send is [{}]", action.getLocation());
                final HttpMessage msg = this.httpClient.sendMessageToEndPoint(new URL(action.getLocation()));
                LOGGER.debug("Logout message response from the identity provider is {}", msg);
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return null;
    }
}