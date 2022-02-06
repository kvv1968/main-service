package ru.platform.learning.mainservice.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import ru.platform.learning.mainservice.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        httpServletResponse.sendRedirect(determineTargetUrl(authentication));
    }
    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("user")) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("admin")) {
                isAdmin = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")){

            }
        }
        User user = (User) authentication.getPrincipal();

        if (isUser) {

          return   isTechnicalUser(user) ? "/user/reg" : "/user/nav";

        } else if (isAdmin) {

            return "/admin/";
        } else {
            throw new IllegalStateException();
        }
    }


    private boolean isTechnicalUser(User user) {
        return user.getDate() == null &&
                StringUtils.isEmpty(user.getFirstName()) &&
                StringUtils.isEmpty(user.getLastName()) &&
                StringUtils.isEmpty(user.getEmail()) && StringUtils.isEmpty(user.getPhone());
    }

}
