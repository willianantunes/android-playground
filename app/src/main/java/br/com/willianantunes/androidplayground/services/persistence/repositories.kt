package br.com.willianantunes.androidplayground.services.persistence

interface PersonRepository {
    fun findAll(): List<Person>
    fun findById(id: String): Person?
    fun save(person: Person): Person
    fun update(person: Person): Person
    fun delete(id: String)
}
