package serviceExceptions

class BadRequestException(
    override val message: String?
): Exception() {
}