package com.travel.point.service

import com.travel.point.service.param.PointChannelDto

interface PointService {
    fun calculatePoint(request: PointChannelDto)
}
