package com.cris15.xl.util;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Author: Cris
 * Date: 2021/07/06
 * Time: 17:37
 * Project: demotest
 * Description:  get,post工具类
 **/
public class HttpUtils {

    /**
     * get请求
     * @param url
     * @param param   example:（index=1 & age=2）
     * @return
     */
    public static String doGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    /**
     * post请求
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 发送短信工具
     * @param phoneNumber     手机号码
     * @param timeOut       短信有效时间
     * @param verifiedCode      验证码
     * @return
     */
    public static SmsMultiSenderResult sendMessage(String[] phoneNumber,String timeOut,String verifiedCode){
        SmsMultiSenderResult result = null;
        try {
            // 短信应用 SDK AppID
            int appid = 1400561028; // SDK AppID 以1400开头
            // 短信应用 SDK AppKey
            String appkey = "599ad28a0643e7f07136d6fbf69938ae";
            // 需要发送短信的手机号码
            String[] phoneNumbers = phoneNumber;
            // 短信模板 ID，需要在短信应用中申请
            int templateId = 1085146; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
            // 签名
            String smsSign = "Cris个人博客"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请
            String code = verifiedCode;
            String[] params = {code, timeOut};
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            result = msender.sendWithParam("86", phoneNumbers, templateId, params, smsSign, "", "");
        }catch (HTTPException e) {
            //HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            //JSON解析错误
            e.printStackTrace();
        } catch (IOException e) {
            //网络IO错误
            e.printStackTrace();
        }
        return result;
    }
}