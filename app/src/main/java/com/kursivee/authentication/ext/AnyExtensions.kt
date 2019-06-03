package com.kursivee.authentication.ext

inline fun <reified T> Any.castOrNull(): T? =
    try { this as T? } catch (t: Throwable) { null }