package com.travel.point.store.entity

import org.springframework.data.jpa.repository.JpaRepository

interface PointHistoryRepository:JpaRepository<PointHistoryEntity, Long> {
}
