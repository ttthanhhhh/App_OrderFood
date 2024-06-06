package com.example.appfood.Domain

class Price {
    var id: Int = 0
    var Value: String? = null

    constructor() {}

    override fun toString(): String {
        return Value ?: ""
    }
}
