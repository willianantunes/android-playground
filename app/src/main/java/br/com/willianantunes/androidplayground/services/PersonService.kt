package br.com.willianantunes.androidplayground.services

import br.com.willianantunes.androidplayground.services.persistence.Person
import br.com.willianantunes.androidplayground.services.persistence.PersonRepository

class PersonService(val personRepository: PersonRepository) {
    fun createPerson(person: Person): Person {

        return personRepository.save(person)
    }

    fun allPersons(): List<Person> {
        return personRepository.findAll()
    }

    fun updatePerson(person: Person): Person {
        return personRepository.update(person)
    }

    fun deletePerson(id: String) {
        personRepository.delete(id)
    }
}
