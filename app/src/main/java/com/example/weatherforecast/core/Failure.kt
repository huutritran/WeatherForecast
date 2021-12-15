package com.example.weatherforecast.core

sealed class Failure {
    object ServerError : Failure()
    object NetworkConnection : Failure()

    abstract class FeatureFailure : Failure()
}