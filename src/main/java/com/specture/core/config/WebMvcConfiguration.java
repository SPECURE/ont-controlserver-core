package com.specture.core.config;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import com.specure.security.Role;
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

import static com.specture.core.constant.URIConstants.*;
import static com.specure.security.constants.SecurityConstants.SCOPE_PREFIX;

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
                    .antMatchers(TEST_RESULT, TEST_RESULT_DETAIL, RESULT).permitAll()
                    .antMatchers(BASIC_QOS_TEST_MOBILE, BASIC_QOS_TEST_BY_UUID, BASIC_QOS_TEST_FIXED).permitAll()
                    .antMatchers(BASIC_TEST, MOBILE_TEST, BASIC_TEST_BY_UUID).permitAll()
                    .antMatchers(REQUEST_DATA_COLLECTOR).permitAll()
                    .antMatchers(HttpMethod.GET, MEASUREMENT_SERVER).permitAll()
                    .antMatchers(HttpMethod.POST, MEASUREMENT_SERVER).permitAll()
                    .antMatchers(TEST_REQUEST, TEST_REQUEST_FOR_WEB_CLIENT, TEST_REQUEST_FOR_ADMIN, MEASUREMENT_RESULT,
                            MEASUREMENT_RESULT_QOS, MEASUREMENT_QOS_REQUEST, MEASUREMENT_RESULT_BY_UUID).permitAll()
                    .antMatchers(MEASUREMENT_STATS_FOR_GENERAL_USER_PORTAL).permitAll()
                    .antMatchers(SETTINGS).permitAll()
                    .antMatchers(HttpMethod.GET, PROVIDERS).permitAll()
                    .antMatchers(PACKAGES, DEPLOYED_PROBES, PORTS, PACKAGES_PORTS, USER_EXPERIENCE_METRICS, GRAPHS, HOURLY, GRAPHS_HOURLY).permitAll()
                    .antMatchers(HttpMethod.GET, SITES).permitAll()
                    .antMatchers(PROBES_KEEP_ALIVE).permitAll()
                    .antMatchers(EXPORT_FULL, EXPORT_MONTHLY).permitAll()
                    .antMatchers(HttpMethod.GET, PROBES).permitAll()
                    .antMatchers(HttpMethod.GET, STATS_SITE).permitAll()
                    .antMatchers(MEASUREMENT_SERVER, PROBES, WEB_TEST).hasAuthority(SCOPE_PREFIX + Role.SPECURE.getName())
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
