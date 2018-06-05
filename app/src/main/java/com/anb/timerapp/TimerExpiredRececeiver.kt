package com.anb.timerapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.anb.timerapp.util.NotificationUtil
import com.anb.timerapp.util.PrefUtil

class TimerExpiredRececeiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)
        PrefUtil.setTimerState(TimerActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}
