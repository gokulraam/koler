package com.chooloo.www.koler.interactor.callaudio

import android.telecom.CallAudioState
import android.telecom.CallAudioState.*
import androidx.annotation.StringRes
import com.chooloo.www.koler.R
import com.chooloo.www.koler.interactor.base.BaseInteractor

interface CallAudioInteractor : BaseInteractor<CallAudioInteractor.Listener> {
    interface Listener {
        fun onMuteChanged(isMuted: Boolean)
        fun onAudioRouteChanged(audioRoute: AudioRoute)
    }

    val supportedAudioRoutes: Array<AudioRoute>

    var isMuted: Boolean?
    var isSpeakerOn: Boolean?
    var audioRoute: AudioRoute?


    fun entryCallAudioStateChanged(audioState:CallAudioState)


    enum class AudioRoute(val route: Int, @StringRes val stringRes: Int) {
        SPEAKER(ROUTE_SPEAKER, R.string.audio_route_speaker),
        EARPIECE(ROUTE_EARPIECE, R.string.audio_route_earpiece),
        BLUETOOTH(ROUTE_BLUETOOTH, R.string.audio_route_bluetooth),
        WIRED_HEADSET(ROUTE_WIRED_HEADSET, R.string.audio_route_wired_headset),
        WIRED_OR_EARPIECE(ROUTE_WIRED_OR_EARPIECE, R.string.audio_route_wired_or_earpiece);

        companion object {
            fun fromRoute(route: Int?) = values().firstOrNull { it.route == route }
        }
    }
}