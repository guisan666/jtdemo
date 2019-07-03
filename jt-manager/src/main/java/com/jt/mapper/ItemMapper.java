package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemMapper extends BaseMapper<Item>{


    /**
     * 查询数据库时,要求最近的记录在最前方 ,倒叙
     * @param start
     * @param rows
     * @return
     */
    @Select("select * from tb_item order by updated desc limit #{start},#{rows}")
    List<Item> findItemPages(@Param("start") Integer start, @Param("rows") Integer rows);
}
