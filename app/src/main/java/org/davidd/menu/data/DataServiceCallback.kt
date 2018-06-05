package org.davidd.menu.data

interface DataServiceCallback<T> {

    fun onLoadFailed()
    fun onLoadSucceeded(data: T)
}