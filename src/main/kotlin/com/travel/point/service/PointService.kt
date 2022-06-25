package com.travel.point.service

import com.travel.point.service.param.PointChannelDto

interface PointService {
    fun add(request: PointChannelDto)
    fun modify(request: PointChannelDto)
    fun delete(request: PointChannelDto)
}


