package com.travel.point.store.entity.point

import com.travel.point.config.MySQLTestConfig
import com.travel.point.mock.getMockPoint
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@Import(MySQLTestConfig::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointRepositoryTest(
    private val pointRepository: PointRepository
) : FunSpec({

    beforeEach {
        pointRepository.deleteAll()
    }

    test("userId를 조회할 수 있다.") {
        //given
        val userId = "test1"
        val mockPointEntity = getMockPoint(userId = userId, point = 1)
        pointRepository.save(mockPointEntity)

        //when
        val pointEntity = pointRepository.findByUserId(userId)

        //then
        pointEntity.get().user.id shouldBe mockPointEntity.user.id
        pointEntity.get().point shouldBe mockPointEntity.point
    }
})
