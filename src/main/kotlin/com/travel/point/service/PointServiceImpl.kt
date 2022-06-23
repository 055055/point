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
        }
    }

    private fun add(request: PointChannelDto) {
        val review = request.review
        pointStore.addPoint(Point(review.user, review.calculateScore()), review)
    }

    private fun modify(request: PointChannelDto) {
        val review = request.review
        pointStore.modifyPoint(Point(review.user, review.calculateScore()), review)

    }

    private fun delete(request: PointChannelDto) {
        val review = request.review
        val point = Point(review.user, review.calculateScore())
         pointStore.deletePoint(point, review)
    }

    private fun getAllByUser(request: PointChannelDto) {
        TODO("Not yet implemented")
    }
}
