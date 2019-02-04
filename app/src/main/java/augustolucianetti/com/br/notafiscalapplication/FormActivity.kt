package augustolucianetti.com.br.notafiscalapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import augustolucianetti.com.br.notafiscalapplication.model.NotaFiscal
import br.com.augustolucianetti.calculaflex.extention.getValue
import com.google.firebase.auth.FirebaseAuth
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
        val key = FirebaseDatabase.getInstance().getReference(getString(R.string.nomeTabelaNotaFiscal)).push().key.toString()
        val nota = NotaFiscal( etNumeroEdit.getValue().toInt(), etProdutoEdit.getValue(), etQuanidadeEdit.getValue().toFloat(),
            etValorEdit.getValue().toFloat(), rbEntradaEdit.isChecked, rbSaidaEdit.isChecked, rbArmazenagem.isChecked, etFornecedorCliente.getValue(), key,
            FirebaseAuth.getInstance().currentUser!!.uid)
        FirebaseDatabase.getInstance()
            .getReference(getString(R.string.nomeTabelaNotaFiscal))
            .child(key)
            .setValue(nota)
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    val builder = AlertDialog.Builder(this@FormActivity)
                    builder.setMessage(getString(R.string.mensagem_nota_fiscal_cadastrada_sucesso))
                    builder.setPositiveButton(getString(R.string.ok)) {dialog, which ->
                        val intent = Intent()
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    val builder = AlertDialog.Builder(this@FormActivity)
                    builder.setMessage(it.exception?.message)
                    builder.setPositiveButton(getString(R.string.ok)) {dialog, which ->

                    }
                    val dialog = builder.create()
                    dialog.show()
                    System.out.println(getString(R.string.erro_firebase) + it.exception)
                }
            }
    }
}
