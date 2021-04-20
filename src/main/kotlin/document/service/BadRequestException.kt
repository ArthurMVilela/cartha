package document.service

class BadRequestException(
    override val message: String?
): Exception() {
}