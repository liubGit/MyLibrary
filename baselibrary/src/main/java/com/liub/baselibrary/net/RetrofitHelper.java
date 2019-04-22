package com.liub.baselibrary.net;

import android.util.Log;

import com.liub.baselibrary.common.Constants;
import com.liub.baselibrary.rx.RxSchedulers;
import com.liub.baselibrary.utils.DateUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by liub on 2019/4/8
 * Describe:
 */
public class RetrofitHelper {
    private String TAG = "RetrofitHelper";
    private static RetrofitHelper instances;
    private Long CONNECT_TIMEOUT = 60L;
    private Long READ_TIMEOUT = 30L;
    private Long WHITE_TIMEOUT = 30L;
    public Retrofit mRetrofit;

    public RetrofitHelper() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_L)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WHITE_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(new commonInteptor())
                .addInterceptor(new RqInterceptor())
                .addInterceptor(new LogInterceptor())
                .build();
    }


    public static RetrofitHelper getInstance() {
        if (null == instances) {
            synchronized (RetrofitHelper.class) {
                if (instances == null) {
                    instances = new RetrofitHelper();
                }
            }
        }
        return instances;
    }

    /**
     * 添加请求头
     */
    private class RqInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * 日志打印
     */
    private class LogInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
            String time = DateUtils.INSTANCE.getNowDateFormat(DateUtils.INSTANCE.getDATE_FORMAT_2());
            String url = request.url().toString();
            String params = requestBodyToString(request.body());
            String requestLog = "**请求时间:" + time + " **URL:" + url + " **参数:" + params + "\n";
            Log.e(TAG, requestLog);
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            ResponseBody responseString=response.peekBody((long) (1024*1024));
//            String responseString = JsonUtils.toJsonForNote(response.body().toString());
            long t = (t2 - t1);
            String log = "\n**耗时:$t **返回参数:" + responseString.string() + " \n \n";
            Log.e(TAG, log);
            return chain.proceed(request);
        }
    }

    private String requestBodyToString(RequestBody body) {
        Buffer buffer = null;
        try {
            buffer = new Buffer();
            if (body != null) {
                body.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (IOException e) {
            return "did not work";
        } finally {
            if (buffer != null) {
                buffer.close();
            }
        }
    }

    /**
     * 公共参数
     */
    private class commonInteptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder()
//                    .addQueryParameter("key", "ecce8a3ef508f54cc1905af133f5b3a5")
//                    .addQueryParameter("t", "1543210514862")
                    .addQueryParameter("key", "0db6ffd00372064035ef33763dd1c61e")
                    .addQueryParameter("t", "1547700576328")
                    .build();
            return chain.proceed(request.newBuilder().url(url).build());
        }
    }

    /**
     * data中无嵌套
     */
    public <T> DisposableObserver<BaseResult<T>> doCommonRequest(Observable<BaseResult<T>> observable, final BaseObserverListener<T> listener) {
        return observable
                .compose(RxSchedulers.INSTANCE.<BaseResult<T>>io_main())
                .subscribeWith(new DisposableObserver<BaseResult<T>>() {
                    @Override
                    public void onNext(BaseResult<T> t) {
                        if (t.getCode() == 200) {
                            listener.onSuccess(t.getData());
                        } else {
                            listener.onBusinessError(t.getCode(), t.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
    }

    /**
     * data中嵌套了数据
     */
    public <T> DisposableObserver<BaseComplexResult<T>> doRequestOther(Observable<BaseComplexResult<T>> observable, final BaseObserverListener<T> listener) {
        return observable
                .compose(RxSchedulers.INSTANCE.<BaseComplexResult<T>>io_main())
                .subscribeWith(new DisposableObserver<BaseComplexResult<T>>() {
                    @Override
                    public void onNext(BaseComplexResult<T> t) {
                        if (t.getCode() == 200) {
                            if (t.getData().getContent() != null) {
                                listener.onSuccess(t.getData().getContent());
                            } else {
                                listener.onSuccess((T) t.getData());
                            }
                        } else {
                            listener.onBusinessError(t.getCode(), t.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });

    }

    /**
     * 未封装json请求
     */
    public <T> DisposableObserver<T> doRequest(Observable<T> observable, final BaseObserverListener<T> listener) {
        return observable
                .compose(RxSchedulers.INSTANCE.<T>io_main())
                .subscribeWith(new DisposableObserver<T>() {
                    @Override
                    public void onNext(T t) {
                        listener.onSuccess(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }

                });
    }


}
