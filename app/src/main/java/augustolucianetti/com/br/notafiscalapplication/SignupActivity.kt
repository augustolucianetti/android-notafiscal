package augustolucianetti.com.br.notafiscalapplication

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
                    Toast.makeText(this@SignupActivity,
                        it.exception?.message, Toast.LENGTH_SHORT).show()
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

                    if (it.isSuccessful) {
                        Toast.makeText(this@SignupActivity,
                                getString(R.string.usuario_criado_sucesso), Toast.LENGTH_SHORT).show()

                        val intent = Intent()
                        intent.putExtra("email", inputEmail.getValue())
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    } else {
                        System.out.println(getString(R.string.erro_firebase) + it.exception)
                        Toast.makeText(this@SignupActivity,
                                it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
    }

}
