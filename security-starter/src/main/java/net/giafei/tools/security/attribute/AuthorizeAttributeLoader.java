package net.giafei.tools.security.attribute;

import net.giafei.tools.util.SpringApplicationContextHolder;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.security.access.ConfigAttribute;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_
 * //                         o8888888o
 * //                         88" . "88
 * //                         (| ^_^ |)
 * //                         O\  =  /O
 * //                      ____/`---'\____
 * //                    .'  \\|     |//  `.
 * //                   /  \\|||  :  |||//  \
 * //                  /  _||||| -:- |||||-  \
 * //                  |   | \\\  -  /// |   |
 * //                  | \_|  ''\---/''  |   |
 * //                  \  .-\__  `-`  ___/-. /
 * //                ___`. .'  /--.--\  `. . ___
 * //              ."" '<  `.___\_<|>_/___.'  >'"".
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /
 * //      ========`-.____`-.___\_____/___.-`____.-'========
 * //                           `=---='
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * //         佛祖保佑       永无BUG     永不修改
 * ////////////////////////////////////////////////////////////////////
 *
 * @author xjf
 * @version 1.0
 * Date 2019/7/2 8:36
 */
public class AuthorizeAttributeLoader {
    //理论上运行过程中 这个是不会变化的 缓存起来
    private Map<MethodInvocation, Collection<ConfigAttribute>> attrCache = new ConcurrentHashMap<>();

    private Class<?> getTargetClass(MethodInvocation mi) {
        Object target = mi.getThis();

        if (target != null) {
            return target instanceof Class<?> ? (Class<?>) target
                    : AopProxyUtils.ultimateTargetClass(target);
        }

        return null;
    }

    public Collection<ConfigAttribute> loadAuthorizeAttribute(Object object, Collection<ConfigAttribute> configAttributes) {
        if (object instanceof MethodInvocation) {

            MethodInvocation mi = (MethodInvocation) object;
            Collection<ConfigAttribute> result = attrCache.get(mi);
            if (result == null) {
                Method method = mi.getMethod();

                AuthorizeAttributeContainer container = new AuthorizeAttributeContainer(configAttributes);
                SpringApplicationContextHolder.getInstance()
                        .getBeansOfType(IAuthorizeAttributeProvider.class)
                        .forEach((k, v) -> v.loadAttribute(getTargetClass(mi), method, container));

                result = new ArrayList<>(container.getAttributes());
                attrCache.put(mi, result);
            }

            return result;
        }

        return configAttributes;
    }
}
