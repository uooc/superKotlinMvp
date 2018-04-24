/*
 * Copyright (C) 2017 Ricky.yao https://github.com/vihuela
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 */

package uooconline.com.nucleus.utils.eventbus

import org.greenrobot.eventbus.EventBus

data class Event<T>(var code: Int, var data: T) {

    companion object {
        fun obtain(code: Int): Event<*> = Event(code, null)
        fun <T> obtain(code: Int, data: T): Event<T> = Event(code, data)
    }
}


fun Any.sendEvent(event: Event<*>) {
    if (event.code != -1)
        EventBus.getDefault().post(event)
}

fun Any.sendEventSticky(event: Event<*>) {
    if (event.code != -1)
        EventBus.getDefault().postSticky(event)
}

fun Any.removeEventSticky(event: Event<*>) {
    EventBus.getDefault().removeStickyEvent(event)
}
