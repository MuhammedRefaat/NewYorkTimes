package com.mango.nytimes.network

import com.mango.nytimes.models.NYTimesNewsViewModel
import dagger.Component

@Component(modules = [APIsClient::class])
interface ApiComponent {

    fun inject(service: APIsCaller)

    fun inject(viewModel: NYTimesNewsViewModel)

}