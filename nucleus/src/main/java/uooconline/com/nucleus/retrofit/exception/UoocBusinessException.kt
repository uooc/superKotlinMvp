package uooconline.com.nucleus.retrofit.exception

class UoocBusinessException(message: String, val code: Int = -1) : Throwable(message)
