package net.giafei.ui.demo.security.authorization;

import net.giafei.tools.security.token.AuthorityAuthenticationToken;
import net.giafei.tools.security.token.authorization.ITokenConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
 * Date 2019/7/2 9:03
 */

@Component
public class TestTokenDecoder implements ITokenConverter {
    @Override
    public AuthorityAuthenticationToken decodeToken(String token) {
        if (StringUtils.isEmpty(token))
            return null;

        List<GrantedAuthority> authorities = new ArrayList<>(2);

        switch (token) {
            case "1":
                authorities.add(new SimpleGrantedAuthority("test1"));
                break;

            case "2":
                authorities.add(new SimpleGrantedAuthority("test2"));
                break;

            case "admin":
                authorities.add(new SimpleGrantedAuthority("admin"));
                break;
        }

        return new AuthorityAuthenticationToken(token, authorities);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
