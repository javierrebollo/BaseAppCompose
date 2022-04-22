package com.javierrebollo.myapplication.data.network.exception

class NetworkException(val errorCode: Int) : Exception("Error code: $errorCode")
