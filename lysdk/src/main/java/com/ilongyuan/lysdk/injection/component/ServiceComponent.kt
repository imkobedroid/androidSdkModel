package com.ilongyuan.lysdk.injection.component

import com.ilongyuan.lysdk.controller.BaseController
import com.ilongyuan.lysdk.injection.CustomScope
import com.ilongyuan.lysdk.injection.module.ServiceModule
import dagger.Component

/**
 * @author Dsh  imkobedroid@gmail.com
 * @date 2019-07-09
 */

@CustomScope
@Component(modules = [ServiceModule::class])
interface ServiceComponent {
    fun inject(service: BaseController)
}