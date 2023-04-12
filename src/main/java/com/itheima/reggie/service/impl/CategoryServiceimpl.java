package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.Mapper.CategoryMapper;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceimpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishService dishService;
    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
//    public void remove1(Long id){
//        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Dish::getCategoryId,id);
//        //添加查询条件，根据分类id进行查询
//        int count = dishService.count(lambdaQueryWrapper);
//        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
//        if (count>0){
//            //已经关联啦菜品
//            throw new RuntimeException("无法删除已经关联了菜品");
//        }
//        //查询当前分类是否关联啦套餐，如果已经关联，抛出一个业务异常
//        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
//        lambdaQueryWrapper1.eq(Setmeal::getCategoryId,id);
//        int count1 = setmealService.count(lambdaQueryWrapper1);
//        if (count1>0){
//            throw new RuntimeException("无法删除已经关联了套餐");
//        }
//        //正常删除分类
//        super.removeById(id);
//    }

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId,id);
        //添加查询条件，根据分类id进行查询
        int count = dishService.count(lambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if (count>0){
            //已经关联啦菜品
            throw new CustomException("无法删除已经关联了菜品");
        }
        //查询当前分类是否关联啦套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(lambdaQueryWrapper1);
        if (count1>0){
            throw new CustomException("无法删除已经关联了套餐");
        }
        //正常删除分类
        super.removeById(id);
    }
}
