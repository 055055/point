package com.travel.point.domain

import com.travel.point.type.PointType

class Point(user: User, score: Int) {
    val user: User = user
    val score: Int = score
    var type: PointType? = null
    val isGreaterThanZero: Boolean
        get() = score > 0
}
