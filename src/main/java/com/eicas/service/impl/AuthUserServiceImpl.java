//package com.eicas.service.impl;
//
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.eicas.common.ResultData;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.Resource;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements IAuthUserService {
//    @Resource
//    AuthUserMapper authUserMapper;
//    @Resource
//    AuthPermitMapper permitMapper;
//    @Resource
//    IAuthUserRoleRService authUserRoleRService;
//    @Resource
//    private PasswordEncoder passwordEncoder;
//
//    @Value("${user.defaultPwd}")
//    private String defaultPwd;
//
//    /**
//     * 根据用户账号名称获取用户权限信息，构造UserDetails对象
//     *
//     * @param username 用户账号名称
//     * @return User对象（包含用户名、密码、权限集合）
//     * @throws UsernameNotFoundException 用户不存在
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AuthUser authUser = this.getOne(Wrappers.<AuthUser>lambdaQuery().eq(AuthUser::getUsername, username), false);
//        if (authUser == null) {
//            throw new RuntimeException("用户不存在");
//        }
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        /*
//         * 获取用户所有角色，并加入到权限信息列表
//         */
////        List<AuthRole> roles = authUserMapper.selectRoleListByUserId(authUser.getId());
////        roles = roles.stream().distinct().collect(Collectors.toList());
////        roles.forEach(role -> {
////            GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleKey());
////            authorities.add(authority);
////        });
//        /*
//         * 获取用户所有权限，并加入到权限信息列表
//         */
//        List<AuthPermit> permits = permitMapper.selectPermitListByUserId(authUser.getId());
//        permits = permits.stream().distinct().collect(Collectors.toList());
//        permits.forEach(permit -> {
//            GrantedAuthority authority = new SimpleGrantedAuthority(permit.getPermissionCode());
//            authorities.add(authority);
//        });
//        return new User(authUser.getUsername(), authUser.getPassword(), authorities);
//    }
//
//    @Override
//    public AuthUser register(UserRegisterParam param) {
//        AuthUser user = new AuthUser();
//        BeanUtils.copyProperties(param, user);
//        user.setEnabled(true);
//        user.setAccountNonExpired(true);
//        user.setAccountNonLocked(true);
//        user.setCredentialsNotExpired(true);
//
//        List<AuthUser> userList = authUserMapper.selectList(Wrappers.<AuthUser>lambdaQuery().eq(AuthUser::getUsername, param.getUsername()));
//        if (userList.size() > 0) {
//            return null;
//        }
//        String encodePassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodePassword);
//        authUserMapper.insert(user);
//        return user;
//    }
//
//    @Override
//    public AuthUser getByUsername(String username) {
//        return authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getUsername, username));
//    }
//
//    @Override
//    public AuthUser getUserDetailsByUsername(String username) {
//        /*
//         * TODO
//         */
//        return null;
//    }
//
//    @Override
//    public boolean login(String username, String password) {
//        boolean result = false;
//        try {
//            UserDetails userDetails = loadUserByUsername(username);
//            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
//                log.error("用户名密码不匹配");
//                result = false;
//            }
//            if (!userDetails.isEnabled()) {
//                log.error("账号被禁用");
//                result = false;
//            }
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
//                    null,
//                    userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            result = true;
//        } catch (Exception e) {
//            log.error("登录异常");
//        }
//        return result;
//    }
//
//    @Override
//    public List<AuthRole> listRolesById(Long userId) {
//        return authUserMapper.selectRoleListByUserId(userId);
//    }
//
//    @Override
//    public List<AuthPermit> listPermitsById(Long userId) {
//        return authUserMapper.selectPermitListByUserId(userId);
//    }
//
//    @Override
//    public int changePassword(UserPasswordParam param) {
//        if (StrUtil.isEmpty(param.getUsername())
//                || StrUtil.isEmpty(param.getOldPassword())
//                || StrUtil.isEmpty(param.getNewPassword())) {
//            return -1;
//        }
//        List<AuthUser> userList = authUserMapper.selectList(Wrappers.<AuthUser>lambdaQuery().eq(AuthUser::getUsername, param.getUsername()));
//        if (userList.size() > 0) {
//            return -2;
//        }
//        AuthUser user = userList.get(0);
//        if (!passwordEncoder.matches(param.getOldPassword(), user.getPassword())) {
//            return -3;
//        }
//        user.setPassword(passwordEncoder.encode(param.getNewPassword()));
//        authUserMapper.updateById(user);
//        return 1;
//    }
//
//    @Override
//    public Page<AuthUser> listAuthUser(String keyword, Integer current, Integer size) {
//        return authUserMapper.listAuthUser(keyword, Page.of(current, size));
//    }
//
//    @Override
//    public void allocRole(Long userId, List<Long> roleIds) {
//        //先删除原来的关系
//        authUserRoleRService.remove(Wrappers.<AuthUserRoleR>lambdaQuery().eq(AuthUserRoleR::getUserId, userId));
//        //建立新关系
//        if (!CollectionUtils.isEmpty(roleIds)) {
//            List<AuthUserRoleR> list = new ArrayList<>();
//            for (Long roleId : roleIds) {
//                AuthUserRoleR roleRelation = new AuthUserRoleR();
//                roleRelation.setUserId(userId);
//                roleRelation.setRoleId(roleId);
//                list.add(roleRelation);
//            }
//            authUserRoleRService.saveBatch(list);
//        }
//    }
//
//    @Override
//    public Boolean updateStatus(Long id, Boolean enabled) {
//        // TODO 更新用户状态，启用，禁用
//        return null;
//    }
//
//    @Override
//    public ResultData createUser(AuthUser user) {
//        if(!StringUtils.hasText(user.getPassword())){
//            user.setPassword(passwordEncoder.encode(defaultPwd));
//        }
//
//        if(!save(user)){
//            return ResultData.failed("保存失败！");
//        }
//
//        if(user.getRoleList() != null && user.getRoleList().size() > 0){
//            List<AuthUserRoleR> list = user.getRoleList().stream().map(i->
//                    new AuthUserRoleR()
//                            .setUserId(user.getId())
//                            .setRoleId(i.getId())
//            ).collect(Collectors.toList());
//            if(!authUserRoleRService.saveBatch(list)){
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return ResultData.failed("保存失败！");
//            }
//        }
//        return ResultData.success(user,"保存成功！");
//    }
//
//    @Override
//    public UserResourceVo getUserResourceById(Long userId) {
//        return authUserMapper.getUserResourceById(userId);
//    }
//
//    @Override
//    public UserResourceVo getUserResource() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authUserMapper.getUserResourceByUsername(String.valueOf(authentication.getPrincipal()));
//    }
//
//    @Override
//    public boolean removeById(Serializable id) {
//        super.removeById(id);
//        return authUserRoleRService.remove(Wrappers.<AuthUserRoleR>lambdaQuery().eq(AuthUserRoleR::getUserId, id));
//    }
//}
