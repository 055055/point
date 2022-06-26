package com.travel.point.service

import com.travel.point.domain.User
import com.travel.point.service.param.PointRequest
import com.travel.point.service.param.PointResult
import com.travel.point.type.PointEventType

interface PointService {
    fun add(request: PointRequest)
    fun modify(request: PointRequest)
    fun delete(request: PointRequest)
    fun eventType(): PointEventType
    fun getPoint(user: User): PointResult
}
