package domain.commons

sealed interface Error {

    data object Other : Error

    data object Network : Error

    data object NoError : Error
}
