package net.giafei.ui.demo.security.authorization;

import net.giafei.tools.util.ServletHolder;
import net.giafei.ui.menu.core.IUserAuthorityProvider;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

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
 * Date 2019/7/2 9:54
 */

@Component
public class TestUserAuthoritiesProvider implements IUserAuthorityProvider {
    @Override
    public Set<String> getCurrentUserAuthorities() {
        //正常应该有业务提供这个数据，暂时从Principal获取
        HttpServletRequest request = ServletHolder.getCurrentRequest();
        Principal principal = request.getUserPrincipal();

        if (principal == null)  //未登录
            return Collections.emptySet();

        if (!(principal instanceof AbstractAuthenticationToken)) {
            return Collections.emptySet();
        }

        Collection<GrantedAuthority> authorities = ((AbstractAuthenticationToken) principal).getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}
