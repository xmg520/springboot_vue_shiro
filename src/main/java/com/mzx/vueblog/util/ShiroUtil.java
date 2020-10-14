package com.mzx.vueblog.util;

import com.mzx.vueblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
    public static AccountProfile getProfile(){
        return (AccountProfile)SecurityUtils.getSubject().getPrincipal();
    }
}
