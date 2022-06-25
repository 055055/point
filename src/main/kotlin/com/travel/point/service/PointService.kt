package com.travel.point.service

import com.travel.point.service.param.PointChannelDto
import com.travel.point.type.PointEventType

interface PointService {
    fun add(request: PointChannelDto)
    fun modify(request: PointChannelDto)
    fun delete(request: PointChannelDto)
    fun eventType():PointEventType
}
