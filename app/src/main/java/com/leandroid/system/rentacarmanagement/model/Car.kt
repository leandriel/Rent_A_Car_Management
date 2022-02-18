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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Car

        if (id != other.id) return false
        if (patent != other.patent) return false
        if (model != other.model) return false
        if (brand != other.brand) return false
        if (active != other.active) return false
        if (color != other.color) return false
        if (comment != other.comment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + patent.hashCode()
        result = 31 * result + model.hashCode()
        result = 31 * result + brand.hashCode()
        result = 31 * result + active.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + comment.hashCode()
        return result
    }
}