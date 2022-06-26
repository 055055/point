package com.travel.point.store.entity.pointhistory

import com.travel.point.config.MySQLTestConfig
import com.travel.point.mock.getMockPointHistoryEntity
import com.travel.point.mock.getMockPointHistoryEntityList
import com.travel.point.type.PointType
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Pageable

@Import(MySQLTestConfig::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointHistoryRepositoryTest(
    private val pointHistoryRepository: PointHistoryRepository
) : FunSpec({


    beforeEach {
        pointHistoryRepository.deleteAll()
    }

    test("reviewId로 Review 이벤트로 쌓인 포인트 내역들을 조회할 수 있다.") {
        val mock = getMockPointHistoryEntityList()
        val reviewId = mock.map { it.pointReviewEntity?.id }.first() ?: ""
        val mockByReviewType = mock.filter { it.pointType == PointType.REVIEW }

        pointHistoryRepository.saveAll(mock)

        //when
        val pointHistoryEntityList = pointHistoryRepository.findByReviewId(reviewId = reviewId, pointType = PointType.REVIEW)

        //then
        pointHistoryEntityList.size shouldBe 1
        pointHistoryEntityList[0].user.id shouldBe mockByReviewType[0].user.id
        pointHistoryEntityList[0].comment shouldBe mockByReviewType[0].comment
        pointHistoryEntityList[0].totalPoint shouldBe mockByReviewType[0].totalPoint
        pointHistoryEntityList[0].point shouldBe mockByReviewType[0].point
        pointHistoryEntityList[0].pointType shouldBe mockByReviewType[0].pointType
    }

    test("reviewId로 마지막에 추가된 보너스 포인트 내역을 조회할 수 있다.") {
        val mock = getMockPointHistoryEntityList()
        val testReviewHistory = mock.last { it.pointReviewEntity?.id == "testReviewId" }
        val testReviewId = testReviewHistory.pointReviewEntity?.id ?: ""
        pointHistoryRepository.saveAll(mock)

        //when
        val pointHistoryEntityList = pointHistoryRepository.findLastBonusPointByReviewId(
            reviewId = testReviewId,
            pointType = PointType.BONUS,
            pageable = Pageable.ofSize(1)
        )

        //then
        pointHistoryEntityList.size shouldBe 1
        pointHistoryEntityList[0].user.id shouldBe testReviewHistory.user.id
        pointHistoryEntityList[0].comment shouldBe testReviewHistory.comment
        pointHistoryEntityList[0].totalPoint shouldBe testReviewHistory.totalPoint
        pointHistoryEntityList[0].point shouldBe testReviewHistory.point
        pointHistoryEntityList[0].pointType shouldBe testReviewHistory.pointType
    }

    test("reviewId로 마지막에 저장된 포인트 내역을 조회할 수 있다.") {
        //given
        val testReviewId = "testReviewId"

        val mock = getMockPointHistoryEntity(reviewId = testReviewId, pointType = PointType.REVIEW)

        pointHistoryRepository.save(mock)

        //when
        val pointHistoryEntity = pointHistoryRepository.findLastOneByReviewId(
            reviewId = testReviewId,
            pointType = PointType.REVIEW,
            pageable = Pageable.ofSize(1)
        )

        //then
        pointHistoryEntity.size shouldBe 1
        pointHistoryEntity[0].user.id shouldBe mock.user.id
        pointHistoryEntity[0].comment shouldBe mock.comment
        pointHistoryEntity[0].totalPoint shouldBe mock.totalPoint
        pointHistoryEntity[0].point shouldBe mock.point
        pointHistoryEntity[0].pointType shouldBe mock.pointType
    }
})
