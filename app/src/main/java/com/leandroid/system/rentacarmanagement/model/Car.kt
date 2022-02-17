package com.leandroid.system.rentacarmanagement.model

data class Car(
    val id: String,
    var patent: String,
    var model: String,
    var brand: Brand,
    var active: Boolean,
    var color: Color,
    var comment: String
){
    constructor(): this("", "", "", Brand(), false, Color(), "")
    val carDetails: String
      get() = "${brand.name} $model ${color.name} - $patent".uppercase()

    val isRequiredEmptyData: Boolean
    get() = patent.isEmpty() || model.isEmpty() || brand.name.isEmpty() || color.name.isEmpty()
}