package fr.utils

/**
 * Interface that provide a single method to execute
 */
fun interface UseCase<out T> {
    /**
     * Launch main code of a controller
     *
     * @return Result containing the data or the exception
     */
    suspend fun execute(): Result<T>
}