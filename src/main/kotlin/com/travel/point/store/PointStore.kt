package com.travel.point.store

import com.travel.point.domain.Point
import com.travel.point.domain.Review

interface PointStore {
    fun addPoint(point: Point, review: Review)
    fun deletePoint(point: Point, review: Review)
    fun modifyPoint(point: Point, review: Review)
}

