/*
 * 官网地站:http://www.ShareSDK.cn
 * �?��支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第�?��间�?过微信将版本更新内容推�?给您。如果使用过程中有任何问题，也可以�?过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013�?Shacn.sharesdk.demo.videoghts reserved.
 */

package cuit.travelweather.activity;

import java.util.HashMap;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * OneKeyShareCallback是快捷分享功能的�?��“外部回调�?示例�? *演示了如何�?过添加extra的方法，将快捷分享的分享结果回调�?
 * *外面来做自定义处理�?
 */
public class OneKeyShareCallback implements PlatformActionListener {

	@Override
	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {
		System.out.println(res.toString());
		// 在这里添加分享成功的处理代码
	}

	@Override
	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();
		// 在这里添加分享失败的处理代码
	}

	@Override
	public void onCancel(Platform plat, int action) {
		// 在这里添加取消分享的处理代码
	}

}
