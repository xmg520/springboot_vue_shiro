package com.mzx.vueblog.service.impl;

import com.mzx.vueblog.entity.Blog;
import com.mzx.vueblog.mapper.BlogMapper;
import com.mzx.vueblog.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
