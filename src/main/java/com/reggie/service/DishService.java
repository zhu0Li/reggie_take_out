package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表，dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    /**
     * 根据id查询菜品信息以及对应的口味信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id);
}
