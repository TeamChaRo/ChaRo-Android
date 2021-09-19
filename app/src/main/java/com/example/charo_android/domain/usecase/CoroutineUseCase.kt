package com.example.charo_android.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<P, R>() {
    abstract suspend fun execute(parameter: P) : R

    suspend operator fun invoke(parameter: P): Result<R>{
        return try{
            withContext(Dispatchers.IO){
                val result = execute(parameter)
                Result.Success(result)
            }
        } catch (e: Exception){
            Result.Error(e)
        }
    }
}