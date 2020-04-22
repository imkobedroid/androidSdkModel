package com.ilongyuan.lysdk.injection.module

import com.ilongyuan.lysdk.service.Service
import com.ilongyuan.lysdk.service.impl.ServiceImpl
import dagger.Module
import dagger.Provides

/**
 * @author Dsh  imkobedroid@gmail.com
 * @date 2019-07-09
 */

@Module
class ServiceModule {
    @Provides
    fun provideService(service: ServiceImpl): Service {
        return service
    }
}