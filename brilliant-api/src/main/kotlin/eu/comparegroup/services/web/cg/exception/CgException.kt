package eu.comparegroup.services.web.cg.exception

import java.lang.Exception

class cgException : Exception {
	constructor() : super()
	constructor(message: String?) : super(message)
	constructor(message: String?, throwable: Throwable?) : super(message, throwable)
}