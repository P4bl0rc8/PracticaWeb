package com.example.PracticaWeb.Security.Filter;

import com.example.PracticaWeb.Enumerated.Role;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityJsonViewControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {


    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        if(SecurityContextHolder.getContext().getAuthentication() != null
        && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null){
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            List<Class> jsonViews = authorities.stream().map(GrantedAuthority::getAuthority).map(Role::valueOf).map(View.mapping::get).collect(Collectors.toList());

            if(jsonViews.size() == 1){
                bodyContainer.setSerializationView(jsonViews.get(0));
                return;
            }
            throw new IllegalArgumentException("Ambiguous @JsonView declaration for roles "
                    + authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));

        }
    }
}
