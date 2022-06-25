package com.travel.point.store

import com.travel.point.constants.ONE
import com.travel.point.constants.ZERO
import com.travel.point.domain.Point
import com.travel.point.domain.Review
import com.travel.point.domain.User
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
    override fun saveReview(review: Review): Review {
        if (pointReviewRepository.findById(review.id).isPresent) {
            throw IllegalAccessException("already got point")
        }
        val pointReviewEntity = pointReviewRepository.save(PointReviewEntity(review))
        return pointReviewEntity.convertToReview()
    }

    @Transactional
    override fun addPoint(point: Point, review: Review, comment: String) {
        val pointEntity = pointRepository.findByUserId(point.user.id).orElse(
            PointEntity(user = point.user)
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
    override fun getReviewBonusPoint(review: Review): Point {
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

    override fun getRollbackReviewBonusPoint(review: Review): Point {
        val bonusPointHistory = pointHistoryRepository.findLastBonusPointByReviewId(
            review.id,
            PointType.BONUS,
            Pageable.ofSize(1)
        )
        if (bonusPointHistory.size > 1) {
            throw IllegalArgumentException(" bonus point exception")
        }

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
    override fun deletePoint(point: Point, review: Review, comment: String) {

        val deletePoint = pointHistoryRepository.findByReviewId(review.id, point.type ?: PointType.REVIEW)
            .stream().mapToInt { it.point }.sum()
        println("review = ${review.id}")
        println("deletePoint = ${deletePoint}")
        println("point = ${point.score}")

        if (point.score != deletePoint) {
            throw IllegalAccessException("point sum error!")
        }

        val pointEntity = pointRepository.findByUserId(point.user.id)
            .orElseThrow { IllegalAccessException("유저 아이디를 다시 확인해주세요.") }

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
    override fun deleteReview(review: Review): Review {
        val pointReview = pointReviewRepository.findById(review.id)
            .orElseThrow { IllegalAccessException("리뷰 아이디를 다시 확인해주세요.") }

        if (pointReview.actionType == EventActionType.DELETE) {
            throw IllegalStateException("이미 삭제된 리뷰 입니다. ")
        }
        pointReview.updatePointReview(review)
        return pointReview.convertToReview()
    }

    @Transactional
    override fun getLastReviewPointHistory(review: Review): Point {
        val pointHistoryEntity = pointHistoryRepository.findLastOneByReviewId(
            reviewId = review.id,
            pageable = Pageable.ofSize(1)
        )

        if (pointHistoryEntity.isEmpty()) {
            throw IllegalAccessException("에러")
        }
        return Point(
            user = pointHistoryEntity.first().user,
            score = pointHistoryEntity.first().point
        ).apply {
            this.type = pointHistoryEntity.first().pointType
        }
    }

    @Transactional(readOnly = true)
    override fun getPoint(user: User): Point {
        val pointEntity = pointRepository.findByUserId(user.id)
            .orElseThrow { IllegalAccessException("회원 아이디를 확인해 주세요.") }

        return Point(
            user = pointEntity.user,
            score = pointEntity.point
        )
    }

    @Transactional
    override fun modifyReview(review: Review) {
        val pointReview = pointReviewRepository.findById(review.id)
            .orElseThrow { IllegalAccessException("리뷰 아이디를 다시 확인해주세요.") }

        if (pointReview.actionType == EventActionType.DELETE) {
            throw IllegalStateException("이미 삭제된 리뷰 입니다. ")
        }
        pointReview.updatePointReview(review)
    }
}
