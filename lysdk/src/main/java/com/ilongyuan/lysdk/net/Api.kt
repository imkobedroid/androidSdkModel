package com.ilongyuan.lysdk.net

import com.ilongyuan.lysdk.entity.*
import com.ilongyuan.lysdk.protocol.BaseResp
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * @author Dsh  imkobedroid@gmail.com
 * @date 2019-07-09
 */
interface Api {


    @GET("/v1/grants/info")
    fun initSDK(
        @Query("userId") userId: String,
        @Query("anchor") anchor: Boolean,
        @Query("name") name: String
    ): Flowable<BaseResp<SdkInfo>>


    @GET("/v1/anchor/musics")
    fun queryPlayList(
        @Query("number") number: Long?,
        @Query("size") size: Int?,
        @Query("userId") userId: String,
        @Query("roomId") roomId: String?,
        @Query("mediaAction") mediaAction: String?,
        @Query("sort") sort: String?
    ): Flowable<BaseResp<List<MusicInfo>>>


    @GET("/v1/musics/tags")
    fun label(
        @Query("current") current: Int?,
        @Query("size") size: Int?
    ): Flowable<BaseResp<List<MusicTag>>>

    @GET("/v1/musics/search")
    fun searchSong(
        @Query("current") current: Int?,
        @Query("size") size: Int?,
        @Query("tag") tag: String,
        @Query("keyword") keyword: String
    ): Flowable<BaseResp<List<SearchMusicInfo>>>


    @GET("/v1/musics/{musicNo}")
    fun resource(
        @Path("musicNo") musicNo: String,
        @Query("userId") userId: String,
        @Query("userName") userName: String,
        @Query("roomId") roomId: String?,
        @Query("mediaAction") mediaAction: String
    ): Flowable<BaseResp<MusicResource>>

    @GET("/v1/musics/recommend")
    fun recommend(
        @Query("current") current: Int?,
        @Query("size") size: Int?
    ): Flowable<BaseResp<List<RecommendMusic>>>


    @GET("/v1/anchor/musics/count")
    fun queryCount(
        @Query("userId") userId: String
    ): Flowable<BaseResp<MusicCount>>


    @POST("/v1/anchor/musics")
    fun addSong(
        @Body addSongJson: AddSongJson
    ): Flowable<BaseResp<AddSongBean>>


    @DELETE("/v1/anchor/musics/{musicNo}")
    fun deleteSong(
        @Path("musicNo") musicNo: String,
        @Query("userId") userId: String,
        @Query("roomId") roomId: String?,
        @Query("mediaAction") mediaAction: String?
    ): Flowable<BaseResp<DeleteSongBean>>


}
