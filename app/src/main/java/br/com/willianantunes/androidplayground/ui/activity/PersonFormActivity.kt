package br.com.willianantunes.androidplayground.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.willianantunes.androidplayground.R
import br.com.willianantunes.androidplayground.services.persistence.Person
import br.com.willianantunes.androidplayground.setup.personService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PersonFormActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var birthdayEditText: EditText
    private var person: Person? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_person)
        initializeComponents()
        initializeForm()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_form_person_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId.let {
            when (it) {
                R.id.activity_form_person__menu_save -> {
                    val name = nameEditText.text.toString()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val birthday = LocalDate.parse(birthdayEditText.text.toString(), formatter)
                    person = person?.copy(name = name, birthday = birthday) ?: Person(null, name, birthday)

                    when (person?.id) {
                        null -> person?.let { personService.createPerson(it) }
                        else -> person?.let { personService.updatePerson(it) }
                    }

                    finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initializeComponents() {
        nameEditText = findViewById(R.id.activity_form_person__et__name)
        birthdayEditText = findViewById(R.id.activity_form_person__et__birthday)
    }

    private fun initializeForm() {
        if (intent.hasExtra(KEY_PERSON)) {
            setTitle(R.string.title_person_form_edit)
            person = intent.getSerializableExtra(KEY_PERSON) as Person
            fillFields()
        } else {
            setTitle(R.string.title_person_form_new)
        }
    }

    private fun fillFields() {
        person?.name.let { nameEditText.setText(it) }
        person?.birthday.let { birthdayEditText.setText(it.toString()) }
    }
}
