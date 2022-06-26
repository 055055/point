package com.travel.point.store.entity.pointreview

import com.travel.point.config.MySQLTestConfig
import com.travel.point.mock.getMockPointReviewEntity
import com.travel.point.mock.getMockPointReviewEntityForCountNotDeletedPlaceId
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@Import(MySQLTestConfig::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointReviewRepositoryTest(
    private val pointReviewRepository: PointReviewRepository
) : FunSpec({

    beforeEach {
        pointReviewRepository.deleteAll()
    }

    test("reviewId를 조회할 수 있다.") {
        //given
        val mock = getMockPointReviewEntity()
        pointReviewRepository.save(mock)

        //when
        val pointReviewEntity = pointReviewRepository.findById(mock.id)

        //then
        pointReviewEntity.get().id shouldBe mock.id
        pointReviewEntity.get().content shouldBe mock.content
        pointReviewEntity.get().place.id shouldBe mock.place.id
        pointReviewEntity.get().actionType shouldBe mock.actionType
        pointReviewEntity.get().user.id shouldBe mock.user.id
        pointReviewEntity.get().photo shouldContainAll mock.photo
    }

    test("삭제되지 않은 리뷰들의 특정 장소 ID를 카운트 할 수 있다.") {
        //given
        val placeId = "testPlaceId1"
        val userId = "testUserId"
        val mock = getMockPointReviewEntityForCountNotDeletedPlaceId()
        pointReviewRepository.saveAll(mock)
        val count = mock.filter { it.place.id == placeId }
            .count { it.user.id != userId }

        //when
        val pointReviewEntity = pointReviewRepository.countNotDeletedPlaceId(
            placeId = placeId,
            userId = userId
        )

        //then
        pointReviewEntity shouldBe count
    }
})
