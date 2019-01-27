package augustolucianetti.com.br.notafiscalapplication

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import augustolucianetti.com.br.notafiscalapplication.model.NotaFiscal
import br.com.augustolucianetti.calculaflex.extention.getValue
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        btnCadastrarNota.setOnClickListener {
                saveInDatabase()
        }
    }

    private fun saveInDatabase() {
        val key = FirebaseDatabase.getInstance().getReference("notaFiscal").push().key.toString()
        val nota = NotaFiscal( etNumeroEdit.getValue().toInt(), etProdutoEdit.getValue(), etQuanidadeEdit.getValue().toFloat(),
            etValorEdit.getValue().toFloat(), rbEntradaEdit.isChecked, rbSaidaEdit.isChecked, key)
        FirebaseDatabase.getInstance()
            .getReference("notaFiscal")
            .child(key)
            .setValue(nota)
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(this@FormActivity,
                        "Nota Fiscal cadastrada com sucesso!", Toast.LENGTH_SHORT).show()

                    val intent = Intent()
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else {
                    System.out.println("erro no firebase: " + it.exception)
                    Toast.makeText(this@FormActivity,
                        it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}
