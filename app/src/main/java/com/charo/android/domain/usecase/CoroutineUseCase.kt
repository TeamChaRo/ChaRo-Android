package com.charo.android.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

// 확인요망
abstract class CoroutineUseCase<P, R>(
    private val coroutineDispatcher: CoroutineDispatcher
) {
    protected abstract suspend fun execute(parameter: P) : R

    suspend operator fun invoke(parameter: P): Result<R>{
        return try{
            withContext(coroutineDispatcher){
                val result = execute(parameter)
                Result.Success(result)
            }
        } catch (e: Exception){
            Result.Error(e)
        }
    }
}