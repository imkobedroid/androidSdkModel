package com.ilongyuan.lysdk.controller

import android.content.Context
import com.ilongyuan.lysdk.common.BaseConstance.Companion.SUCCEED
import com.ilongyuan.lysdk.common.BaseException
import com.ilongyuan.lysdk.dagger.DaggerServiceComponent
import com.ilongyuan.lysdk.entity.*
import com.ilongyuan.lysdk.ext.request
import com.ilongyuan.lysdk.hInterface.DataResponse
import com.ilongyuan.lysdk.injection.module.ServiceModule
import com.ilongyuan.lysdk.rx.BaseSubscribe
import com.ilongyuan.lysdk.widget.CircleProgressDialog


/**
 * @author Dsh  imkobedroid@gmail.com
 * @date 2019-07-09
 */
class LyManager(val context: Context) : BaseController() {


    init {
        DaggerServiceComponent.builder().serviceModule(ServiceModule()).build().inject(this)
    }

    override fun creditUser(
        context: Context,
        isAnchor: Boolean,
        userId: String,
        userName: String,
        response: DataResponse
    ) {
        if (!checkNetWork(context, response)) {
            return
        }
        mService.initSdk(isAnchor, userId, userName)
            .request(object : BaseSubscribe<SdkInfo>(response) {
                override fun onNext(t: SdkInfo) {
                    super.onNext(t)
                    response.data(t)
                }


            })
    }


    override fun musicSmallerThan(
        context: Context,
        num: Int?,
        size: Int?,
        userId: String,
        roomId: String?,
        mediaAction: String,
        response: DataResponse
    ) {
        if (!checkNetWork(context, response)) {
            return
        }
        mService.queryPlayList(num, size, userId, roomId, mediaAction, "ASC")
            .request(object : BaseSubscribe<List<MusicInfo>>(response) {
                override fun onNext(t: List<MusicInfo>) {
                    super.onNext(t)
                    response.data(t)
                }
            })
    }

    override fun musicBiggerThan(
        context: Context,
        num: Int?,
        size: Int?,
        userId: String,
        roomId: String?,
        mediaAction: String,
        sort: String?,
        response: DataResponse
    ) {
        if (!checkNetWork(context, response)) {
            return
        }
        mService.queryPlayList(num, size, userId, roomId, mediaAction, "DESC")
            .request(object : BaseSubscribe<List<MusicInfo>>(response) {
                override fun onNext(t: List<MusicInfo>) {
                    super.onNext(t)
                    response.data(t)
                }
            })
    }


    override fun queryPlayList(
        context: Context,
        num: Int?,
        size: Int?,
        userId: String,
        roomId: String?,
        mediaAction: String,
        sort: String?,
        response: DataResponse
    ) {
        if (!checkNetWork(context, response)) {
            return
        }
        mService.queryPlayList(num, size, userId, roomId, mediaAction, sort)
            .request(object : BaseSubscribe<List<MusicInfo>>(response) {
                override fun onNext(t: List<MusicInfo>) {
                    super.onNext(t)
                    response.data(t)
                }
            })
    }


    override fun addToPlayList(
        context: Context,
        userId: String,
        roomId: String?,
        musicNo: String,
        mediaAction: String,
        response: DataResponse
    ) {
        val dialog = CircleProgressDialog(context)
        dialog.showDialog()
        if (!checkNetWork(context, response)) {
            if (dialog.isShowing) dialog.dismiss()
            return
        }
        mService.addSong(userId, roomId, musicNo, mediaAction)
            .request(object : BaseSubscribe<AddSongBean>(response) {
                override fun onNext(t: AddSongBean) {
                    super.onNext(t)
                    response.data(t)
                }

                override fun onError(t: Throwable?) {
                    super.onError(t)
                    if (dialog.isShowing) dialog.dismiss()
                    when (t) {
                        is BaseException -> when (SUCCEED) {
                            t.status -> response.data(t)
                        }
                    }
                }


                override fun onComplete() {
                    super.onComplete()
                    if (dialog.isShowing) dialog.dismiss()
                }
            })
    }


