package net.giafei.tools.security.token.filter;

import net.giafei.tools.security.token.AuthorityAuthenticationToken;
import net.giafei.tools.security.token.authorization.ITokenConverter;
import net.giafei.tools.security.token.authentication.ITokenProvider;
import net.giafei.tools.util.SpringApplicationContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Comparator;
import java.util.UUID;

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
 * Date 2019/7/2 7:13
 */

public class TokenAuthenticationFilter implements Filter {
    private ITokenProvider[] providers;
    private ITokenConverter[] converters;

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private final AnonymousAuthenticationToken anonymousUser = new AnonymousAuthenticationToken(
            UUID.randomUUID().toString(), "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
    );

    private String getToken(HttpServletRequest request) {
        if (providers == null) {  //幂等的 所以没加锁
            providers = SpringApplicationContextHolder.getInstance()
                .getBeansOfType(ITokenProvider.class)
                .values().stream()
                .sorted(Comparator.comparingInt(ITokenProvider::getOrder))
                .toArray(ITokenProvider[]::new);
        }

        for (ITokenProvider provider : providers) {
            String token = provider.getToken(request);
            if (StringUtils.hasText(token))
                return token;
        }

        return null;
    }

    private AuthorityAuthenticationToken decodeToken(String token) {
        if (StringUtils.isEmpty(token))
            return null;

        if (converters == null) {
            converters = SpringApplicationContextHolder.getInstance()
                    .getBeansOfType(ITokenConverter.class)
                    .values().stream()
                    .sorted(Comparator.comparingInt(ITokenConverter::getOrder))
                    .toArray(ITokenConverter[]::new);
        }

        for (ITokenConverter converter : converters) {
            AuthorityAuthenticationToken u = converter.decodeToken(token);
            if (u != null)
                return u;
        }

        return null;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            String token = getToken(request);
            AuthorityAuthenticationToken authToken = null;

            if (StringUtils.hasText(token)) {
                authToken = decodeToken(token);
            }

            if (authToken != null) {
                authToken.setDetails(authenticationDetailsSource.buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                SecurityContextHolder.getContext().setAuthentication(anonymousUser);
            }
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(anonymousUser);
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
