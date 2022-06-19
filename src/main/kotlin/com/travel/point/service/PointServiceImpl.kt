package com.travel.point.service

import com.travel.point.domain.Point
import com.travel.point.service.param.PointChannelDto
import com.travel.point.store.PointStore
import com.travel.point.type.ReviewActionType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointServiceImpl(
    private val pointStore: PointStore
) : PointService {

    @Transactional
    override fun calculatePoint(request: PointChannelDto) {
        when (request.review.actionType) {
            ReviewActionType.ADD -> add(request)
            ReviewActionType.MOD -> modify(request)
            ReviewActionType.DELETE -> delete(request)
            else -> getAllByUser(request)
        }
    }

    private fun add(request: PointChannelDto) {
        val review = request.review
        val bonusPoint = pointStore.getBonusPoint(review)
        pointStore.addPoint(Point(review.user, bonusPoint.addScore(review.calculateScore())), review)
    }

    private fun modify(request: PointChannelDto) {


    }

    private fun delete(request: PointChannelDto) {
        val review = request.review
        val point = pointStore.deletePointReview(review)
        pointStore.subtractPoint(point,review)
    }

    private fun getAllByUser(request: PointChannelDto) {
        TODO("Not yet implemented")
    }
}
