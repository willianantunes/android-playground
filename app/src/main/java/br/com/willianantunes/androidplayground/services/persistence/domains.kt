package br.com.willianantunes.androidplayground.services.persistence

import java.io.Serializable
import java.time.LocalDate

data class Person(val id: String?, val name: String, val birthday: LocalDate) : Serializable
