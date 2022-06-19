package com.travel.point.store

import com.travel.point.constants.ONE
import com.travel.point.constants.ZERO
import com.travel.point.domain.BonusPoint
import com.travel.point.domain.Point
import com.travel.point.domain.Review
import com.travel.point.domain.User
import com.travel.point.store.entity.point.PointEntity
import com.travel.point.store.entity.point.PointRepository
import com.travel.point.store.entity.pointhistory.PointHistoryEntity
import com.travel.point.store.entity.pointhistory.PointHistoryRepository
import com.travel.point.store.entity.pointreview.PointReviewEntity
import com.travel.point.store.entity.pointreview.PointReviewRepository
import com.travel.point.type.PointActionType
import com.travel.point.type.ReviewActionType
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

        pointEntity.addPoint(point.score)

        pointHistoryRepository.save(
            PointHistoryEntity(
                pointEntity = pointEntity,
                pointReviewEntity = pointReviewRepository.findById(review.id).get(),
                pointActionType = PointActionType.ADD,
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
        val reviewCount = pointReviewRepository.countNotDeletedPlaceId(review.place.id)
        pointReviewRepository.save(PointReviewEntity(review))
        return if (reviewCount == 0) {
            BonusPoint(ONE)
        } else {
            BonusPoint(ZERO)
        }
    }

    @Transactional
    override fun deletePointReview(review: Review):Point {
        val pointReview = pointReviewRepository.findById(review.id)
            .orElseThrow { IllegalAccessException("리뷰 아이디를 다시 확인해주세요.") }

        if (pointReview.actionType == ReviewActionType.DELETE) {
            IllegalStateException("이미 지원진 리뷰 입니다. ")
        }
        pointReviewRepository.save(PointReviewEntity(review))
        val deletePoint = pointHistoryRepository.findByReviewId(review.id)
            .stream().mapToInt { it.point }.sum()
        return Point(User(review.user.id), deletePoint)
    }

    @Transactional
    override fun subtractPoint(point: Point, review: Review) {

        val pointEntity = pointRepository.findByUserId(point.user.id)
            .orElseThrow { IllegalAccessException("유저 아이디를 다시 확인해주세요.") }


        pointEntity.subtractPoint(point.score)

        pointHistoryRepository.save(
            PointHistoryEntity(
                pointEntity = pointEntity,
                pointReviewEntity = pointReviewRepository.findById(review.id).get(),
                pointActionType = PointActionType.SUBTRACT,
                point = point.score,
                comment = "리뷰 이벤트 포인트 삭제"
            )
        )
    }
}
