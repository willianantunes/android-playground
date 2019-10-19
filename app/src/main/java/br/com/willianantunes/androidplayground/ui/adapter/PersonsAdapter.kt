package br.com.willianantunes.androidplayground.ui.adapter

import android.content.Context
import android.widget.ArrayAdapter
import br.com.willianantunes.androidplayground.services.persistence.Person

class PersonsAdapter(context: Context, resource: Int) : ArrayAdapter<Person>(context, resource)