    override fun deleteFromPlayList(
        context: Context,
        musicNo: String,
        userId: String,
        roomId: String?,
        mediaAction: String?,
        response: DataResponse
    ) {
        val dialog = CircleProgressDialog(context)

        dialog.showDialog()
        if (!checkNetWork(context, response)) {
            if (dialog.isShowing) dialog.dismiss()
            return
        }

        mService.deleteSong(musicNo, userId, roomId, mediaAction)
            .request(object : BaseSubscribe<DeleteSongBean>(response) {
                override fun onNext(t: DeleteSongBean) {
                    super.onNext(t)
                    response.data(t)

                }


                override fun onError(t: Throwable?) {
                    super.onError(t)
                    if (dialog.isShowing) dialog.dismiss()
                    when (t) {
                        is BaseException -> when (SUCCEED) {
                            t.status -> response.data(t)
                        }
                    }
                }

                override fun onComplete() {
                    super.onComplete()
                    if (dialog.isShowing) dialog.dismiss()
                }
            })
    }

    override fun musicTags(context: Context, current: Int?, size: Int?, response: DataResponse) {
        if (!checkNetWork(context, response)) {
            return
        }
        mService.label(current, size)
            .request(object : BaseSubscribe<List<MusicTag>>(response) {
                override fun onNext(t: List<MusicTag>) {
                    super.onNext(t)
                    response.data(t)
                }
            })
    }

    override fun searchMusicByTag(
        context: Context,
        current: Int?,
        size: Int?,
        tag: String,
        keyword: String,
        response: DataResponse
    ) {

        if (!checkNetWork(context, response)) {
            return
        }
        mService.searchSong(current, size, tag, keyword)
            .request(object : BaseSubscribe<List<SearchMusicInfo>>(response) {
                override fun onNext(t: List<SearchMusicInfo>) {
                    super.onNext(t)
                    response.data(t)
                }
            })
    }


    override fun resource(
        context: Context,
        musicNo: String,
        userId: String,
        userName: String,
        roomId: String?,
        mediaAction: String,
        response: DataResponse
    ) {
        val dialog = CircleProgressDialog(context)

        dialog.showDialog()
        if (!checkNetWork(context, response)) {
            if (dialog.isShowing) dialog.dismiss()
            return
        }
        mService.resourceAcquisition(musicNo, userId, userName, roomId, mediaAction)
            .request(object : BaseSubscribe<MusicResource>(response) {
                override fun onNext(t: MusicResource) {
                    super.onNext(t)
                    response.data(t)
                }

                override fun onError(t: Throwable?) {
                    super.onError(t)
                    if (dialog.isShowing) dialog.dismiss()
                }


                override fun onComplete() {
                    super.onComplete()
                    if (dialog.isShowing) dialog.dismiss()
                }
            })

    }

    override fun recommendMusic(
        context: Context,
        current: Int?,
        size: Int?,
        response: DataResponse
    ) {
        val dialog = CircleProgressDialog(context)

        dialog.showDialog()
        if (!checkNetWork(context, response)) {
            if (dialog.isShowing) dialog.dismiss()
            return
        }
        mService.recommended(current, size)
            .request(object : BaseSubscribe<List<RecommendMusic>>(response) {
                override fun onNext(t: List<RecommendMusic>) {
                    super.onNext(t)
                    response.data(t)
                }

                override fun onError(t: Throwable?) {
                    super.onError(t)
                    if (dialog.isShowing) dialog.dismiss()
                }

                override fun onComplete() {
                    super.onComplete()
                    if (dialog.isShowing) dialog.dismiss()
                }
            })
    }


    override fun musicCount(context: Context, userId: String, dataResponse: DataResponse) {
        if (!checkNetWork(context, dataResponse)) {
            return
        }
        mService.queryCount(userId)
            .request(object : BaseSubscribe<MusicCount>(dataResponse) {
                override fun onNext(t: MusicCount) {
                    super.onNext(t)
                    dataResponse.data(t)
                }
            })
    }


}

