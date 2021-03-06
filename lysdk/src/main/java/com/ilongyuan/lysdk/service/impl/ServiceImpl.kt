package com.ilongyuan.lysdk.service.impl

import com.ilongyuan.lysdk.entity.*
import com.ilongyuan.lysdk.ext.convert
import com.ilongyuan.lysdk.repository.DataRepository
import com.ilongyuan.lysdk.service.Service
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Dsh  imkobedroid@gmail.com
 * @date 2019-07-09
 */
class ServiceImpl @Inject constructor() : Service {


    @Inject
    lateinit var dataRepository: DataRepository


    override fun queryPlayList(
        num: Int?,
        size: Int?,
        userId: String,
        roomId: String?,
        mediaAction: String,
        sort: String?
    ): Flowable<List<MusicInfo>> {
        return dataRepository.queryPlayList(num, size, userId, roomId, mediaAction, sort).convert()
    }

    override fun queryCount(userId: String): Flowable<MusicCount> {
        return dataRepository.queryCount(userId).convert()
    }


    override fun initSdk(isAnchor: Boolean, userId: String, userName: String): Flowable<SdkInfo> {
        return dataRepository.initSDK(isAnchor, userId, userName).convert()
    }


    override fun addSong(
        userId: String,
        roomId: String?,
        musicNo: String,
        mediaAction: String
    ): Flowable<AddSongBean> {
        return dataRepository.addSong(userId, roomId, musicNo, mediaAction).convert()
    }

    override fun deleteSong(
        musicNo: String,
        userId: String,
        roomId: String?,
        mediaAction: String?
    ): Flowable<DeleteSongBean> {
        return dataRepository.deleteSong(musicNo, userId, roomId, mediaAction).convert()
    }

    override fun label(current: Int?, size: Int?): Flowable<List<MusicTag>> {
        return dataRepository.label(current, size).convert()
    }

    override fun searchSong(
        current: Int?,
        size: Int?,
        tag: String,
        keyword: String
    ): Flowable<List<SearchMusicInfo>> {
        return dataRepository.searchSong(current, size, tag, keyword).convert()

    }

    override fun resourceAcquisition(
        musicNo: String,
        userId: String,
        userName: String,
        roomId: String?,
        mediaAction: String
    ): Flowable<MusicResource> {
        return dataRepository.resourceAcquisition(musicNo, userId, userName, roomId, mediaAction)
            .convert()

    }


    override fun recommended(current: Int?, size: Int?): Flowable<List<RecommendMusic>> {
        return dataRepository.recommend(current, size).convert()

    }

}