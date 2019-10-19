package br.com.willianantunes.androidplayground.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import br.com.willianantunes.androidplayground.R

class MainActivity : AppCompatActivity() {
    lateinit var cardView: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeFields()
        initializeActions()
    }

    private fun initializeActions() {
        cardView.setOnClickListener { startActivity(Intent(this, PersonsActivity::class.java)) }
    }

    private fun initializeFields() {
        cardView = findViewById(R.id.activity_main__cardview_person)
    }
}
