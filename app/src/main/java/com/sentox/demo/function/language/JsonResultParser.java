package com.sentox.demo.function.language;

/**
 * 返回的json数据解析器
 * 
 * @author lishen
 */
public class JsonResultParser {

	private static final String FIELD_LANG = "lang";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_URL = "url";
	private static final String FIELD_TOKEN = "token";

	/**
	 * 解析语言包下发数组
	 * 
	 * @param json
	 * @return
	 */
//	public static LinkedHashMap<String, LanguageBean> parsePackageList(
//			Context ctx, String json) {
//		LinkedHashMap<String, LanguageBean> beans = new LinkedHashMap<String, LanguageBean>();
//		try {
//			JSONArray array = new JSONArray(json);
//			final int length = array.length();
//			for (int i = 0; i < length; i++) {
//				JSONObject obj = array.optJSONObject(i);
//				LanguageBean bean = parseLangObject(ctx, obj);
//				if (bean != null) {
//					beans.put(bean.getKeyCode(), bean);
//				}
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return beans;
//	}

	/**
	 * 解析当个语言包对象
	 * 
	 * @param ctx
	 * @param obj
	 * @return
//	 */
//	private static LanguageBean parseLangObject(Context ctx, JSONObject obj) {
//		LanguageBean bean = null;
//		if (obj != null) {
//			String keycode = obj.optString(FIELD_LANG);
//			if (!TextUtils.isEmpty(keycode)) { // 确保语言码有效
//				bean = new LanguageBean();
//				String[] codes = parseKeyCode(keycode);
//				bean.setLanguageCode(codes[0]);
//				bean.setCountryCode(codes[1]);
//				bean.setInternal(false);
//				bean.setInstalled(LanguageFileUtil.isLanguageInstalled(ctx,
//						keycode));
//				bean.setDisplayName(obj.optString(FIELD_NAME));
//				bean.setDownloadUrl(obj.optString(FIELD_URL));
//				bean.setMd5(obj.optString(FIELD_TOKEN));
//			}
//		}
//		return bean;
//	}

	/**
	 * 解释语言唯一码(开如en_US, 或en)
	 * 
	 * @param keyCode
	 * @return String[] codes, languageCode = codes[0], countryCode = codes[1]<br>
	 */
	public static String[] parseKeyCode(String keyCode) {
		String[] codes = new String[2];
		if (keyCode.contains("_")) {
			String[] params = null;
			try {
				params = keyCode.split("_");
			} catch (Exception e) {
//				if (Loger.DEBUG) {
					e.printStackTrace();
//				}
			}
			if (null != params && params.length == 2) {
				codes[0] = params[0];
				codes[1] = params[1];
			}
		} else {
			codes[0] = keyCode;
			codes[1] = "";
		}
		return codes;
	}

}
