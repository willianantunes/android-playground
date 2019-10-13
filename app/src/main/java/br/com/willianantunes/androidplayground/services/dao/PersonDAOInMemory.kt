package br.com.willianantunes.androidplayground.services.dao

import br.com.willianantunes.androidplayground.services.persistence.Person
import br.com.willianantunes.androidplayground.services.persistence.PersonRepository
import java.util.*

class PersonDAOInMemory : PersonRepository {
    val PERSON_DATABASE = mutableListOf<Person>()

    override fun findAll(): List<Person> {
        return PERSON_DATABASE.toList()
    }

    override fun findById(id: String): Person? {
        return PERSON_DATABASE.filter { it.id == id }.single()
    }

    override fun save(person: Person): Person {
        when (person.id) {
            null -> {
                val personToBeSaved = person.copy(id = UUID.randomUUID().toString())
                PERSON_DATABASE.add(personToBeSaved)
                return personToBeSaved
            }
            else -> {
                throw IdMustNotBeSetException()
            }
        }
    }

    override fun update(person: Person): Person {
        when (person.id) {
            null -> {
                throw IdIsNotSetException()
            }
            else -> {
                val personFound = findById(person.id)
                val whereThePersonIs = PERSON_DATABASE.indexOf(personFound)
                val (_, name, birthday) = person
                val personToBeUpdated = Person(person.id, name, birthday)
                PERSON_DATABASE.set(whereThePersonIs, personToBeUpdated)
                return personToBeUpdated
            }
        }
    }

    override fun delete(id: String) {
        findById(id)?.let { PERSON_DATABASE.remove(it) }
    }
}
