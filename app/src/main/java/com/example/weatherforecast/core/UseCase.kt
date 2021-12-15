package com.example.weatherforecast.core

import arrow.core.Either

abstract class UseCase<out Type, in Params> {
    abstract suspend operator fun invoke(params: Params): Either<Failure, Type>
}

object NoParams