package br.com.willianantunes.androidplayground.ui

import android.app.AlertDialog
import android.content.Context
import android.view.MenuItem
import android.widget.AdapterView
import br.com.willianantunes.androidplayground.R
import br.com.willianantunes.androidplayground.services.persistence.Person
import br.com.willianantunes.androidplayground.setup.personService
import br.com.willianantunes.androidplayground.ui.adapter.PersonsAdapter

class PersonsView {
    val context: Context
    val adapter: PersonsAdapter

    constructor(context: Context) {
        this.context = context
        adapter = PersonsAdapter(context, android.R.layout.simple_list_item_1)
    }

    fun confirmDeletion(menuItem: MenuItem) {
        with(AlertDialog.Builder(context)) {
            setTitle(R.string.title_person_alert_deletion)
            setMessage(R.string.message_person_alert_deletion)
            setPositiveButton(R.string.yes) { dialog, which ->
                with(menuItem.menuInfo as AdapterView.AdapterContextMenuInfo) {
                    val chosenPerson = adapter.getItem(this.position)
                    chosenPerson?.let { remove(chosenPerson) }
                }
            }
            setNegativeButton(R.string.no, null)
            show()
        }
    }

    fun refresh() {
        with(adapter) {
            clear()
            addAll(personService.allPersons())
        }
    }

    private fun remove(person: Person) {
        person.id?.let { personService.deletePerson(it) }.also { adapter.remove(person) }
    }
}
