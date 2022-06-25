package com.travel.point.store

import com.travel.point.domain.Point
import com.travel.point.domain.Review
import com.travel.point.domain.User

interface PointStore {
    fun addPoint(point: Point, review: Review, comment: String)
    fun deletePoint(point: Point, review: Review, comment: String)
    fun modifyReview(review: Review)
    fun saveReview(review: Review): Review
    fun getReviewBonusPoint(review: Review): Point
    fun getRollbackReviewBonusPoint(review: Review): Point
    fun deleteReview(review: Review): Review
    fun getLastReviewPointHistory(review: Review): Point
    fun getPoint(user: User): Point
}
