package com.ilongyuan.lysdk.repository

import com.ilongyuan.lysdk.entity.*
import com.ilongyuan.lysdk.net.RetrofitFactory
import com.ilongyuan.lysdk.protocol.BaseResp
import io.reactivex.Flowable
import javax.inject.Inject


/**
 * @author Dsh  imkobedroid@gmail.com
 * @date 2019-07-09
 */
class DataRepository @Inject constructor() {


    /**
     * 获取授权信息/ SDK初始化信息
     */
    fun initSDK(isAnchor: Boolean, userId: String, userName: String): Flowable<BaseResp<SdkInfo>> {
        return RetrofitFactory.Api().initSDK(userId, isAnchor, userName)
    }

    /**
     * 查询主播歌单
     */
    fun queryPlayList(
        num: Int?,
        size: Int?,
        userId: String,
        roomId: String?,
        mediaAction: String,
        sort: String?
    ): Flowable<BaseResp<List<MusicInfo>>> {
        return RetrofitFactory.Api()
            .queryPlayList(num?.toLong(), size, userId, roomId, mediaAction, sort)
    }

    /**
     * 添加歌单，k歌/听歌
     */
    fun addSong(
        userId: String,
        roomId: String?,
        musicNo: String,
        mediaAction: String
    ): Flowable<BaseResp<AddSongBean>> {
        val json = AddSongJson(userId, roomId, musicNo, mediaAction)
        return RetrofitFactory.Api().addSong(json)
    }

    /**
     * 歌单删除
     */
    fun deleteSong(
        musicNo: String,
        userId: String,
        roomId: String?,
        mediaAction: String?
    ): Flowable<BaseResp<DeleteSongBean>> {
        return RetrofitFactory.Api().deleteSong(musicNo, userId, roomId, mediaAction)
    }

    /**
     * 歌曲标签
     */
    fun label(current: Int?, size: Int?): Flowable<BaseResp<List<MusicTag>>> {
        return RetrofitFactory.Api().label(current, size)
    }


    /**
     * 歌曲搜索
     */
    fun searchSong(
        current: Int?,
        size: Int?,
        tag: String,
        keyword: String
    ): Flowable<BaseResp<List<SearchMusicInfo>>> {
        return RetrofitFactory.Api().searchSong(current, size, tag, keyword)
    }

    /**
     * 音乐点播/音乐播放资源获取
     */
    fun resourceAcquisition(
        musicNo: String,
        userId: String,
        userName: String,
        roomId: String?,
        mediaAction: String
    ): Flowable<BaseResp<MusicResource>> {
        return RetrofitFactory.Api().resource(musicNo, userId, userName, roomId, mediaAction)
    }

    /**
     * 歌曲推荐
     */
    fun recommend(
        current: Int?,
        size: Int?
    ): Flowable<BaseResp<List<RecommendMusic>>> {
        return RetrofitFactory.Api().recommend(current, size)
    }

    /**
     * 获取歌曲统计
     */
    fun queryCount(userId: String): Flowable<BaseResp<MusicCount>> {
        return RetrofitFactory.Api().queryCount(userId)

    }
}

