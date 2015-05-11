package com.xunlei.apple2.test.cases;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.MalformedURLException;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.xunlei.apple2.test.modules.BaseCase;

public class OCR extends BaseCase {
	private String imageId;

	@Test
	public void upload() throws MalformedURLException {
		File file = new File("res/QQ (2).png");
		long size = file.length();
		excutor.setHttpParam("size", String.valueOf(size));
		excutor.setHttpParam("type", "jpg");
		JSONObject result = excutor.getJsonRespWithMulti("ocr.upload", file);
		assertNotNull(result);
		imageId = result.getString("imageId");
	}

	@Test
	public void result() {
		excutor.setHttpParam("imageId", "6");
		JSONObject result = excutor.getJsonResp("ocr.result");
		assertNotNull(result);
	}
}
