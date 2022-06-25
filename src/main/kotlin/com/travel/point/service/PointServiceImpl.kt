package com.travel.point.service

import com.travel.point.service.param.PointChannelDto
import com.travel.point.store.PointStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointServiceImpl(
    private val pointStore: PointStore
) : PointService {
    @Transactional
    override fun add(request: PointChannelDto) {
        val review = pointStore.saveReview(request.review)
        pointStore.addPoint(
            point = review.convertToPoint(),
            review = review,
            comment = "리뷰 이벤트 포인트 추가"
        )
        val bonusPoint = pointStore.getReviewBonusPoint(review)
        if (bonusPoint.isGreatherThanZero()) {
            pointStore.addPoint(
                point = bonusPoint,
                review = review,
                comment = "여행지 첫 방문 보너스 포인트 추가"
            )
        }
    }

    @Transactional
    override fun delete(request: PointChannelDto) {
        val review = pointStore.deleteReview(request.review)
        pointStore.deletePoint(
            point = review.convertToPoint(),
            review = review,
            comment = "리뷰 이벤트 포인트 삭제"
        )
        val bonusPoint = pointStore.getRollbackReviewBonusPoint(review)
        if (bonusPoint.isGreatherThanZero()) {
            pointStore.deletePoint(
                point = bonusPoint,
                review = review,
                comment = "여행지 첫 방문 보너스 포인트 삭제"
            )
        }
    }

    @Transactional
    override fun modify(request: PointChannelDto) {
        val review = request.review
        val point = pointStore.getLastReviewPointHistory(review)
        pointStore.deletePoint(
            point = point,
            review = review,
            comment = "리뷰 변경으로 인한 직전 포인트 삭제"
        )

        pointStore.modifyReview(review)
        pointStore.addPoint(
            point = review.convertToPoint(),
            review = review,
            comment = "리뷰 이벤트 포인트 추가"
        )
    }

    private fun getAllByUser(request: PointChannelDto) {
        TODO("Not yet implemented")
    }
}
