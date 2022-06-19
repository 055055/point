package com.travel.point.domain

data class Point(private val review: Review) {
    val user: User = review.user
    val point: Int = review.calculateScore()
}
