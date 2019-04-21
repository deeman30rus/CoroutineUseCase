package com.delizarov.coroutineusecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException
import kotlin.coroutines.CoroutineContext

typealias ResultCallback<T> = ((T) -> Unit)

interface UseCase<TResult> {

    var callback: ResultCallback<TResult>?

    fun action(): TResult

    fun execute(callback: ResultCallback<TResult>) {

        this.callback = callback

        val result = action()

        this.callback?.invoke(result)
    }

    fun cancel() {

        callback = null
    }
}

class CoroutineUseCase<T>(
    usecase: UseCase<T>,
    private val context: CoroutineContext
) : UseCase<T>  by usecase {

    override fun execute(callback: ResultCallback<T>) {

        this.callback = callback

        val result = runBlocking(context) {
            this@CoroutineUseCase.action()
        }

        this.callback?.invoke(result)
    }
}

fun <T> UseCase<T>.executeIoCoroutine(callback: ResultCallback<T>) = CoroutineUseCase(this, Dispatchers.IO).execute(callback)


class GetHoroscopeUseCase(
    private val sunsign: String
) : UseCase<HoroscopeDto> {

    var _callback: ResultCallback<HoroscopeDto>? = null
    override var callback: ResultCallback<HoroscopeDto>?
        get() = _callback
        set(value) {
            _callback = value
        }

    override fun action() = App.INSTANCE.horoscopeApi.getHoroscope(sunsign).execute().let { response ->
        if (response.isSuccessful) response.body()!!
        else throw IllegalArgumentException()
    }
}