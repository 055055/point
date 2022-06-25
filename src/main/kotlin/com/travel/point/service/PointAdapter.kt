package com.travel.point.service

import com.travel.point.service.param.PointChannelDto
import com.travel.point.type.ReviewActionType
import org.springframework.stereotype.Service

@Service
class PointAdapter(
    private val pointService: PointService
) {
    fun calculatePoint(request: PointChannelDto) {
        when (request.review.actionType) {
            ReviewActionType.ADD -> pointService.add(request)
            ReviewActionType.MOD -> pointService.modify(request)
            ReviewActionType.DELETE -> pointService.delete(request)
        }
    }
}
