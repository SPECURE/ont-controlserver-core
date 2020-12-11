package com.specture.sah.config;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import com.specure.security.resolver.SahAccountHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.specture.sah.constant.URIConstants.RESULT;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        final SortHandlerMethodArgumentResolver sortResolver = new CaseSortArgumentResolver();
        final PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver(sortResolver);
        final SahAccountHandlerMethodArgumentResolver sahAccountHandlerMethodArgumentResolver = new SahAccountHandlerMethodArgumentResolver();

        pageableResolver.setOneIndexedParameters(true);
        argumentResolvers.add(sortResolver);
        argumentResolvers.add(pageableResolver);
        argumentResolvers.add(sahAccountHandlerMethodArgumentResolver);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Value("${auth0.apiAudience}")
        private String audience;
        @Value("${auth0.issuer}")
        private String issuer;

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            JwtWebSecurityConfigurer.forRS256(audience, issuer)
                .configure(httpSecurity)
                .authorizeRequests()
                .antMatchers(RESULT).permitAll()
                .anyRequest().authenticated();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            // ignoring security for swagger APIs
            web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/health").and()
                .ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        }
    }

}
