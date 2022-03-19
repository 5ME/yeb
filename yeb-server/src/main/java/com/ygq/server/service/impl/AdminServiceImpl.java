package com.ygq.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygq.server.Result;
import com.ygq.server.config.security.JwtUtil;
import com.ygq.server.mapper.AdminMapper;
import com.ygq.server.mapper.RoleMapper;
import com.ygq.server.pojo.Admin;
import com.ygq.server.pojo.Menu;
import com.ygq.server.pojo.Role;
import com.ygq.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Yin Guiqing
 * @since 2022-02-17
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Result login(String username, String password, String code,
                        HttpServletRequest request) {
        // 从session中获取验证码，并进行验证
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(code)) {
            return Result.error("验证码错误，请重新输入！");
        }
        // 登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password,
                userDetails.getPassword())) {
            return Result.error("用户名或密码错误！");
        }
        if (!userDetails.isEnabled()) {
            return Result.error("账号被禁用，请联系管理员！");
        }

        // 更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 生成token
        String token = jwtUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>(8);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return Result.success("登录成功！", tokenMap);
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",
                username).eq("enabled", true));
    }

    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }
}
