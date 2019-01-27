package augustolucianetti.com.br.notafiscalapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import augustolucianetti.com.br.notafiscalapplication.model.NotaFiscal
import br.com.augustolucianetti.calculaflex.extention.getValue
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val notaFiscal = intent.getSerializableExtra("notaFiscal") as NotaFiscal
        val tabela = FirebaseDatabase.getInstance().getReference("notaFiscal")

        var chave: String? = null
        if (notaFiscal != null) {
            etNumeroEdit.setText(notaFiscal.numero.toString())
            etProdutoEdit.setText(notaFiscal.nomeProduto)
            etQuanidadeEdit.setText(notaFiscal.quantidade.toString())
            etValorEdit.setText(notaFiscal.valor.toString())
            chave = notaFiscal.id
        }

        btnEditar.setOnClickListener {
            updateNotaFiscal(tabela, chave!!)
        }
    }


    private fun updateNotaFiscal(tabela: DatabaseReference, chave: String) {

        var novo = NotaFiscal()
        novo.valor = etValorEdit.getValue().toFloat()
        novo.quantidade = etQuanidadeEdit.getValue().toFloat()
        novo.nomeProduto = etProdutoEdit.getValue()
        novo.numero = etNumeroEdit.getValue().toInt()
        novo.id = chave
        novo.entrada = rbEntradaEdit.isChecked
        novo.saida = rbSaidaEdit.isChecked

        tabela.child(chave).setValue(novo).addOnCompleteListener{
            if (it.isSuccessful) {
                Toast.makeText(this@EditActivity,
                    "Nota Fiscal editada com sucesso!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@EditActivity, ListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                System.out.println("erro no firebase: " + it.exception)
                Toast.makeText(this@EditActivity,
                    it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
