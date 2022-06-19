package com.travel.point.store.entity

import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository : JpaRepository<PointEntity, Long> {
}
