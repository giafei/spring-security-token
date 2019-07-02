package net.giafei.ui.menu.anno;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("isAuthenticated()")  //这个注解必须有，启用Spring Security的投票机制
public @interface MenuItem {
    String label();
    String icon();
    String code();
}
