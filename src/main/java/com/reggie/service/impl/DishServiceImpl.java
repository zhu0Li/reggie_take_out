package com.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;
import com.reggie.entity.DishFlavor;
import com.reggie.mapper.DishMapper;
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 新增菜品，同时保存口味数据
     * 因为涉及多表操作，所以需要加入事务控制@Transactional
     * @param dishDto
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        //获取菜品id
        Long dishId = dishDto.getId();

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
//        for (DishFlavor item :
//                flavors) {
//            item.setDishId(dishId);
//        }
        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);

    }
}
