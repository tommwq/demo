package com.tq.myapplication.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tq.myapplication.database.BarRepository
import com.tq.abc.Manager

class BarManager private constructor(private val repository: BarRepository):
Manager<com.tq.myapplication.domain.Bar,com.tq.myapplication.viewmodel.Bar,com.tq.myapplication.database.Bar>(repository) {

    override fun fromViewModel(viewModel: com.tq.myapplication.viewmodel.Bar): com.tq.myapplication.domain.Bar {
        val domainModel = com.tq.myapplication.domain.Bar(this)
        fromViewModel(viewModel, domainModel)
        return domainModel
    }    
    override fun fromViewModel(viewModel: com.tq.myapplication.viewmodel.Bar, domainModel: com.tq.myapplication.domain.Bar) {
    }    
    override fun toViewModel(domainModel: com.tq.myapplication.domain.Bar, viewModel: com.tq.myapplication.viewmodel.Bar) {
    }
    override fun toViewModel(domainModel: com.tq.myapplication.domain.Bar): com.tq.myapplication.viewmodel.Bar {
        val viewModel = com.tq.myapplication.viewmodel.Bar()
        toViewModel(domainModel, viewModel)
        return viewModel
    }
    override fun fromEntity(entity: com.tq.myapplication.database.Bar): com.tq.myapplication.domain.Bar {
        val domainModel = com.tq.myapplication.domain.Bar(this)
        fromEntity(entity, domainModel)
        return domainModel
    }
    override fun fromEntity(entity: com.tq.myapplication.database.Bar, domainModel: com.tq.myapplication.domain.Bar) {
    }
    override fun toEntity(domainModel: com.tq.myapplication.domain.Bar, entity: com.tq.myapplication.database.Bar) {
    }
    override fun toEntity(domainModel: com.tq.myapplication.domain.Bar): com.tq.myapplication.database.Bar {
        val entity = com.tq.myapplication.database.Bar()
        toEntity(domainModel, entity)
        return entity
    }
    override fun createDomainModel(): com.tq.myapplication.domain.Bar {
        return com.tq.myapplication.domain.Bar(this)
    }
    companion object {
        private var instance: BarManager? = null
        fun get(repository: BarRepository): BarManager {
            return instance ?: synchronized(this) {
                instance ?: BarManager(repository)
            }
        }
    }
}
