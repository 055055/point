package com.travel.point.store.entity.pointhistory

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PointHistoryRepository : JpaRepository<PointHistoryEntity, Long> {
    @Query(
        """
         SELECT ph FROM PointHistoryEntity ph 
            WHERE ph.pointReviewEntity.id = :reviewId
        """
    )
    fun findByReviewId(@Param("reviewId") reviewId: String): List<PointHistoryEntity>
}
