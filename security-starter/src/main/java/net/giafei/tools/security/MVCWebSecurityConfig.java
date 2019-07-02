package net.giafei.tools.security;

import net.giafei.tools.security.handler.UnauthenticatedEntryPoint;
import net.giafei.tools.security.handler.UnauthorizedAccessDeniedHandler;
import net.giafei.tools.security.token.filter.TokenAuthenticationFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class MVCWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http
            .exceptionHandling()
                //未登录时的handler
                .authenticationEntryPoint(new UnauthenticatedEntryPoint())
                //无权限时的handler
                .accessDeniedHandler(new UnauthorizedAccessDeniedHandler())
            .and()
                //如果是微服务 需要禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                //不关心跨域
                .csrf().disable()
                //我们自己实现匿名
                .anonymous().disable()
                //我们自己实现登录
                .formLogin().disable()
                //我们自己实现注销
                .logout().disable()
                //业务逻辑
                .addFilterAt(new TokenAuthenticationFilter(), RememberMeAuthenticationFilter.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
