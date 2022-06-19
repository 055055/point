package com.travel.point.store.entity

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PointRepository : JpaRepository<PointEntity, Long> {

    fun findByUserId(userId: String): Optional<PointEntity>
}
