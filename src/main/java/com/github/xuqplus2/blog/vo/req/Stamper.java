package com.github.xuqplus2.blog.vo.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class Stamper implements Serializable {
	private Integer docIndex;
	private Integer pageIndex;
	private Integer top;
	private Integer left;
	private Integer width;
	private Integer height;
	private Integer rotateAngle;
	private Long sealId;
}
