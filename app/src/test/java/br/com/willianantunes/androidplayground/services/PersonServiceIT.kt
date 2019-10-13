package br.com.willianantunes.androidplayground.services

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import assertk.assertions.isZero
import br.com.willianantunes.androidplayground.helpers.isValidUUID
import br.com.willianantunes.androidplayground.services.dao.PersonDAOInMemory
import br.com.willianantunes.androidplayground.services.persistence.Person
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.Month


class PersonServiceIT {
    lateinit var personService: PersonService

    @Before
    fun beforeEachTest() {
        personService = PersonService(PersonDAOInMemory())
    }

    @Test
    fun `Should create person given valid input data`() {
        val personName = "Jafar"
        val birthday = LocalDate.now()

        val personToBeSaved = Person(null, personName, birthday)
        val personSaved = personService.createPerson(personToBeSaved)

        assertThat(personSaved.id!!.isValidUUID()).isTrue()
        assertThat(personSaved.name).isEqualTo(personName)
        assertThat(personSaved.birthday).isEqualTo(birthday)
    }

    @Test
    fun `Should return empty list given none have been saved`() {
        val persons = personService.allPersons()

        assertThat(persons.size).isZero()
    }

    @Test
    fun `Should return list with two entries given two persons have been saved`() {
        val personSavedIago = personService.createPerson(Person(null, "Iago", LocalDate.now()))
        val personSavedJasmine = personService.createPerson(Person(null, "Jasmine", LocalDate.now()))

        val persons = personService.allPersons()
        assertThat(persons.size).isEqualTo(2)
        assertThat(persons.contains(personSavedIago)).isTrue()
        assertThat(persons.contains(personSavedJasmine)).isTrue()
    }

    @Test
    fun `Should update person given valid input`() {
        val personSavedIago = personService.createPerson(Person(null, "Aladdin", LocalDate.now()))
        val updatedName = "Robin Williams"
        val updatedBirthday = LocalDate.of(1951, Month.JULY, 21)

        personService.updatePerson(personSavedIago.copy(name = updatedName, birthday = updatedBirthday)).let {
            assertThat(it.name).isEqualTo(updatedName)
            assertThat(it.birthday).isEqualTo(updatedBirthday)
            assertThat(it.id).isNotNull()
        }
    }

    @Test
    fun `Should delete person`() {
        val personSavedIago = personService.createPerson(Person(null, "Aladdin", LocalDate.now()))
        assertThat(personService.allPersons().size).isEqualTo(1)

        personSavedIago.id?.let { personService.deletePerson(it) }

        assertThat(personService.allPersons().size).isZero()
    }
}

