package com.travel.point.service

import com.travel.point.domain.User
import com.travel.point.service.param.PointRequest
import com.travel.point.service.param.PointResult
import com.travel.point.type.PointEventType

interface PointService {
    suspend fun add(request: PointRequest)
    suspend fun modify(request: PointRequest)
    suspend fun delete(request: PointRequest)
    fun eventType(): PointEventType
    suspend fun getPoint(user: User): PointResult
}
