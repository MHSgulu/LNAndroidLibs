package com.lnkj.android.demo.ui;

import com.lnkj.libs.core.BaseViewModelExtKt;
import com.lnkj.libs.net.ThrowableExtKt;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import rxhttp.wrapper.param.RxHttp;

public class TestApi {

    public void api() {

        Pair<String, Object>[] params = new Pair[]{
                new Pair<String, Object>("key", "value")
        };
        BaseViewModelExtKt.okRequest("url", "tag", params,
                () -> {
                    // 加载中
                    return null;
                }, () -> {
                    // 请求成功
                    return null;
                }, (msg, code) -> {
                    // 请求失败
                    return null;
                });

        Map<String, Object> params1 = new HashMap<>();
        RxHttp.postForm("")
                .tag("tag")
                .addAll(params1)
                .asResponseList(String.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    // 加载中
                })
                .subscribe(result -> {
                    // 请求成功 result返回结果
                }, throwable -> {
                    // 请求失败
                    String msg = ThrowableExtKt.getMsg(throwable);
                    String code = ThrowableExtKt.getCode(throwable);

                });

    }

}
