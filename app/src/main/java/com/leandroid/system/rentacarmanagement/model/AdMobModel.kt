package com.leandroid.system.rentacarmanagement.model

data class AdMobModel(
    val id: Int = 1222123
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdMobModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}