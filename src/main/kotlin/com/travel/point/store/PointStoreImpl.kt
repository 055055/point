package com.travel.point.store

import com.travel.point.constants.ONE
import com.travel.point.domain.Point
import com.travel.point.domain.Review
import com.travel.point.store.entity.point.PointEntity
import com.travel.point.store.entity.point.PointRepository
import com.travel.point.store.entity.pointhistory.PointHistoryEntity
import com.travel.point.store.entity.pointhistory.PointHistoryRepository
import com.travel.point.store.entity.pointreview.PointReviewEntity
import com.travel.point.store.entity.pointreview.PointReviewRepository
import com.travel.point.type.PointType
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

        val pointReviewEntity = saveReview(review)

        val pointEntity = pointRepository.findByUserId(point.user.id).orElse(
            PointEntity(user = point.user)
        )
        pointEntity.addPoint(point.score)

        pointHistoryRepository.save(
            PointHistoryEntity(
                pointEntity = pointEntity,
                pointReviewEntity = pointReviewEntity,
                pointType = PointType.REVIEW,
                point = point.score,
                comment = "리뷰 이벤트 참여 포인트 추가"
            )
        )
        addBonusPoint(review, pointEntity, pointReviewEntity)
    }

    private fun saveReview(review: Review): PointReviewEntity {
        if (pointReviewRepository.findById(review.id).isPresent) {
            IllegalAccessException("already got point")
        }
        return pointReviewRepository.save(PointReviewEntity(review))
    }

    private fun addBonusPoint(review: Review, pointEntity: PointEntity, pointReviewEntity: PointReviewEntity) {

        val reviewCount = pointReviewRepository.countNotDeletedPlaceId(review.place.id)
        if (reviewCount == 0) {
            pointEntity.addPoint(ONE)
            pointHistoryRepository.save(
                PointHistoryEntity(
                    pointEntity = pointEntity,
                    pointReviewEntity = pointReviewEntity,
                    pointType = PointType.BONUS,
                    point = ONE,
                    comment = "특정 장소 첫 리뷰 보너스 포인트 추가"
                )
            )
        }
    }

    @Transactional
    override fun deletePoint(point: Point, review: Review) {
        val pointReviewEntity = deleteReview(review)

        val deletePoint = pointHistoryRepository.findByReviewId(review.id)
            .stream().mapToInt { it.point }.sum()

        if (point.score != deletePoint) {
            java.lang.IllegalStateException(" not equals point!")
        }

        val pointEntity = pointRepository.findByUserId(point.user.id)
            .orElseThrow { IllegalAccessException("유저 아이디를 다시 확인해주세요.") }

        pointEntity.subtractPoint(point.score)

        pointHistoryRepository.save(
            PointHistoryEntity(
                pointEntity = pointEntity,
                pointReviewEntity = pointReviewEntity,
                pointType = PointType.REVIEW,
                point = point.score,
                comment = "리뷰 이벤트 포인트 삭제"
            )
        )

        deleteBonusPoint(review, pointEntity, pointReviewEntity)
    }

    private fun deleteReview(review: Review): PointReviewEntity {
        val pointReview = pointReviewRepository.findById(review.id)
            .orElseThrow { IllegalAccessException("리뷰 아이디를 다시 확인해주세요.") }

        if (pointReview.actionType == ReviewActionType.DELETE) {
            IllegalStateException("이미 지원진 리뷰 입니다. ")
        }
        pointReview.updatePointReview(review)
        return pointReview
    }

    private fun deleteBonusPoint(review: Review, pointEntity: PointEntity, pointReviewEntity: PointReviewEntity) {
        pointHistoryRepository.findLastBonusPointByReviewId(review.id, PointType.BONUS)
            .ifPresent {
                val bonusPoint = it.toInt()
                pointEntity.subtractPoint(bonusPoint)
                pointHistoryRepository.save(
                    PointHistoryEntity(
                        pointEntity = pointEntity,
                        pointReviewEntity = pointReviewEntity,
                        pointType = PointType.BONUS,
                        point = bonusPoint,
                        comment = "특정 장소 첫 리뷰 보너스 포인트 삭제"
                    )
                )
            }
    }

    @Transactional
    override fun modifyPoint(point: Point, review: Review) {

    }
}
