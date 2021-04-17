package document

class BadRequestException(
    override val message: String?
): Exception() {
}