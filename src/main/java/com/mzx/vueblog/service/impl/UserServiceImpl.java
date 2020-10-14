package com.mzx.vueblog.service.impl;

import com.mzx.vueblog.entity.User;
import com.mzx.vueblog.mapper.UserMapper;
import com.mzx.vueblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mzx
 * @since 2020-10-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
