package br.com.willianantunes.androidplayground.setup

import android.app.Application
import br.com.willianantunes.androidplayground.services.PersonService
import br.com.willianantunes.androidplayground.services.dao.PersonDAOInMemory
import br.com.willianantunes.androidplayground.services.persistence.Person
import java.time.LocalDate

val personService = PersonService(PersonDAOInMemory())

class AndroidPlaygroundApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeWithData()
    }

    private fun initializeWithData() {
        personService.createPerson(Person(null, "Iago", LocalDate.now()))
        personService.createPerson(Person(null, "Jasmine", LocalDate.now()))
    }
}
