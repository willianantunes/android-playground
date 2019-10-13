package br.com.willianantunes.androidplayground.helpers

fun String.isValidUUID(): Boolean {
    val UUIDRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\$"
    return UUIDRegex.toRegex().matches(this)
}
