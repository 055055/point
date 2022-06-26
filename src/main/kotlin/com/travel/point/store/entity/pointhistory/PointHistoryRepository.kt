package com.travel.point.store.entity.pointhistory

import com.travel.point.type.PointType
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PointHistoryRepository : JpaRepository<PointHistoryEntity, Long> {
    @Query(
        """
         SELECT ph FROM PointHistoryEntity ph 
            WHERE ph.pointReviewEntity.id = :reviewId
            AND ph.pointType = :pointType
        """
    )
    fun findByReviewId(
        @Param("reviewId") reviewId: String,
        @Param("pointType") pointType: PointType = PointType.REVIEW
    ): List<PointHistoryEntity>

    @Query(
        """
         SELECT ph FROM PointHistoryEntity ph 
            WHERE ph.pointReviewEntity.id = :reviewId
            AND ph.pointType = :pointType
            AND ph.point = 1
            ORDER BY ph.seq DESC
        """
    )
    fun findLastBonusPointByReviewId(
        @Param("reviewId") reviewId: String,
        @Param("pointType") pointType: PointType = PointType.BONUS,
        pageable: Pageable
    ): List<PointHistoryEntity>

    @Query(
        """
         SELECT ph FROM PointHistoryEntity ph 
            WHERE ph.pointReviewEntity.id = :reviewId
            AND ph.pointType = :pointType
            ORDER BY ph.seq DESC
        """
    )
    fun findLastOneByReviewId(
        @Param("reviewId") reviewId: String,
        @Param("pointType") pointType: PointType = PointType.REVIEW,
        pageable: Pageable
    ): List<PointHistoryEntity>
}
