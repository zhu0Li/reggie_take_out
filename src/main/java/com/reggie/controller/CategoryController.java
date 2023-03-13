package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.R;
import com.reggie.entity.Category;
import com.reggie.entity.Employee;
import com.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询
     * 使用封装好的page方法
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        Page<Category> pageInfo = new Page<Category>(page, pageSize);

        //构造分页构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        //添加排序条件
        queryWrapper.orderByAsc(Category::getType);
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 新增分类
     * 因为已经添加全局异常处理器，所以如果重复添加分类会跳转到异常处理
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("新增分类，分类信息{}", category.toString());
        categoryService.save(category);

        return R.success("分类添加成功");
    }

    /**
     * 根据id来删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除分类，id：{}",id);
//        categoryService.removeById(id);

        //调用自己在业务层编写的remove方法
        categoryService.remove(id);
        return R.success("分类信息删除成功");
    }
}
