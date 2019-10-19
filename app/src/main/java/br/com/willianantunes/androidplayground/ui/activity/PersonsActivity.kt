package br.com.willianantunes.androidplayground.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import br.com.willianantunes.androidplayground.R
import br.com.willianantunes.androidplayground.services.persistence.Person
import br.com.willianantunes.androidplayground.setup.personService
import br.com.willianantunes.androidplayground.ui.adapter.PersonsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

val KEY_PERSON = "KEY_PERSON"

class PersonsActivity : AppCompatActivity() {
    private lateinit var fabNewPerson: FloatingActionButton
    private lateinit var listView: ListView
    private lateinit var personsAdapter: PersonsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persons)
        setTitle(R.string.persons)
        initializeComponents()
        configureActions()
    }

    override fun onPostResume() {
        super.onPostResume()
        personsAdapter.clear()
        personsAdapter.addAll(personService.allPersons())
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.activity_persons_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.activity_persons__menu_remove -> {
                with(item.menuInfo as AdapterView.AdapterContextMenuInfo) {
                    val chosenPerson = this@PersonsActivity.personsAdapter.getItem(this.position) as Person
                    this@PersonsActivity.remove(chosenPerson)
                }
            }
        }
        return super.onContextItemSelected(item);
    }

    private fun initializeComponents() {
        personsAdapter = PersonsAdapter(this, android.R.layout.simple_list_item_1)
        fabNewPerson = findViewById<FloatingActionButton>(R.id.activity_persons_fab_new_person)
        listView = findViewById<ListView>(R.id.activity_persons_lv)
        with(listView) {
            this.adapter = personsAdapter
            this@PersonsActivity.registerForContextMenu(this)
        }
    }

    private fun configureActions() {
        listView.setOnItemClickListener { parent, view, position, id -> opensFormInEditMode(parent, position) }
        fabNewPerson.setOnClickListener { opensFormInNewMode() }
    }

    private fun opensFormInNewMode() {
        startActivity(Intent(this, PersonFormActivity::class.java))
    }

    private fun opensFormInEditMode(parent: AdapterView<*>, position: Int) {
        val person = parent.getItemAtPosition(position) as Person
        Intent(this@PersonsActivity, PersonFormActivity::class.java)
            .also { it.putExtra(KEY_PERSON, person) }
            .apply { startActivity(this) }
    }

    private fun remove(person: Person) {
        person.id?.let { personService.deletePerson(it) }.also { personsAdapter.remove(person) }
    }
}
