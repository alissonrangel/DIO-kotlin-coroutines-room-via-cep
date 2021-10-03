package one.digitalinnovation.coroutines.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import one.digitalinnovation.coroutines.data.dao.AppDatabase
import one.digitalinnovation.coroutines.data.model.Address2
import one.digitalinnovation.coroutines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindEvents()

    }

    private fun bindEvents() = with(binding) {
        val db = Room.databaseBuilder(
            this@MainActivity,
            AppDatabase::class.java, "via_cep_address"
        )
            .fallbackToDestructiveMigration()
            .build()
        lifecycleScope.launch { // coroutine on Main
            var listaString: String = ""
            for ( item in db.addressDao().getAll().asReversed()){
                listaString += item.toString() +"\n\n"
            }
            tvAddresses.text = "Últimas pesquisas feitas:\n${listaString}"
        }

        btnSearch.setOnClickListener {
            viewModel.findAddress(tilCep.editText?.text?.toString().orEmpty())
        }

        viewModel.state.observe(this@MainActivity){
            tvAddress.text = it

        lifecycleScope.launch { // coroutine on Main
            Log.i("TAGX", viewModel.endereco.toString())
            var end : Address2? = viewModel.endereco

            if (end != null) {
                end.uid = db.addressDao().getAll().last().uid + 1
                tvAddresses.text = end.uid.toString()

                db.addressDao().insertAll(end)
                val enderecos = db.addressDao().getAll()
                if ( enderecos.size > 3) {
                    db.addressDao().delete(enderecos.first())
                }
                var listaString: String = ""
                for ( item in db.addressDao().getAll().asReversed()){
                    listaString += item.toString() + "\n\n"
                }
                tvAddresses.text = "Últimas pesquisas feitas:\n${listaString}"
            }
        }

        }
    }
}