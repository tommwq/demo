package com.tq.myapplication.domain

import com.tq.abc.DomainModel
import com.tq.myapplication.manager.BarManager

class Bar(manager: BarManager): DomainModel<com.tq.myapplication.domain.Bar, com.tq.myapplication.viewmodel.Bar,com.tq.myapplication.database.Bar>(manager) {
}
