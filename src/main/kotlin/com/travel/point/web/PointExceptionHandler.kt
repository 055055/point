package com.travel.point.web

import com.travel.point.config.logger
import com.travel.point.error.common.CommonError
import com.travel.point.error.common.CommonException
import com.travel.point.error.pointreview.PointReviewException
import com.travel.point.error.PointErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PointExceptionHandler {

    @ExceptionHandler(CommonException::class)
    fun responseCommonException(e: CommonException): ResponseEntity<PointErrorResponse> {
        logger.error("CommonException ", e)
        return ResponseEntity.status(e.commonError.httpStatus)
            .body(PointErrorResponse(e.commonError.description))
    }

    @ExceptionHandler(PointReviewException::class)
    fun responsePointReviewException(e: PointReviewException): ResponseEntity<PointErrorResponse> {
        logger.error("PointReviewException ", e)
        return ResponseEntity.status(e.pointReviewError.httpStatus)
            .body(PointErrorResponse(e.pointReviewError.description))
    }

    @ExceptionHandler(Exception::class)
    fun responseGlobalException(e: Exception): ResponseEntity<PointErrorResponse> {
        logger.error("Exception ", e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(PointErrorResponse(CommonError.SERVICE_ERROR.description))
    }
}
