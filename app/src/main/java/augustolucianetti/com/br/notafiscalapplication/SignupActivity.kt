package augustolucianetti.com.br.notafiscalapplication

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import br.com.augustolucianetti.calculaflex.extention.getValue
import br.com.augustolucianetti.calculaflex.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btCreate.setOnClickListener {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                inputEmail.getValue(),
                inputPassword.getValue()
            ).addOnCompleteListener {

                if (it.isSuccessful) {
                    saveInDatabase()
                } else {
                    val builder = AlertDialog.Builder(this@SignupActivity)
                    builder.setMessage(it.exception?.message)
                    builder.setPositiveButton(getString(R.string.ok)) { dialog, witch ->

                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }

    private fun saveInDatabase() {
        val user = User(inputName.getValue(), inputEmail.getValue(), inputPhone.getValue())
        FirebaseDatabase.getInstance()
                .getReference(getString(R.string.nomeTabelaUsuario))
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(user)
                .addOnCompleteListener {

                    val builder = AlertDialog.Builder(this@SignupActivity)
                    builder.setMessage(getString(R.string.usuario_criado_sucesso))


                    if (it.isSuccessful) {

                        builder.setPositiveButton(getString(R.string.ok)) {dialog, whitch ->

                            val intent = Intent()
                            intent.putExtra("email", inputEmail.getValue())
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }

                        val dialog = builder.create()
                        dialog.show()
                    } else {

                        builder.setTitle(R.string.erro_firebase)
                        builder.setMessage(it.exception?.message)
                        builder.setPositiveButton(getString(R.string.ok)) {dialog, witch ->

                        }

                        val dialogErro = builder.create()
                        dialogErro.show()
                    }
                }
    }

}
