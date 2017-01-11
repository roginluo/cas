package org.apereo.cas.adaptors.gauth.repository.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is {@link BaseGoogleAuthenticatorTokenRepository}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
public abstract class BaseGoogleAuthenticatorTokenRepository implements GoogleAuthenticatorTokenRepository {
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());
}