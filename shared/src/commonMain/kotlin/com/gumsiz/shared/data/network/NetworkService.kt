package com.gumsiz.shared.data.network

import com.gumsiz.shared.data.model.WordNetworkModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface NetworkService {
    suspend fun getData():List<WordNetworkModel>
}

class NetworkServiceImp(private val client: HttpClient): NetworkService{
    override suspend fun getData() = client.get("http://gumsiz.hol.es/connect.php").body<List<WordNetworkModel>>()
}