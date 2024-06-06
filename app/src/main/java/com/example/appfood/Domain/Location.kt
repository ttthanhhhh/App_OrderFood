package com.example.appfood.Domain

class Location {
    var id: Int = 0
    var loc: String? = null

    constructor() {}

    override fun toString(): String {
        return loc ?: ""
    }
}
