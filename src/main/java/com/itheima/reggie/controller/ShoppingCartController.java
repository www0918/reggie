package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加菜品或套餐到购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> addshoppingcart(@RequestBody ShoppingCart shoppingCart){
        //设置用户id，指定当前是那个用户的购物车
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        //查看菜品是否在购物车中
        Long dishId = shoppingCart.getDishId();
        //条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        if (dishId!=null){
            //判断添加的是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            //判断添加的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        //进行条件查询
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        if (one!=null){
            //如果菜品或套餐存在购物车中，则数量加一
            Integer number = one.getNumber();
            one.setNumber(number+1);
            //提交事务
            shoppingCartService.updateById(one);
        }else {
            //如果不存在，进行添加
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            one=shoppingCart;
        }
        return R.success(one);

    }

    /**
     * 查询用户购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> listR(){
        //查询登录用户的购物车
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @Transactional
    @DeleteMapping("/clean")
    public R<String> deleteR(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清空成功！！！");
    }
}
