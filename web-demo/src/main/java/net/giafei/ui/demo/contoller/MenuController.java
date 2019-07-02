package net.giafei.ui.demo.contoller;

import net.giafei.ui.menu.anno.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @GetMapping("/menu/menu1")
    @MenuItem(label = "菜单1", icon = "glyphicon glyphicon-level-up", code = "test1")
    public String menu1() {
        return "view/menu/menu1";
    }

    @GetMapping("/menu/menu2")
    @MenuItem(label = "菜单2", icon = "glyphicon glyphicon-log-in", code = "test2")
    public String menu2() {
        return "view/menu/menu2";
    }
}
