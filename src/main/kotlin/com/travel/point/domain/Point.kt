package com.travel.point.domain

import com.travel.point.type.PointType

class Point(val user: User, val score: Int) {
    var type: PointType? = null
    val isGreaterThanZero: Boolean
        get() = score > 0
}
