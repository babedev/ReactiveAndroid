package com.github.kittinunf.reactiveandroid.subscription

import android.os.Looper
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import rx.Subscription
import rx.subscriptions.BooleanSubscription

/**
 * Created by Kittinun Vantasin on 2/1/16.
 */

class AndroidMainThreadSubscription(private val unsubscriber: () -> Unit) : Subscription {

    val subscription = BooleanSubscription.create {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            unsubscriber()
        } else {
            AndroidThreadScheduler.mainThreadScheduler.createWorker()?.schedule(unsubscriber)
        }
    }

    override fun isUnsubscribed() = subscription.isUnsubscribed

    override fun unsubscribe() {
        subscription.unsubscribe()
    }

}