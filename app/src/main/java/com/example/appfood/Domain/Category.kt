package com.example.appfood.Domain

class Category {
    var Id: Int = 0
    var ImagePath: String? = null
    var Name: String? = null

    constructor() {}

    constructor(id: Int, imagePath: String?, name: String?) {
        this.Id = id
        this.ImagePath = imagePath
        this.Name = name
    }
}
