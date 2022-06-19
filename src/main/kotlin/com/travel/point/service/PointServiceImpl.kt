package com.travel.point.service

import com.travel.point.domain.Point
import com.travel.point.domain.Review
import com.travel.point.service.param.PointChannelDto
import com.travel.point.store.PointStore
import com.travel.point.type.ActionType
import org.springframework.stereotype.Service

@Service
class PointServiceImpl(
    private val pointStore: PointStore
) : PointService {
    override fun calculatePoint(request: PointChannelDto) {
        when (request.review.actionType) {
            ActionType.ADD -> add(request)
            ActionType.MOD -> modify(request)
            ActionType.DELETE -> delete(request)
            else -> getAllByUser(request)
        }
    }

    private fun add(request: PointChannelDto) {
        pointStore.addPoint(Point(request.review))

    }

    private fun modify(request: PointChannelDto) {
        TODO("Not yet implemented")
    }

    private fun delete(request: PointChannelDto) {
        TODO("Not yet implemented")
    }

    private fun getAllByUser(request: PointChannelDto) {
        TODO("Not yet implemented")
    }
}
