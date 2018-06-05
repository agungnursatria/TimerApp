package com.anb.timerapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.anb.timerapp.util.NotificationUtil
import com.anb.timerapp.util.PrefUtil

class TimerNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action){
            AppConstant.ACTION_STOP -> {
                TimerActivity.removeAlarm(context)
                PrefUtil.setTimerState(TimerActivity.TimerState.Stopped, context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstant.ACTION_PAUSE -> {
                var secondRemaining = PrefUtil.getSecondsRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = TimerActivity.nowSeconds

                secondRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondRemaining, context)

                TimerActivity.removeAlarm(context)
                PrefUtil.setTimerState(TimerActivity.TimerState.Paused, context)
                NotificationUtil.showTimerPaused(context)
            }
            AppConstant.ACTION_RESUME -> {
                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeUpTime = TimerActivity.setAlarm(context, TimerActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(TimerActivity.TimerState.Running, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
            AppConstant.ACTION_START -> {
                val minuteRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minuteRemaining * 60L
                val wakeUpTime = TimerActivity.setAlarm(context, TimerActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(TimerActivity.TimerState.Running, context)
                PrefUtil.setSecondsRemaining(secondsRemaining, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
        }
    }
}
