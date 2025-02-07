package com.chooloo.www.koler.interactor.navigation

import com.chooloo.www.koler.data.account.SimAccount
import com.chooloo.www.koler.interactor.base.BaseInteractor

interface NavigationInteractor : BaseInteractor<NavigationInteractor.Listener> {
    interface Listener

    fun goToRateApp()
    fun goToSendEmail()
    fun goToAppGithub()
    fun goToDonatePage()
    fun goToMainActivity()
    fun goToReportBugPage()
    fun goToManageBlockedNumbers()

    fun goToSendSMS(number: String?)
    fun goToAddContact(number: String)

    fun goToViewContact(contactId: Long)
    fun goToEditContact(contactId: Long)

    fun callVoicemail()
    fun call(number: String)
    fun call(simAccount: SimAccount?, number: String)
}