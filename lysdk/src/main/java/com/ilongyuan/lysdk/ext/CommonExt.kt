package com.ilongyuan.lysdk.ext

import com.ilongyuan.lysdk.protocol.BaseResp
import com.ilongyuan.lysdk.rx.BaseFunc
import com.ilongyuan.lysdk.rx.BaseSubscribe
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Dsh  imkobedroid@gmail.com
 * @date 2019-07-09
 */

fun <T> Flowable<T>.execute(subscribe: BaseSubscribe<T>) {
    this.onBackpressureDrop()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscribe)
}

fun <T> Flowable<T>.request(subscribe: BaseSubscribe<T>) {
    execute(subscribe)
}


fun <T> Flowable<BaseResp<T>>.convert(): Flowable<T> {
    return this.flatMap(BaseFunc())
}
