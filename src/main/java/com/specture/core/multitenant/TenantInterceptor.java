package com.specture.core.multitenant;

import com.specture.core.constant.URIConstants;
import com.specure.security.Role;
import com.specure.security.SahAccount;
import com.specure.security.resolver.SahAccountHandlerMethodArgumentResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.specure.security.constants.SecurityConstants.*;

@Component
@AllArgsConstructor
@Slf4j
public class TenantInterceptor extends HandlerInterceptorAdapter {

    private final MultiTenantManager multiTenantManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional.ofNullable(request.getHeader(CLIENT_NAME))
                .ifPresent(multiTenantManager::setCurrentTenant);

        if (request.getHeader(AUTHORIZATION) == null) return true;
        if (request.getServletPath().equals(URIConstants.ERROR)) return true;

        SahAccount sahAccount = new SahAccountHandlerMethodArgumentResolver()
                .convert(request.getUserPrincipal(), StringUtils.removeStart(request.getHeader(AUTHORIZATION), BEARER));
        String tenantPermissionHeader = String.join(":", CLIENT, request.getHeader(CLIENT_NAME));
        if (sahAccount.getRoles().contains(Role.SPECURE.getName())) return true;
        if (!sahAccount.getPermissions().contains(tenantPermissionHeader)) throw new SahAccount.NotPermitted();

        return true;
    }
}
