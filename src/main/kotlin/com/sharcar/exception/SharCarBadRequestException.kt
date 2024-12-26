package com.sharcar.exception

class SharCarBadRequestException(message: String) : Exception(message) {
    companion object {
        fun create(code: Int): SharCarBadRequestException {
            val message = mapForBadRequests[code] ?: "Bad Request"

            return SharCarBadRequestException(message)
        }
    }
}
