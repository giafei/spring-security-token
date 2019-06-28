package net.giafei.ui.menu.anno;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MenuItem {
    String label();
    String icon();
}
