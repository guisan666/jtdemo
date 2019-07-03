package com.jt.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;
//定义公共的pojo对象
@Data
@Accessors(chain = true)
public class BasePOJO implements Serializable{
	private static final long serialVersionUID = -4310081728105382718L;
	private Date created;
	private Date updated;

}
