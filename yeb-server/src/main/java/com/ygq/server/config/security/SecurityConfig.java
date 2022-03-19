package com.ygq.server.config.security;

import com.ygq.server.pojo.Admin;
import com.ygq.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-28 14:44
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private RestAuthorizationEntryPoint authorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CustomFilter customFilter;
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // 重写 loadByUsername 方法
        return username -> {
            Admin admin = adminService.getAdminByUsername(username);
            if (admin != null) {
                admin.setRoles(adminService.getRoles(admin.getId()));
                return admin;
            }
            throw new UsernameNotFoundException("异常：用户名或密码不正确");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 所有的请求都需要认证
                .anyRequest().authenticated()
                // 动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(customFilter);
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        return object;
                    }
                })
                .and()
                // 禁用缓存
                .headers().cacheControl();
        // 添加jwt登录授权拦截器
        http.addFilterBefore(jwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
        // 添加自定义未登录和未授权结果返回
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authorizationEntryPoint);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/login", "/logout",
                // 静态资源
                "/css/**", "/js/**", "/index.html", "favicon.ico",
                // 文档接口
                "/doc.html", "/webjars/**", "/swagger-resources/**",
                "/v2/api-docs/**", "/captcha"
        );
    }
}
