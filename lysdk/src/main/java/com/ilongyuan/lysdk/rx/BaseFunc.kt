package com.ilongyuan.lysdk.rx

import com.ilongyuan.lysdk.common.BaseConstance.Companion.SUCCEED
import com.ilongyuan.lysdk.common.BaseException
import com.ilongyuan.lysdk.protocol.BaseResp
import io.reactivex.Flowable
import io.reactivex.functions.Function

/**
 * @author Dsh  imkobedroid@gmail.com
 * @date 2019-07-09
 */
class BaseFunc<T> : Function<BaseResp<T>, Flowable<T>> {
    override fun apply(t: BaseResp<T>): Flowable<T> {
        if (t.code != SUCCEED) {
            return Flowable.error(BaseException(t.code, t.msg))
        }
        return if (t.data != null) Flowable.just(t.data) else Flowable.error(
            BaseException(
                t.code,
                t.msg
            )
        )
    }
}