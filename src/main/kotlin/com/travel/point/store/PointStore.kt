package com.travel.point.store

import com.travel.point.domain.Point
import com.travel.point.domain.Review
import com.travel.point.domain.User

interface PointStore {
    suspend fun addPoint(point: Point, review: Review, comment: String)
    suspend fun deletePoint(point: Point, review: Review, comment: String)
    suspend fun modifyReview(review: Review)
    suspend fun saveReview(review: Review): Review
    suspend fun getReviewBonusPoint(review: Review): Point
    suspend fun getRollbackReviewBonusPoint(review: Review): Point
    suspend fun deleteReview(review: Review): Review
    suspend fun getLastReviewPointHistory(review: Review): Point
    suspend fun getPoint(user: User): Point
}
