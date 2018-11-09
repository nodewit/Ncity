package com.ncity.app.yunpian;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ncity.app.uitls.RedisUtils;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

/**
 * 云片网API调用
 * @author 艾克
 * 2018年10月15日 11点59分
 */
@Component
public class YunpianClientApi {
	
	@Value("${apikey}")
	private String apikey;
	@Value("${appName}")
	private String appName;
	@Value("${min}")
	private int min;
	
	
	/**
	 * 发送短信
	 * @param mobile 手机号
	 * @return
	 */
	public int send(String mobile) {
		//初始化client,apikey作为所有请求的默认值(可以为空)
		YunpianClient clnt = new YunpianClient(apikey).init();
		//发送短信API
		Map<String, String> param = clnt.newParam(2);
		param.put(YunpianClient.MOBILE, mobile);
		//模板：【节点数智网络科技】亲爱的#name#，您的验证码是#code#。有效期为#hour#，请尽快验证
		StringBuilder text = new StringBuilder("【");
		text.append(appName);
		text.append("】");
		text.append("亲爱的用户，");
		text.append("您的验证码是");
		String code = random();
		text.append(code);
		text.append("。有效期为");
		text.append(min);
		text.append("分钟，请尽快验证");
		param.put(YunpianClient.TEXT, text.toString());
		//获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()
		//clnt.post("https://sms.yunpian.com/v2/sms/single_send.json", param);
		Result<SmsSingleSend> r = clnt.sms().single_send(param);
		if(r.getCode() == 0){
			RedisUtils.set(mobile, code, 5, TimeUnit.MINUTES);
		}
		//释放clnt
		clnt.close();
		return r.getCode();
	}
	
	public static String random() {
		Random rand = new Random();
		String code = "";
		for (int i = 0; i < 6; i++) {
			int r = rand.nextInt(10);
			code += r;
		}
		return code;
	}
}
