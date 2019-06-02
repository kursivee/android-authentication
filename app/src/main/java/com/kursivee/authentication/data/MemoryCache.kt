package com.kursivee.authentication.data

class MemoryCache {
    private val cache = mutableMapOf<Any, Any>()

    fun put(key: Any, value: Any) {
        cache[key] = value
    }

    fun get(key: Any): Any? = cache.get(key)

    fun remove(key: Any) {
        cache.remove(key)
    }

    fun clear() {
        cache.clear()
    }
}
