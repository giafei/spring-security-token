package net.giafei.ui.menu.core.impl;

import net.giafei.tools.mvc.IMVCMappingHandler;
import net.giafei.ui.menu.anno.MenuItem;
import net.giafei.ui.menu.core.IMenuUIService;
import net.giafei.ui.menu.core.IUserAuthorityProvider;
import net.giafei.ui.menu.core.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Component("menuUIService")
public class MenuUIServiceImpl implements IMVCMappingHandler, IMenuUIService {
    private List<MenuVO> menus;

    @Autowired
    private IUserAuthorityProvider provider;

    @Override
    public void afterMappingMethodsInstantiated(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
        List<MenuVO> arr = new LinkedList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HandlerMethod method = entry.getValue();
            Class<?> beanType = method.getBeanType();

            MenuItem menuItem = AnnotatedElementUtils.getMergedAnnotation(method.getMethod(), MenuItem.class);
            if (menuItem == null)
                continue;

            MenuVO vo = new MenuVO();
            vo.setIcon(menuItem.icon());
            vo.setLabel(menuItem.label());
            vo.setCode(menuItem.code());

            //取第一个
            vo.setUrl(entry.getKey().getPatternsCondition().getPatterns().iterator().next());
            arr.add(vo);
        }

        menus = arr;
    }

    @Override
    public List<MenuVO> getAllMenu() {
        if (menus == null) {
            throw new RuntimeException("菜单尚未初始化完成");
        }

        return menus;
    }

    @Override
    public List<MenuVO> getUserMenu() {
        Set<String> authorities = provider.getCurrentUserAuthorities();
        if (authorities.isEmpty())
            return Collections.emptyList();

        List<MenuVO> menu = getAllMenu();
        if (authorities.contains("admin"))
            return menu;

        return menu.stream()
                .filter(m -> authorities.contains(m.getCode()))
                .collect(Collectors.toList());
    }
}
