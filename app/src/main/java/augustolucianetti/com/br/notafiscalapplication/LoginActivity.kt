package augustolucianetti.com.br.notafiscalapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.augustolucianetti.calculaflex.extention.getValue
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var myAuth: FirebaseAuth

    private val newUserRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val preferences = getSharedPreferences("manterConectado", Context.MODE_PRIVATE)
        myAuth = FirebaseAuth.getInstance()
        val manterConectado = preferences.getBoolean("manterConectado", false)
        if (manterConectado) {
            goHome()
        }

        btLogin.setOnClickListener{
            myAuth.signInWithEmailAndPassword(
                inputLoginEmail.getValue(),
                inputLoginPassword.getValue()
            ).addOnCompleteListener {

                val preferences = getSharedPreferences("manterConectado", Context.MODE_PRIVATE)
                if(cbManterConectado.isChecked) {
                    val editor = preferences.edit()
                    editor.putBoolean("manterConectado", true)
                    editor.apply()
                }

                if (it.isSuccessful) {
                    goHome()
                } else {
                    Toast.makeText(this@LoginActivity, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        btSignup.setOnClickListener {
            startActivityForResult(Intent(this, SignupActivity::class.java ), newUserRequestCode)
        }
    }

    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            inputLoginEmail.setText(data?.getStringExtra("email"))
        }
    }
}
