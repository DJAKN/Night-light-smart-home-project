package com.coderqi.publicutil.voice;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.text.TextUtils;

//import com.iflytek.speech.ErrorCode;
//import com.iflytek.speech.SpeechError;
/**
 * ¶ÔÔÆ¶Ë·µ»ØµÄJson½á¹û½øÐÐ½âÎö
 * @author iFlytek
 * @since 20131211
 */
public class JsonParser {
	
	/**
	 * ÌýÐ´½á¹ûµÄJson¸ñÊ½½âÎö
	 * @param json
	 * @return
	 */
	public static String parseIatResult(String json) {
		if(TextUtils.isEmpty(json))
			return "";
		
		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			JSONArray words = joResult.getJSONArray("ws");
			for (int i = 0; i < words.length(); i++) {
				// ÌýÐ´½á¹û´Ê£¬Ä¬ÈÏÊ¹ÓÃµÚÒ»¸ö½á¹û
				JSONArray items = words.getJSONObject(i).getJSONArray("cw");
				JSONObject obj = items.getJSONObject(0);
				ret.append(obj.getString("w"));
//				Èç¹ûÐèÒª¶àºòÑ¡½á¹û£¬½âÎöÊý×éÆäËû×Ö¶Î
//				for(int j = 0; j < items.length(); j++)
//				{
//					JSONObject obj = items.getJSONObject(j);
//					ret.append(obj.getString("w"));
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ret.toString();
	}
	
	/**
	 * Ê¶±ð½á¹ûµÄJson¸ñÊ½½âÎö
	 * @param json
	 * @return
	 */
	public static String parseGrammarResult(String json) {
		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			JSONArray words = joResult.getJSONArray("ws");
			for (int i = 0; i < words.length(); i++) {
				JSONArray items = words.getJSONObject(i).getJSONArray("cw");
				for(int j = 0; j < items.length(); j++)
				{
					JSONObject obj = items.getJSONObject(j);
					if(obj.getString("w").contains("nomatch"))
					{
						ret.append("Ã»ÓÐÆ¥Åä½á¹û.");
						return ret.toString();
					}
					ret.append("¡¾½á¹û¡¿" + obj.getString("w"));
					ret.append("¡¾ÖÃÐÅ¶È¡¿" + obj.getInt("sc"));
					ret.append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.append("Ã»ÓÐÆ¥Åä½á¹û.");
		} 
		return ret.toString();
	}
	
	/**
	 * ÓïÒå½á¹ûµÄJson¸ñÊ½½âÎö
	 * @param json
	 * @return
	 */
	public static String parseUnderstandResult(String json) {
		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			ret.append("¡¾Ó¦´ðÂë¡¿" + joResult.getString("rc") + "\n");
			ret.append("¡¾×ªÐ´½á¹û¡¿" + joResult.getString("text") + "\n");
			ret.append("¡¾·þÎñÃû³Æ¡¿" + joResult.getString("service") + "\n");
			ret.append("¡¾²Ù×÷Ãû³Æ¡¿" + joResult.getString("operation") + "\n");
			ret.append("¡¾ÍêÕû½á¹û¡¿" + json);
		} catch (Exception e) {
			e.printStackTrace();
			ret.append("Ã»ÓÐÆ¥Åä½á¹û.");
		} 
		return ret.toString();
	}
}
