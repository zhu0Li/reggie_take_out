package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.Category;
import com.reggie.entity.Dish;
import com.reggie.entity.Setmeal;
import com.reggie.mapper.CategoryMapper;
import com.reggie.service.CategoryService;
import com.reggie.service.DishService;
import com.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 自己编写根据id删除分类，因为要在删除之前查看分类是否关联菜品
     * @param id
     */
    @Override
    public void remove(Long id) {
        //构造查询条件
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);

        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        //dish中有category_id的信息
        int countDish = dishService.count(dishLambdaQueryWrapper);
        if (countDish>0){
            //已经关联菜品，抛出业务异常

        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);

        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        int countSetmeal = setmealService.count(setmealLambdaQueryWrapper);
        if (countSetmeal>0){
            //已经关联菜品，抛出业务异常

        }

        //正常删除
        super.removeById(id);
    }
}
