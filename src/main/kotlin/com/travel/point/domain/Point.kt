package com.travel.point.domain

import com.travel.point.type.PointType

class Point(user:User, score: Int, type: PointType) {
    var user: User = user
    val score: Int = score
    val type: PointType = type

    fun  isGreatherThanZero(): Boolean {
        return score > 0
    }
}
