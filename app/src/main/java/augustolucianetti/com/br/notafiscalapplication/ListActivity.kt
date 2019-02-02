package augustolucianetti.com.br.notafiscalapplication

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import augustolucianetti.com.br.notafiscalapplication.model.NotaFiscal
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.reciclerview_item_row.*
import kotlinx.android.synthetic.main.reciclerview_item_row.view.*
import com.google.firebase.database.DatabaseError
import java.nio.file.Files.exists
import com.google.firebase.database.DataSnapshot




class ListActivity : AppCompatActivity() {

    lateinit  private var linearLayoutManager: LinearLayoutManager

    lateinit var tabela:DatabaseReference

    lateinit var FirebaseRecyclerAdapter: FirebaseRecyclerAdapter<NotaFiscal, NotaFiscalViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        /*if (FirebaseDatabase.getInstance() == null) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            //vai permitir trabalhar offline.
        }*/
        //definindo o nome da - colecao
        tabela = FirebaseDatabase.getInstance().getReference(getString(R.string.nomeTabelaNotaFiscal))
        val query: Query = tabela.orderByChild("userId").equalTo(FirebaseAuth.getInstance().currentUser!!.uid)

        carregarRecyclerView(query)
        //rvNotaFiscal.adapter = ListAdapter(listOf(), this, {})
        //rvNotaFiscal.layoutManager = LinearLayoutManager(this)

    }

    fun carregarRecyclerView(query : Query) {
        FirebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<NotaFiscal, NotaFiscalViewHolder>(
            NotaFiscal::class.java,
            R.layout.reciclerview_item_row,
            NotaFiscalViewHolder::class.java,
            query //tabela.orderByChild("numeroNota")
        ) {
            override fun populateViewHolder(viewHolder: NotaFiscalViewHolder?, notaFiscal: NotaFiscal?, position: Int) {
                viewHolder?.itemView?.tvNumeroNota?.text = notaFiscal?.numero.toString()
                viewHolder?.itemView?.tvNomeProduto?.text = notaFiscal?.nomeProduto.toString()
                viewHolder?.itemView?.tvQuantidade?.text = notaFiscal?.quantidade.toString()
                if (notaFiscal!!.entrada) {
                    viewHolder?.itemView?.tvEntradaSaida?.text = getString(R.string.entrada)
                } else if (notaFiscal!!.saida){
                    viewHolder?.itemView?.tvEntradaSaida?.text = getString(R.string.saida)
                } else {
                    viewHolder?.itemView?.tvEntradaSaida?.text = getString(R.string.armazenagem)
                }

                //fazendo botão excluir
                viewHolder?.itemView?.ibtnDelete?.setOnClickListener {
                    System.out.println("Nota fiscal:" + position)
                    System.out.println("tabela" + tabela)

                    val builder = AlertDialog.Builder(this@ListActivity)

                    builder.setTitle(getString(R.string.atencao))
                    builder.setMessage(getString(R.string.mensagem_confirmaar_exclusao_nota_fiscal))
                    builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->

                        FirebaseDatabase.getInstance().getReference(getString(R.string.nomeTabelaNotaFiscal)).child(notaFiscal.id).removeValue().addOnCompleteListener{
                            if (it.isSuccessful) {

                                val builderExcluir = AlertDialog.Builder(this@ListActivity)
                                builderExcluir.setTitle(getString(R.string.atencao))
                                builderExcluir.setMessage(getString(R.string.mensagem_nota_fiscal_excluida_sucesso))
                                builder.setPositiveButton(getString(R.string.ok)) {dialogExcluir, witch ->

                                }

                                val dialogExcluir = builderExcluir.create()
                                dialogExcluir.show()
                            } else {
                                val buildErro = AlertDialog.Builder(this@ListActivity)
                                buildErro.setTitle(getString(R.string.erro_firebase))
                                buildErro.setMessage(it.exception?.message)
                                buildErro.setPositiveButton(getString(R.string.ok)) { dialogErro, witch ->

                                }
                                val dialogErro = buildErro.create()
                                dialogErro.show()
                            }
                        }
                    }

                    builder.setNeutralButton("Cancel") { dialog, witch ->

                    }

                    val dialog: AlertDialog = builder.create()

                    dialog.show()
                }

                //pogramando botao editar
                viewHolder!!.itemView.ibtnEditar.setOnClickListener {

                    val intent = Intent(viewHolder.itemView.context, EditActivity::class.java)
                    intent.putExtra("notaFiscal", notaFiscal)
                    viewHolder.itemView.context.startActivity(intent)
                    this@ListActivity.finish()
                }
            }
        }
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {

                    val builder = AlertDialog.Builder(this@ListActivity)

                    builder.setTitle(getString(R.string.atencao))
                    builder.setMessage(getString(R.string.mensagem_nao_tem_nota))
                    builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->

                        val intent = Intent(this@ListActivity, MainActivity::class.java)
                        startActivity(intent)
                        this@ListActivity.finish()
                    }

                    val dialog: AlertDialog = builder.create()

                    dialog.show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        rvNotaFiscal.adapter = FirebaseRecyclerAdapter
        rvNotaFiscal.layoutManager = LinearLayoutManager(this, OrientationHelper.VERTICAL, false )
    }

    class NotaFiscalViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    }

}
