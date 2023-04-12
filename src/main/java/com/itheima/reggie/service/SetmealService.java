package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    //保存新增套餐需要保存的套餐和菜品的关联关系
    public void saveWithDish(SetmealDto setmealDto);
    //删除套餐
    public void deleteWithDish(List<Long> ids);
    //修改套餐
    public SetmealDto getWithDish(Long id);
    //修改套餐数据保存
    public void updateWithDish(SetmealDto setmealDto);
}
