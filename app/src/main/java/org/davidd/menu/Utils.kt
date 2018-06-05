package org.davidd.menu

fun generateOrderId(): Long {
    return System.currentTimeMillis() % 1000
}