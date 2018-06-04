package com.dalimao.mytaxi;

/**
 * Created by liuguangli on 17/4/24.
 */
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestOkHttp3 {
    /**
     * 测试 OkHttp Get 方法；主要分3步；
     */
    @Test
    public void testGet() {
        // 创建 OkHttpClient 对象
        OkHttpClient client = new OkHttpClient();
        // 创建 Request 对象
        Request request = new Request.Builder()
                .url("http://httpbin.org/get?id=id")
                .build();
        // OkHttpClient 执行 Request
        try {
            Response response = client.newCall(request).execute();
            System.out.println("response:" + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 OkHttp Post 方法
     */
    @Test
    public void testPost() {
        // 创建 OkHttpClient 对象
        OkHttpClient client = new OkHttpClient();
        // 创建 Request 对象
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        // Post请求还需要一个请求体 RequestBody，而RequestBody又需要一个MediaType
        RequestBody body = RequestBody.create(mediaType, "{\"name\": \"taiangu\"}");
        Request request = new Request.Builder()
                .url("http://httpbin.org/post")// 请求行
                //.header(); // 请求头
                .post(body) // 请求体
                .build();
        // OkHttpClient 执行 Request
        try {
            Response response = client.newCall(request).execute();
            System.out.println("response:" + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  测试拦截器
     */
    @Test
    public void testInterceptor() {
        //  定义拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                long start = System.currentTimeMillis();
                // 截获请求
                Request request  = chain.request();
                // 进行处理，把request往下传递
                Response response = chain.proceed(request);
                long end = System.currentTimeMillis();
                System.out.println("interceptor: cost time = " + (end - start));
                System.out.println("拦截器: 请求的耗时（重发起请求到获得响应） = " + (end - start) + "毫秒");
                return response;
            }
        };
        // 创建 OkHttpClient 对象
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)    // 添加拦截器
                .build();
        // 创建 Request 对象
        Request request = new Request.Builder()
                .url("http://httpbin.org/get?id=id")
                .build();
        // OkHttpClient 执行 Request
        try {
            Response response = client.newCall(request).execute();
            System.out.println("response:" + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  测试缓存
     */
    @Test
    public void testCache() {
        // 创建缓存对象
        // 创建缓存的文件目录cache.cache，缓存的大小为1MB
        Cache cache = new Cache(new File("cache.cache"), 1024 * 1024);
            // 创建 OkHttpClient 对象
            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(cache)
                    .build();
            // 创建 Request 对象
            Request request = new Request.Builder()
                    .url("http://httpbin.org/get?id=id")
                    .cacheControl(CacheControl.FORCE_NETWORK)   // 强制从网络获取，不从缓存获取
                    .build();
            // OkHttpClient 执行 Request
            try {
                Response response = client.newCall(request).execute();
                Response responseCache = response.cacheResponse();  // 从缓存获取数据
                Response responseNet = response.networkResponse();  // 从网络获取数据
                if (responseCache != null) {
                    // 从缓存响应
                    System.out.println("response from cache 从缓存获取");
                }
                if (responseNet != null) {
                    // 从缓存响应
                    System.out.println("response from net 从网络获取");
                }

                System.out.println("response:" + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
