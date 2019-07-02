package net.giafei.tools.security.attribute;

import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
 * Date 2019/7/2 8:38
 */
public class AuthorizeAttributeContainer {
    private Set<ConfigAttribute> attributes;

    public AuthorizeAttributeContainer() {
        attributes = new HashSet<>();
    }

    public AuthorizeAttributeContainer(Collection<? extends ConfigAttribute> attrs) {
        attributes = new HashSet<>(attrs);
    }

    public void addAttribute(ConfigAttribute attr) {
        this.attributes.add(attr);
    }

    public void addAttributes(Collection<? extends ConfigAttribute> attrs) {
        this.attributes.addAll(attrs);
    }

    public Set<ConfigAttribute> getAttributes() {
        return attributes;
    }
}
