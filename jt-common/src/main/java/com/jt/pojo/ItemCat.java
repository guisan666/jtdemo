package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_item_cat")
public class ItemCat  extends BasePOJO{

    @TableId(type = IdType.AUTO)
    private  Long id;
    private Long parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;  //商品分类排序
    private Boolean isParent;   //是否为父级

}
