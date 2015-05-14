package com.xunlei.apple2.test.utils;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

/**
 * http请求
 * 
 * @author peiyu
 * 
 */
public class HttpTools {
	/**
	 * POST请求
	 * 
	 * @param url
	 *            ，请求的地址
	 * @param map
	 *            ，请求的参数Map
	 * @return 返回JSON串形式的结果
	 */
	public static JSONObject SendPostRequest(String url, Map<String, String> map) {
		JSONObject resultJsonObject = null;
		System.out.println("请求地址：" + url);
		CloseableHttpClient client = HttpClientBuilder.create().build();
		try {
			HttpPost post = new HttpPost(url);
			addHeaders(post);
			JSONObject jsonParam = new JSONObject();
			Set<String> keySet = map.keySet();
			for (String s : keySet) {
				if (!"".equals(map.get(s))) {
					jsonParam.put(s, map.get(s));
				}
			}
			StringEntity entity = new StringEntity(jsonParam.toString(),"UTF-8");
			entity.setContentType("application/json");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			int responseCode = response.getStatusLine().getStatusCode();
			System.out.println("HttpStatusCode: " + responseCode);
			String resultString = EntityUtils.toString(response.getEntity());
			resultJsonObject = JSONObject.fromObject(resultString);
			if (resultJsonObject == null) {
				fail("response error, body is null...");
			}
			System.out.println("返回结果：" + resultJsonObject);
			post.releaseConnection();
			client.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultJsonObject;
	}

	/**
	 * Multipart方式上传文件
	 * 
	 * @param sUrl
	 *            ，请求的地址
	 * @param file
	 *            ，上传的文件
	 * @param params
	 *            ，请求的参数
	 * @return 返回JSON串形式的结果
	 */
	@SuppressWarnings("deprecation")
	public static JSONObject SendMultiRequest(String sUrl,
			Map<String, String> params, File file) {
		String boundary = "Boundary-RedApple2-fysn73kxb";
		JSONObject resultJsonObject = null;

		try {
			MultipartEntity multipartEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE, boundary,
					Charset.defaultCharset());
			Set<String> keySet = params.keySet();
			for (String s : keySet) {
				multipartEntity.addPart(s,
						new StringBody(params.get(s), Charset.forName("UTF-8")));
			}
			multipartEntity.addPart("data", new FileBody(file));

			CloseableHttpClient cilent = HttpClients.createDefault();
			HttpPost post = new HttpPost(sUrl);
			post.addHeader("Content-Type", "multipart/form-data;boundary="
					+ boundary);
			post.setEntity(multipartEntity);

			CloseableHttpResponse response = cilent.execute(post);

			int responseCode = response.getStatusLine().getStatusCode();
			System.out.println("HttpStatusCode: " + responseCode);
			String resultString = EntityUtils.toString(response.getEntity());
			resultJsonObject = JSONObject.fromObject(resultString);
			if (resultJsonObject == null) {
				fail("response error, body is null...");
			}
			System.out.println("返回结果：" + resultJsonObject);
			response.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultJsonObject;
	}

	private static void addHeaders(HttpPost post) {
		post.setHeader("x-xl-retry-times", Constant.RETRY_TIMES);
		post.setHeader("x-xl-device", Constant.DEVICE);
		post.setHeader("x-xl-device-model", Constant.DEVICE_MODEL);
		post.setHeader("x-xl-device-os", Constant.DEVICE_OS);
		post.setHeader("x-xl-channel", Constant.CHANNEL);
		post.setHeader("x-xl-client", Constant.CLIENT);
		post.setHeader("x-xl-client-version", Constant.CLIENT_VERSION);
		post.setHeader("x-xl-userid", Constant.USERID);
	}
}
