package com.travel.point.service.param

import com.travel.point.domain.Review
import com.travel.point.type.PointEventType

data class PointRequest(
    val pointEventType: PointEventType,
    val review: Review
)
