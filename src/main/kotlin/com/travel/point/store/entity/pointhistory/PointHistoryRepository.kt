package com.travel.point.store.entity.pointhistory

import com.travel.point.type.PointType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface PointHistoryRepository : JpaRepository<PointHistoryEntity, Long> {
    @Query(
        """
         SELECT ph FROM PointHistoryEntity ph 
            WHERE ph.pointReviewEntity.id = :reviewId
        """
    )
    fun findByReviewId(@Param("reviewId") reviewId: String): List<PointHistoryEntity>

    @Query(
        """
         SELECT ph.point FROM PointHistoryEntity ph 
            WHERE ph.pointReviewEntity.id = :reviewId
            AND ph.pointType = :pointType
            AND ph.point = 1
            ORDER BY ph.seq DESC
        """
    )
    fun findLastBonusPointByReviewId(
        @Param("reviewId") reviewId: String,
        @Param("pointType") pointType: PointType
    ): Optional<Integer>

    @Query(
        """
         SELECT ph FROM PointHistoryEntity ph 
            WHERE ph.pointReviewEntity.id = :reviewId
            ORDER BY ph.seq DESC
        """
    )
    fun findLastOneByReviewId(@Param("reviewId") reviewId: String): PointHistoryEntity
}
