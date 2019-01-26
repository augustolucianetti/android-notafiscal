package augustolucianetti.com.br.notafiscalapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import augustolucianetti.com.br.notafiscalapplication.model.NotaFiscal
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.reciclerview_item_row.view.*


class ListActivity : AppCompatActivity() {

    lateinit  private var linearLayoutManager: LinearLayoutManager

    lateinit var tabela:DatabaseReference

    lateinit var FirebaseRecyclerAdapter: FirebaseRecyclerAdapter<NotaFiscal, NotaFiscalViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        if (FirebaseDatabase.getInstance() == null) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            //vai permitir trabalhar offline.
        }
        //definindo o nome da - colecao
        tabela = FirebaseDatabase.getInstance().getReference("notaFiscal")

        carregarRecyclerView()
        //rvNotaFiscal.adapter = ListAdapter(listOf(), this, {})
        //rvNotaFiscal.layoutManager = LinearLayoutManager(this)

    }

    fun carregarRecyclerView() {
        FirebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<NotaFiscal, NotaFiscalViewHolder>(
            NotaFiscal::class.java,
            R.layout.reciclerview_item_row,
            NotaFiscalViewHolder::class.java,
            tabela //tabela.orderByChild("numeroNota")
        ) {
            override fun populateViewHolder(viewHolder: NotaFiscalViewHolder?, notaFiscal: NotaFiscal?, position: Int) {
                viewHolder?.itemView?.tvNumeroNota?.text = notaFiscal?.numero.toString()
                viewHolder?.itemView?.tvNomeProduto?.text = notaFiscal?.nomeProduto.toString()
                viewHolder?.itemView?.tvQuantidade?.text = notaFiscal?.quantidade.toString()
                if (notaFiscal!!.entrada) {
                    viewHolder?.itemView?.tvEntradaSaida?.text = "Saída"
                } else {
                    viewHolder?.itemView?.tvEntradaSaida?.text = "Entrada"
                }
            }
        }

        rvNotaFiscal.adapter = FirebaseRecyclerAdapter
        rvNotaFiscal.layoutManager = LinearLayoutManager(this, OrientationHelper.VERTICAL, false )
    }

    class NotaFiscalViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    }

}