package com.travel.point.service

import com.travel.point.domain.Point
import com.travel.point.service.param.PointChannelDto
import com.travel.point.store.PointStore
import com.travel.point.type.ActionType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointServiceImpl(
    private val pointStore: PointStore
) : PointService {

    @Transactional
    override fun calculatePoint(request: PointChannelDto) {
        when (request.review.actionType) {
            ActionType.ADD -> add(request)
            ActionType.MOD -> modify(request)
            ActionType.DELETE -> delete(request)
            else -> getAllByUser(request)
        }
    }

    private fun add(request: PointChannelDto) {
        val review = request.review
        val bonusPoint = pointStore.getBonusPoint(review)
        Point(review.user, bonusPoint.addScore(review.calculateScore()))
        pointStore.addPoint(Point(review.user, review.calculateScore()), review)
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
