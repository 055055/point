package com.travel.point.store

import com.travel.point.constants.ONE
import com.travel.point.constants.ZERO
import com.travel.point.domain.BonusPoint
import com.travel.point.domain.Point
import com.travel.point.domain.Review
import com.travel.point.store.entity.point.PointEntity
import com.travel.point.store.entity.point.PointRepository
import com.travel.point.store.entity.pointhistory.PointHistoryEntity
import com.travel.point.store.entity.pointhistory.PointHistoryRepository
import com.travel.point.store.entity.pointreview.PointReviewEntity
import com.travel.point.store.entity.pointreview.PointReviewRepository
import com.travel.point.type.ActionType
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PointStoreImpl(
    private val pointRepository: PointRepository,
    private val pointHistoryRepository: PointHistoryRepository,
    private val pointReviewRepository: PointReviewRepository,
) : PointStore {

    @Transactional
    override fun addPoint(point: Point, review: Review) {

        val pointEntity = pointRepository.findByUserId(point.user.id).orElse(
            PointEntity(user = point.user)
        )

        pointEntity.addPoint(point = point.score)

        pointHistoryRepository.save(
            PointHistoryEntity(
                pointEntity = pointEntity,
                pointReviewEntity = pointReviewRepository.findById(review.id).get(),
                actionType = ActionType.ADD,
                point = point.score,
                comment = "리뷰 이벤트 참여 포인트 추가"
            )
        )
    }

    @Transactional
    override fun getBonusPoint(review: Review): BonusPoint {
        if (pointReviewRepository.findById(review.id).isPresent) {
            IllegalAccessException("already got point")
        }
        val reviewCount = pointReviewRepository.findByPlaceId(review.place.id)
        pointReviewRepository.save(PointReviewEntity(review))
        return if (reviewCount == 0) {
            BonusPoint(ONE)
        } else {
            BonusPoint(ZERO)
        }
    }
}
