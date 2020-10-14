package com.mzx.vueblog.shiro;

import com.mzx.vueblog.entity.User;
import com.mzx.vueblog.service.UserService;
import com.mzx.vueblog.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AccountRealm是shiro进行登录或者权限校验的逻辑所在，算是核心了，我们需要重写3个方法，分别是
 *
 * supports：为了让realm支持jwt的凭证校验
 * doGetAuthorizationInfo：权限校验
 * doGetAuthenticationInfo：登录认证校验
 *
 * 作者：MarkerHub
 * 链接：https://juejin.im/post/6844903823966732302
 * 来源：掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm  {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        log.info("jwtToken----------------->{}", jwtToken);
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userService.
                getById(Long.parseLong(userId));
        if (user == null){
            throw new UnknownAccountException("账户不存在！");
        }
        if(user.getStatus() == -1){
            throw new LockedAccountException("账户已被锁定！");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtils.copyProperties(user,profile);
        log.info("profile----------------->{}", profile.toString());

        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof  JwtToken;
    }
}
