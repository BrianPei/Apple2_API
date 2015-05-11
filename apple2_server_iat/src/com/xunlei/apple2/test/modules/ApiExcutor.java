package com.xunlei.apple2.test.modules;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.xunlei.apple2.test.utils.Constant;
import com.xunlei.apple2.test.utils.HttpTools;
import net.sf.json.JSONObject;

public class ApiExcutor {
	public Map<String, String> http_param;

	public void setHttpParam(String key, String value) {
		http_param.put(key, value);
	}

	public ApiExcutor() {
		http_param = new HashMap<String, String>();
	}

	public JSONObject getJsonResp(String api) {
		JSONObject resultObject = HttpTools.SendPostRequest(
				CombineUrl(Constant.ONLINE_URL, api), http_param);
		http_param.clear();
		return resultObject;
	}

	public JSONObject getJsonRespWithMulti(String api, File file)
			throws MalformedURLException {
		JSONObject resultObject = HttpTools.SendMultiRequest(
				CombineUrl(Constant.ONLINE_URL, api), http_param, file);
		http_param.clear();
		return resultObject;
	}

	public static String CombineUrl(String sUrl, String apiName) {
		if (!sUrl.endsWith("/")) {
			sUrl += "/";
		}
		String urlEnd = apiName.replace('.', '/');
		String resultUrl = sUrl + urlEnd;
		return resultUrl;
	}
	
	public static String map2Str(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			sb.append(key);
			sb.append("=");
			try {
				sb.append(URLEncoder.encode(map.get(key), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			sb.append("&");
		}
		if (!map.isEmpty())
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
