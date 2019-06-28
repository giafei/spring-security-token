package net.giafei.tools.mvc.internal;

import net.giafei.tools.mvc.IMVCMappingHandler;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@Component
public class WebMvcRegistrationsObserver implements WebMvcRegistrations {
    private ApplicationContext context;
    private MappingHandler handler = new MappingHandler();

    public WebMvcRegistrationsObserver(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return handler;
    }

    private class MappingHandler extends RequestMappingHandlerMapping {
        @Override
        protected void handlerMethodsInitialized(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
            super.handlerMethodsInitialized(handlerMethods);

            context.getBeansOfType(IMVCMappingHandler.class)
                    .forEach((name, bean) -> bean.afterMappingMethodsInstantiated(handlerMethods));
        }
    }
}
