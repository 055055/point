package com.travel.point.store

import com.travel.point.domain.BonusPoint
import com.travel.point.domain.Point
import com.travel.point.domain.Review

interface PointStore {
    fun addPoint(point: Point, review: Review)
    fun getBonusPoint(review: Review): BonusPoint
    fun deletePointReview(review: Review):Point
    fun subtractPoint(point: Point, review: Review)
}
