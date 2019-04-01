package dict;

import java.net.*;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.System;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dict {
  private static final String YOUDAO_URL = "http://openapi.youdao.com/api";
  private static final String APP_KEY = "14240d2ebe4b8336";
  private static final String APP_SECRET = "STdxLa3CuwSxeeWrFzSVbLlVoC8VN94c";

  public static void run(String ...args) {
    try {
      String params = buildParams(args[0]);
      request(params);
    }catch(IOException e){
      System.out.println("异常：" + e.getMessage());
    }
  }

  private static void request(String params) throws IOException {
    URL url = new URL(YOUDAO_URL);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    StringBuffer content = new StringBuffer();
    String inputLine;

    try {
      con.setRequestMethod("GET");
      con.setDoOutput(true);
      DataOutputStream out = new DataOutputStream(con.getOutputStream());
      out.writeBytes(params);
      out.flush();
      out.close();
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
      in.close();
    }catch(IOException e){
      System.out.println(e.getMessage());
    }finally{
      con.disconnect();
    }

    print(content.toString());
  }

  private static void print(String content) {
    JsonAccepter jsonAccepter = new Gson().fromJson(content, JsonAccepter.class); 
    System.out.println(
      Arrays.stream(jsonAccepter.translation).collect(Collectors.joining(", "))
      );
  }

  private static String buildParams(String q) throws UnsupportedEncodingException {
    Map<String, String> params = new HashMap<>();
    params.put("q", q);
    params.put("from", "auto");
    params.put("to", "auto");
    params.put("signType", "v3");
    String salt = String.valueOf(System.currentTimeMillis());
    String curtime = String.valueOf(System.currentTimeMillis() / 1000);
    String signStr = APP_KEY + q + salt + curtime + APP_SECRET;
    String sign = getDigest(signStr);
    params.put("appKey", APP_KEY);
    params.put("salt", salt);
    params.put("sign", sign);
    params.put("curtime", curtime);

    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
      result.append("&");
    }
    return result.toString();
  }

  public static String getDigest(String string) {
    if (string == null) {
        return null;
    }
    char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    byte[] btInput = string.getBytes();
    try {
        MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
        mdInst.update(btInput);
        byte[] md = mdInst.digest();
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (byte byte0 : md) {
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    } catch (NoSuchAlgorithmException e) {
        return null;
    }
  }
}