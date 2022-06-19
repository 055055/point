package com.travel.point.store.entity.pointhistory

import org.springframework.data.jpa.repository.JpaRepository

interface PointHistoryRepository:JpaRepository<PointHistoryEntity, Long> {
}
