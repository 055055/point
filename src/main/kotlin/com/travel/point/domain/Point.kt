package com.travel.point.domain

class Point(user: User, point: Int) {
    val user: User = user
    val score: Int = point
    var totalScore: Int = 0
}
