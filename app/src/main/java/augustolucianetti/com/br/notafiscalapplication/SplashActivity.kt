package augustolucianetti.com.br.notafiscalapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val TEMPO_AGUARDO_SPLASHSCREEN = 3500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val preferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
        val isFirstOpen = preferences.getBoolean("openFirst", true)

        if (isFirstOpen) {
            markAlreadyOpen(preferences)
            showSplash()
        } else {
            showLogin()
        }
    }

    private fun markAlreadyOpen(preferences: SharedPreferences) {
        val editor = preferences.edit()
        editor.putBoolean("openFirst", false)
        editor.apply()
    }

    private fun showLogin() {
        val nextStep = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(nextStep)
        finish()
    }

    private fun showSplash() {
//Carrega a animacao
        val anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)
        anim.reset()
        ivLogo.clearAnimation()
//Roda a animacao
        ivLogo.startAnimation(anim)
//Chama a próxima tela após 3,5 segundos definido na SPLASH_DISPLAY_LENGTH
        Handler().postDelayed({
            val proximaTela = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(proximaTela)
            finish()
        }, TEMPO_AGUARDO_SPLASHSCREEN)
    }
}
