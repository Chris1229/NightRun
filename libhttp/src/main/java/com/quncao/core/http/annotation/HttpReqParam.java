package com.quncao.core.http.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ClassName: HttpReqParam.java
 * @Description: 协议名和Response类型
 * 
 * @author pengjin
 * @version V1.0
 * @Date 2015-10-24 下午4:44:59
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpReqParam {

	String protocal();

	Class<?> responseType();

	HttpReqParam.HttpReqMethod method() default HttpReqParam.HttpReqMethod.HTTP_POST;

	HttpReqParam.DataFormat format() default HttpReqParam.DataFormat.JSON;

	public static enum DataFormat {
		MAP,
		JSON;

		private DataFormat() {
		}
	}

	public static enum HttpReqMethod {
		HTTP_GET,
		HTTP_POST;

		private HttpReqMethod() {
		}
	}
}
