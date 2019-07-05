package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EasyUI_Image {

    private Integer error = 0 ;  //表示上传图片是否发生错误
    private String url ;  //图片的虚拟路径
    private Integer width;
    private Integer height;

}
