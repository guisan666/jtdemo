package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * vo 是服务器数据与页面进行交互的对象,一般要转换为json
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUI_Tree {
    private Long id;    //分类id
    private String text;    //分类信息
    private String state = "closed";    //open 打开节点,closed关闭节点

}
