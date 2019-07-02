package net.giafei.tools.security;

import net.giafei.tools.security.attribute.AuthorizeAttributeLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public class AccessDecisionManagerWrapper implements AccessDecisionManager {
    private AbstractAccessDecisionManager wrapped;
    private Logger logger = LoggerFactory.getLogger(AccessDecisionManagerWrapper.class);
    private AuthorizeAttributeLoader loader = new AuthorizeAttributeLoader();


    public AccessDecisionManagerWrapper(AbstractAccessDecisionManager wrapped) {
        this.wrapped = wrapped;
    }

    private boolean isAnonymous(Authentication authentication) {
        return (authentication instanceof AnonymousAuthenticationToken);
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (authentication == null || !authentication.isAuthenticated()) {    //啥信息都没有
            wrapped.decide(authentication, object, configAttributes);
        } else {
            configAttributes = loader.loadAuthorizeAttribute(object, configAttributes);

            wrapped.decide(authentication, object, configAttributes);
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return wrapped.supports(attribute);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return wrapped.supports(clazz);
    }
}
