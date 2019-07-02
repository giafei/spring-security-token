package net.giafei.ui.demo.contoller;

import net.giafei.ui.demo.biz.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
 * Date 2019/7/2 11:57
 */

@Controller
public class LoginController {
    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String login() {
        return "view/login/login";
    }

    @PostMapping("/login")
    public String login(String redirect, String username, String password, Model model) {
        try {
            userService.login(username, password);

            if (StringUtils.isEmpty(redirect)) {
                redirect = "redirect:/";
            } else {
                redirect = "redirect:" + redirect;
            }

            return  redirect;
        } catch (Exception e) {
            model.addAttribute("errMsg", e.getMessage());
            model.addAttribute("redirect", redirect);
            return "view/login/login";
        }
    }
}
