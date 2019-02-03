package augustolucianetti.com.br.notafiscalapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.augustolucianetti.calculaflex.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val query = FirebaseDatabase.getInstance().getReference(getString(R.string.nomeTabelaUsuario)).orderByChild("userId").equalTo(FirebaseAuth.getInstance().currentUser?.uid)
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val user =  it.getValue(User::class.java)
                    tvNomeUsuario.setText(getString(R.string.olah) + user?.name)
                }
            }
        })
        btnCadastrar.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        btnListar.setOnClickListener {
            val intent = Intent( this, ListActivity::class.java)
            startActivity(intent)
        }

        btnSobre.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        btnSair.setOnClickListener {
            this.finishAndRemoveTask()
        }
    }
}
