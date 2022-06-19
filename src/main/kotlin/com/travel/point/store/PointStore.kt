package com.travel.point.store

import com.travel.point.domain.Point

interface PointStore {
    fun addPoint(point: Point)
}
