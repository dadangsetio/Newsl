package com.repairzone.newsl.data.network.base

class UnAuthenticationException(message: String): BaseException(message)
class ServerErrorException(message: String): BaseException(message)
class BadGatewayException(message: String): BaseException(message)
class PaymentRequiredException(message: String): BaseException(message)
class UnknownException(message: String): BaseException(message = message)
class NotFoundException(message: String): BaseException(message)
class BadRequestException(override val message: String?) : Exception(message)
class NullBodyException(message: String): BaseException(message)