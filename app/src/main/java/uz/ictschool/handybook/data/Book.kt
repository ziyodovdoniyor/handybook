package uz.ictschool.handybook.data

import java.io.Serializable

data class Book(
    val audio: Any,
    val author: String,
    val count_page: Int,
    val description: String,
    val `file`: String,
    val id: Int,
    val image: String,
    val lang: String,
    val name: String,
    val publisher: String,
    val reyting: Int,
    val status: Int,
    val type_id: Int,
    val year: String
):Serializable