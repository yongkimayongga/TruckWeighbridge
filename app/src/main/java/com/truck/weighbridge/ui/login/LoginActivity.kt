package com.truck.weighbridge.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.truck.weighbridge.databinding.ActivityLoginBinding
import com.truck.weighbridge.session.SessionManager
import com.truck.weighbridge.ui.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mGoogleSignInClient: GoogleSignInClient

    @Inject
    lateinit var gso: GoogleSignInOptions

    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var binding: ActivityLoginBinding

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    // Method #1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        skipLoginIfUserExist()

        binding.signInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    // Method #2
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //Success case
                val account = task.getResult(ApiException::class.java)
                Toast.makeText(this, account?.displayName, Toast.LENGTH_SHORT).show()
                sessionManager.setToken(account?.id.toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: ApiException) {
                //Error case
                Toast.makeText(this, "error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Method #3
    private fun skipLoginIfUserExist() {
        sessionManager.getToken().observe(this) {
            it?.let {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


}

