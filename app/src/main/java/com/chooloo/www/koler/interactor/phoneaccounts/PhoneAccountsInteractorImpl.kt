package com.chooloo.www.koler.interactor.phoneaccounts

import android.content.Context
import com.chooloo.www.koler.contentresolver.PhoneLookupContentResolver
import com.chooloo.www.koler.contentresolver.PhonesContentResolver
import com.chooloo.www.koler.data.account.PhoneAccount
import com.chooloo.www.koler.data.account.PhoneLookupAccount
import com.chooloo.www.koler.interactor.base.BaseInteractorImpl
import io.reactivex.exceptions.OnErrorNotImplementedException

class PhoneAccountsInteractorImpl(private val context: Context) :
    BaseInteractorImpl<PhoneAccountsInteractor.Listener>(), PhoneAccountsInteractor {

    override fun lookupAccount(number: String?, callback: (PhoneLookupAccount) -> Unit) {
        try {
            PhoneLookupContentResolver(context, number).queryContent { phones ->
                callback.invoke(phones.getOrNull(0) ?: PhoneLookupAccount(null, number))
            }
        } catch (e: OnErrorNotImplementedException) {
            callback.invoke(PhoneLookupAccount(null, number))
        }
    }

    override fun getContactAccounts(contactId: Long, callback: (Array<PhoneAccount>?) -> Unit) {
        PhonesContentResolver(context, contactId).queryContent {
            callback.invoke(it.toTypedArray())
        }
    }
}