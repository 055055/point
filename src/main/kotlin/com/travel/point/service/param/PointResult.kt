package com.travel.point.service.param

import com.travel.point.domain.Point
import com.travel.point.web.param.PointResponse

data class PointResult(
    val point: Point
) {
    fun convertToPointResponse() =
        PointResponse(
            userId = point.user.id,
            point = point.score
        )
}
