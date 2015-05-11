package com.xunlei.apple2.test.cases;

import static org.junit.Assert.*;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.xunlei.apple2.test.modules.BaseCase;

public class SyncAppDir extends BaseCase{

	@Test
	public void test() {
		excutor.setHttpParam("platform", "android");
		excutor.setHttpParam("channel", "outer");
		excutor.setHttpParam("opVer", "-1");
		excutor.setHttpParam("limit", "10");
		JSONObject result = excutor.getJsonResp("scandir.open.syncAppDir");
		assertNotNull(result);
	}

}
