package net.giafei.ui.demo.biz.user.impl;

import net.giafei.tools.security.token.AuthorityAuthenticationToken;
import net.giafei.tools.security.token.authentication.ITokenProvider;
import net.giafei.tools.security.token.authorization.ITokenConverter;
import net.giafei.tools.util.ServletHolder;
import net.giafei.ui.demo.biz.user.IUserService;
import net.giafei.ui.menu.core.IUserAuthorityProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
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
 * Date 2019/7/2 11:34
 */

@Service
public class UserServiceImpl implements IUserService, ITokenProvider, ITokenConverter, IUserAuthorityProvider {
    private static final String cookieName = "token";
    @Override
    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName()))
                return cookie.getValue();
        }

        return null;
    }

    @Override
    public AuthorityAuthenticationToken decodeToken(String token) {
        try {
            byte[] bytes = Base64.getDecoder().decode(token);
            String[] arr = new String(bytes).split(";");
            if (arr.length != 3)
                return null;

            if (!arr[0].equals(arr[2]))
                return null;

            List<SimpleGrantedAuthority> authorities = getUserAuthorities(arr[1]).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new AuthorityAuthenticationToken(arr[1], authorities);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void login(String username, String password) {
        //登录功能模拟
        if ((username == null) || !username.equals(password))
            throw new RuntimeException("用户名或密码错误");

        String[] arr = new String[] {
                "x", username, "x"
        };

        String token = Base64.getEncoder().encodeToString(String.join(";", arr).getBytes());
        Cookie cookie = new Cookie(cookieName, token);
        ServletHolder.getCurrentResponse().addCookie(cookie);
    }

    @Override
    public void logout() {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        ServletHolder.getCurrentResponse().addCookie(cookie);
    }

    @Override
    public Set<String> getCurrentUserAuthorities() {
        Principal principal = ServletHolder.getCurrentRequest().getUserPrincipal();
        if (principal == null)
            return Collections.emptySet();

        String user =principal.getName();
        if (StringUtils.isEmpty(user))
            return Collections.emptySet();

        return new HashSet<>(getUserAuthorities(user));
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private List<String> getUserAuthorities(String uid) {
        List<String> authorities = new ArrayList<>(2);

        switch (uid) {
            case "1":
                authorities.add("test1");
                break;

            case "2":
                authorities.add("test2");
                break;

            case "admin":
                authorities.add("admin");
                break;
        }

        return authorities;
    }
}
