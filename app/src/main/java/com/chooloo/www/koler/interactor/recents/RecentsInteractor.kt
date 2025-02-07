package com.chooloo.www.koler.interactor.recents

import com.chooloo.www.koler.data.account.Recent
import com.chooloo.www.koler.interactor.base.BaseInteractor

interface RecentsInteractor : BaseInteractor<RecentsInteractor.Listener> {
    interface Listener

    fun queryRecent(recentId: Long): Recent?
    fun queryRecent(recentId: Long, callback: (Recent?) -> Unit)
    fun deleteRecent(recentId: Long)
}