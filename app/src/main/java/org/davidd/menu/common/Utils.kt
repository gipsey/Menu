package org.davidd.menu.common

fun generateOrderId(): Long {
    return System.currentTimeMillis() % 1000
}