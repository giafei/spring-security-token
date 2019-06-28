package net.giafei.tools.mvc;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Map;

public interface IMVCMappingHandler {
    void afterMappingMethodsInstantiated(Map<RequestMappingInfo, HandlerMethod> handlerMethods);
}
