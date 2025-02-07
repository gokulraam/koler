package com.chooloo.www.koler.interactor.numbers

import android.content.ContentValues
import android.content.Context
import android.provider.BlockedNumberContract
import android.provider.BlockedNumberContract.BlockedNumbers
import android.telecom.TelecomManager
import com.chooloo.www.koler.interactor.base.BaseInteractorImpl
import com.chooloo.www.koler.util.annotation.RequiresDefaultDialer

class NumbersInteractorImpl(
    private val context: Context,
    private val telecomManager: TelecomManager
) : BaseInteractorImpl<NumbersInteractor.Listener>(), NumbersInteractor {
    @RequiresDefaultDialer
    override fun isNumberBlocked(number: String) =
        BlockedNumberContract.isBlocked(context, number)

    @RequiresDefaultDialer
    override fun blockNumber(number: String) {
        if (isNumberBlocked(number)) return
        val contentValues = ContentValues()
        contentValues.put(BlockedNumbers.COLUMN_ORIGINAL_NUMBER, number)
        context.contentResolver.insert(BlockedNumbers.CONTENT_URI, contentValues)
    }

    @RequiresDefaultDialer
    override fun unblockNumber(number: String) {
        if (!isNumberBlocked(number)) return
        val contentValues = ContentValues()
        contentValues.put(BlockedNumbers.COLUMN_ORIGINAL_NUMBER, number)
        context.contentResolver.insert(BlockedNumbers.CONTENT_URI, contentValues)?.also {
            context.contentResolver.delete(it, null, null)
        }
    }
}