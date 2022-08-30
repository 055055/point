package com.travel.point.store

import com.travel.point.constants.ONE
import com.travel.point.constants.ZERO
import com.travel.point.domain.Point
import com.travel.point.domain.Review
import com.travel.point.domain.User
import com.travel.point.error.common.CommonError
import com.travel.point.error.common.CommonException
import com.travel.point.error.pointreview.PointReviewError
import com.travel.point.error.pointreview.PointReviewException
import com.travel.point.store.entity.point.PointEntity
import com.travel.point.store.entity.point.PointRepository
import com.travel.point.store.entity.pointhistory.PointHistoryEntity
import com.travel.point.store.entity.pointhistory.PointHistoryRepository
import com.travel.point.store.entity.pointreview.PointReviewEntity
import com.travel.point.store.entity.pointreview.PointReviewRepository
import com.travel.point.type.EventActionType
import com.travel.point.type.PointType
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PointStoreImpl(
    private val pointRepository: PointRepository,
    private val pointHistoryRepository: PointHistoryRepository,
    private val pointReviewRepository: PointReviewRepository,
) : PointStore {

    @Transactional
    override suspend fun saveReview(review: Review): Review {
        if (pointReviewRepository.findById(review.id).isPresent) {
            throw PointReviewException(PointReviewError.ALREADY_SAVED_REVIEW)
        }
        val pointReviewEntity = pointReviewRepository.save(PointReviewEntity(review))
        return pointReviewEntity.convertToReview()
    }

    @Transactional
    override suspend fun addPoint(point: Point, review: Review, comment: String) {
        val pointEntity = pointRepository.findByUserId(point.user.id).orElse(
            PointEntity(point.user)
        )
        pointEntity.addPoint(point.score)

        pointHistoryRepository.save(
            PointHistoryEntity(
                pointEntity = pointEntity,
                pointReviewEntity = pointReviewRepository.findById(review.id).orElse(null),
                pointType = point.type ?: PointType.REVIEW,
                point = point.score,
                comment = comment
            )
        )
    }

    @Transactional(readOnly = true)
    override suspend fun getReviewBonusPoint(review: Review): Point {
        val reviewCount = pointReviewRepository.countNotDeletedPlaceId(review.place.id, review.user.id)
        return if (reviewCount == 0) {
            Point(
                user = review.user,
                score = ONE,
            ).apply {
                this.type = PointType.BONUS
            }
        } else {
            Point(
                user = review.user,
                score = ZERO,
            ).apply {
                this.type = PointType.BONUS
            }
        }
    }

    override suspend fun getRollbackReviewBonusPoint(review: Review): Point {
        val bonusPointHistory = pointHistoryRepository.findLastBonusPointByReviewId(
            review.id,
            PointType.BONUS,
            Pageable.ofSize(1)
        )

        return if (bonusPointHistory.isNotEmpty()) {
            Point(
                user = review.user,
                score = ONE,
            ).apply {
                this.type = PointType.BONUS
            }
        } else {
            Point(
                user = review.user,
                score = ZERO,
            ).apply {
                this.type = PointType.BONUS
            }
        }
    }

    @Transactional
    override suspend fun deletePoint(point: Point, review: Review, comment: String) {

        val deletePoint = pointHistoryRepository.findByReviewId(review.id, point.type ?: PointType.REVIEW)
            .stream().mapToInt { it.point }.sum()

        if (point.score != deletePoint) {
            throw PointReviewException(PointReviewError.MISMATCH_DELETE_POINT)
        }

        val pointEntity = pointRepository.findByUserId(point.user.id)
            .orElseThrow { CommonException(CommonError.CHECK_USER_ID) }

        pointEntity.subtractPoint(point.score)

        pointHistoryRepository.save(
            PointHistoryEntity(
                pointEntity = pointEntity,
                pointReviewEntity = pointReviewRepository.findById(review.id).orElse(null),
                pointType = point.type ?: PointType.REVIEW,
                point = (point.score * -1),
                comment = comment
            )
        )
    }

    @Transactional
    override suspend fun deleteReview(review: Review): Review {
        val pointReview = pointReviewRepository.findById(review.id)
            .orElseThrow { PointReviewException(PointReviewError.CHECK_REVIEW_ID) }

        if (pointReview.actionType == EventActionType.DELETE) {
            throw PointReviewException(PointReviewError.ALREADY_DELETED_REVIEW)
        }
        pointReview.updatePointReview(review)
        return pointReview.convertToReview()
    }

    @Transactional
    override suspend fun getLastReviewPointHistory(review: Review): Point {
        val pointHistoryEntity = pointHistoryRepository.findLastOneByReviewId(
            reviewId = review.id,
            pageable = Pageable.ofSize(1)
        )

        if (pointHistoryEntity.isEmpty()) {
            throw PointReviewException(PointReviewError.EMPTY_POINT_HISTORY)
        }
        return Point(
            user = pointHistoryEntity.first().user,
            score = pointHistoryEntity.first().point
        ).apply {
            this.type = pointHistoryEntity.first().pointType
        }
    }

    @Transactional(readOnly = true)
    override suspend fun getPoint(user: User): Point {
        val pointEntity = pointRepository.findByUserId(user.id)
            .orElseThrow { CommonException(CommonError.CHECK_USER_ID) }

        return Point(
            user = pointEntity.user,
            score = pointEntity.point
        )
    }

    @Transactional
    override suspend fun modifyReview(review: Review) {
        val pointReview = pointReviewRepository.findById(review.id)
            .orElseThrow { PointReviewException(PointReviewError.CHECK_REVIEW_ID) }

        if (pointReview.actionType == EventActionType.DELETE) {
            throw PointReviewException(PointReviewError.ALREADY_DELETED_REVIEW)
        }
        pointReview.updatePointReview(review)
    }
}
