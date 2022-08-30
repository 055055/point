package com.travel.point.service

import com.travel.point.constants.*
import com.travel.point.domain.User
import com.travel.point.service.param.PointRequest
import com.travel.point.service.param.PointResult
import com.travel.point.store.PointStore
import com.travel.point.type.PointEventType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointReviewServiceImpl(
    private val pointStore: PointStore
) : PointService {
    override fun eventType(): PointEventType = PointEventType.REVIEW

    override suspend fun getPoint(user: User) =
        PointResult(
            point = pointStore.getPoint(user)
        )

    @Transactional
    override suspend fun add(request: PointRequest) {
        val review = pointStore.saveReview(request.review)
        pointStore.addPoint(
            point = review.convertToPoint(),
            review = review,
            comment = REVIEW_POINT_ADD
        )
        val bonusPoint = pointStore.getReviewBonusPoint(review)
        if (bonusPoint.isGreaterThanZero) {
            pointStore.addPoint(
                point = bonusPoint,
                review = review,
                comment = REVIEW_BONUS_POINT_ADD
            )
        }
    }

    @Transactional
    override suspend fun delete(request: PointRequest) {
        val review = pointStore.deleteReview(request.review)
        pointStore.deletePoint(
            point = review.convertToPoint(),
            review = review,
            comment = REVIEW_POINT_SUBTRACT
        )
        val bonusPoint = pointStore.getRollbackReviewBonusPoint(review)
        if (bonusPoint.isGreaterThanZero) {
            pointStore.deletePoint(
                point = bonusPoint,
                review = review,
                comment = REVIEW_BONUS_POINT_SUBTRACT
            )
        }
    }

    @Transactional
    override suspend fun modify(request: PointRequest) {
        val review = request.review
        val point = pointStore.getLastReviewPointHistory(review)
        pointStore.deletePoint(
            point = point,
            review = review,
            comment = REVIEW_POINT_SUBTRACT_BY_MODIFICATION
        )

        pointStore.modifyReview(review)
        pointStore.addPoint(
            point = review.convertToPoint(),
            review = review,
            comment = REVIEW_POINT_ADD
        )
    }
}
