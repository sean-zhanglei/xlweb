package com.nbug.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.nbug.mico.common.token.AdminTokenComponent;
import com.nbug.mico.common.constants.SysConfigConstants;
import com.nbug.mico.common.constants.SysGroupDataConstants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.system.SystemAdmin;
import com.nbug.mico.common.model.system.SystemMenu;
import com.nbug.mico.common.model.system.SystemPermissions;
import com.nbug.mico.common.request.SystemAdminLoginRequest;
import com.nbug.mico.common.response.MenusResponse;
import com.nbug.mico.common.response.SystemAdminResponse;
import com.nbug.mico.common.response.SystemGroupDataAdminLoginBannerResponse;
import com.nbug.mico.common.response.SystemLoginResponse;
import com.nbug.mico.common.utils.SecurityUtil;
import com.nbug.mico.common.vo.LoginUserVo;
import com.nbug.mico.common.vo.MenuTree;
import com.nbug.module.infra.api.validateCode.ValidateCodeApi;
import com.nbug.module.system.service.AdminLoginService;
import com.nbug.module.system.service.SystemAdminService;
import com.nbug.module.system.service.SystemConfigService;
import com.nbug.module.system.service.SystemGroupDataService;
import com.nbug.module.system.service.SystemMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 管理端登录服务实现类

 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Resource
    private AdminTokenComponent adminTokenComponent;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private SystemAdminService systemAdminService;

    @Autowired
    private ValidateCodeApi validateCodeApi;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private SystemGroupDataService systemGroupDataService;

    @Autowired
    private SystemMenuService systemMenuService;

    /**
     * PC登录
     */
    @Override
    public SystemLoginResponse login(SystemAdminLoginRequest systemAdminLoginRequest, String ip) {
        // 判断验证码
        boolean codeCheckResult = validateCodeApi.check(systemAdminLoginRequest.getKey(), systemAdminLoginRequest.getCode()).getCheckedData();
        if (!codeCheckResult) throw new XlwebException("验证码不正确");
        // 用户验证
        Authentication authentication = null;
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(systemAdminLoginRequest.getAccount(), systemAdminLoginRequest.getPwd()));
        } catch (AuthenticationException e) {
            if (e instanceof BadCredentialsException) {
                throw new XlwebException("用户不存在或密码错误");
            }
            throw new XlwebException(e.getMessage());
        }
        LoginUserVo loginUser = (LoginUserVo) authentication.getPrincipal();
        SystemAdmin systemAdmin = loginUser.getUser();

        String token = adminTokenComponent.createToken(loginUser);
        SystemLoginResponse systemAdminResponse = new SystemLoginResponse();
        systemAdminResponse.setToken(token);
        BeanUtils.copyProperties(systemAdmin, systemAdminResponse);

        //更新最后登录信息
        systemAdmin.setLoginCount(systemAdmin.getLoginCount() + 1);
        systemAdmin.setLastIp(ip);
        systemAdminService.updateById(systemAdmin);
        return systemAdminResponse;
    }

    /**
     * 用户登出
     */
    @Override
    public Boolean logout() {
        LoginUserVo loginUserVo = SecurityUtil.getLoginUserVo();
        if (ObjectUtil.isNotNull(loginUserVo)) {
            // 删除用户缓存记录
            adminTokenComponent.delLoginUser(loginUserVo.getToken());
        }
        return true;
    }

    /**
     * 获取登录页图片
     * @return Map
     */
    @Override
    public Map<String, Object> getLoginPic() {
        Map<String, Object> map = new HashMap<>();
        //背景图
        map.put("backgroundImage", systemConfigService.getValueByKey(SysConfigConstants.CONFIG_KEY_ADMIN_LOGIN_BACKGROUND_IMAGE));
        //logo
        map.put("logo", systemConfigService.getValueByKey(SysConfigConstants.CONFIG_KEY_ADMIN_LOGIN_LOGO_LEFT_TOP));
        map.put("loginLogo", systemConfigService.getValueByKey(SysConfigConstants.CONFIG_KEY_ADMIN_LOGIN_LOGO_LOGIN));
        //轮播图
        List<SystemGroupDataAdminLoginBannerResponse> bannerList = systemGroupDataService.getListByGid(SysGroupDataConstants.GROUP_DATA_ID_ADMIN_LOGIN_BANNER_IMAGE_LIST, SystemGroupDataAdminLoginBannerResponse.class);
        map.put("banner", bannerList);
        return map;
    }

    /**
     * 获取管理员可访问目录
     * @return List<MenusResponse>
     */
    @Override
    public List<MenusResponse> getMenus() {
        LoginUserVo loginUserVo = SecurityUtil.getLoginUserVo();
        List<String> roleList = Stream.of(loginUserVo.getUser().getRoles().split(",")).collect(Collectors.toList());
        List<SystemMenu> menuList;
        if (roleList.contains("1")) {// 超管
            menuList = systemMenuService.findAllCatalogue();
        } else {
            menuList = systemMenuService.getMenusByUserId(loginUserVo.getUser().getId());
        }
        // 组装前端对象
        List<MenusResponse> responseList = menuList.stream().map(e -> {
            MenusResponse response = new MenusResponse();
            BeanUtils.copyProperties(e, response);
            return response;
        }).collect(Collectors.toList());

        MenuTree menuTree = new MenuTree(responseList);
        return menuTree.buildTree();
    }

    /**
     * 根据Token获取对应用户信息
     */
    @Override
    public SystemAdminResponse getInfoByToken() {
        LoginUserVo loginUserVo = SecurityUtil.getLoginUserVo();
        SystemAdmin systemAdmin = loginUserVo.getUser();
        SystemAdminResponse systemAdminResponse = new SystemAdminResponse();
        BeanUtils.copyProperties(systemAdmin, systemAdminResponse);
        List<String> roleList = Stream.of(systemAdmin.getRoles().split(",")).collect(Collectors.toList());
        List<String> permList = CollUtil.newArrayList();
        if (roleList.contains("1")) {
            permList.add("*:*:*");
        } else {
            permList = loginUserVo.getPermissions().stream().map(SystemPermissions::getPath).collect(Collectors.toList());
        }
        systemAdminResponse.setPermissionsList(permList);
        return systemAdminResponse;
    }
}
