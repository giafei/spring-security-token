package net.giafei.tools.util;

import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.ExpressionBasedAnnotationAttributeFactory;

public class ExpressionAttrFactoryManager {
    private static ExpressionBasedAnnotationAttributeFactory _factory;

    static {
        DefaultMethodSecurityExpressionHandler defaultMethodExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        _factory = new ExpressionBasedAnnotationAttributeFactory(defaultMethodExpressionHandler);
    }

    public static ExpressionBasedAnnotationAttributeFactory getFactory() {
        return _factory;
    }
}
