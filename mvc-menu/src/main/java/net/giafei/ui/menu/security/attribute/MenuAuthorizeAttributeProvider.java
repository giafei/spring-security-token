package net.giafei.ui.menu.security.attribute;

import net.giafei.tools.security.attribute.AuthorizeAttributeContainer;
import net.giafei.tools.security.attribute.IAuthorizeAttributeProvider;
import net.giafei.tools.util.ExpressionAttrFactoryManager;
import net.giafei.ui.menu.anno.MenuItem;
import org.springframework.security.access.expression.method.ExpressionBasedAnnotationAttributeFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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
 * Date 2019/7/2 8:57
 */

@Component
public class MenuAuthorizeAttributeProvider implements IAuthorizeAttributeProvider {

    private ExpressionBasedAnnotationAttributeFactory attributeFactory = ExpressionAttrFactoryManager.getFactory();

    @Override
    public void loadAttribute(Class<?> aClass, Method method, AuthorizeAttributeContainer container) {
        MenuItem menuItem = method.getDeclaredAnnotation(MenuItem.class);
        if (menuItem == null)
            return;

        String code = menuItem.code();
        if ("*".equals(code))
            return;

        String exp;
        if ("admin".equals(code)) {
            exp = "hasAuthority('admin')";
        } else {
            exp = "hasAuthority('" + code + "') || hasAuthority('admin')";
        }

        container.addAttribute(
                attributeFactory.createPreInvocationAttribute(null, null, exp)
        );

    }
}
