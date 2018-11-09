package com.ncity.app.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ResponseBean {
	
	/**
	 * 返回码
	 */
	@ApiModelProperty(value = "返回码", required = false, example="返回码")
	private int code;
	/**
	 * 返回信息
	 */
	@ApiModelProperty(value = "返回信息", required = false, example="返回信息")
	private String message;
	/**
	 * 返回数据
	 */
	@ApiModelProperty(value = "返回数据", required = false, example="返回数据")
	private Object data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
