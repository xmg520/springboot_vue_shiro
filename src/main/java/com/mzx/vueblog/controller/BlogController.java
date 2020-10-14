package com.mzx.vueblog.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzx.vueblog.dto.Result;
import com.mzx.vueblog.entity.Blog;
import com.mzx.vueblog.service.BlogService;
import com.mzx.vueblog.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mzx
 * @since 2020-10-07
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @RequestMapping("/blogs")
    public Result lists(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page,new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageData);
    }

    @RequestMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该文章已被删除！");
        return Result.succ(blog);
    }

    @RequiresAuthentication
    @RequestMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){
        Blog temp = null;

        if (temp != null){
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            System.out.println(ShiroUtil.getProfile().getId());
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"当前无权限编辑");
        }else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);

        }
        BeanUtils.copyProperties(blog,temp,"userId","created","status");
        blogService.saveOrUpdate(temp);
        return Result.succ(temp);
    }
}
