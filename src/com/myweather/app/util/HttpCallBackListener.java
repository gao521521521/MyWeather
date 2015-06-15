package com.myweather.app.util;

public interface HttpCallBackListener {
	public void onFinish(String response);

	public void onError(Exception e);
}
