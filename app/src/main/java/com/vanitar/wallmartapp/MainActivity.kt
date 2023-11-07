package com.vanitar.wallmartapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.dakam.wallmartapp.R
import java.time.Duration
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var userList:ArrayList<User> = loadCredentials()
        val regIntent = intent

     var loginBtn = findViewById<Button>(R.id.btn_login)
        var forgot = findViewById<TextView>(R.id.forgot_pwd)
        var email = findViewById<EditText>(R.id.email)
        forgot.setOnClickListener{
            var email = email.text?.toString()
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            var userPwd:String? = ""
            for(u in userList){
                if(u.username.equals(email)){
                    userPwd = u.password;
                }
            }
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf( email));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Password Reset");
            if(email !=null && userPwd !=null) {
                intent.putExtra(Intent.EXTRA_TEXT, "your password is "+userPwd)
                startActivity(intent)
            }

        }
        var createBtn = findViewById<Button>(R.id.create)

        loginBtn.setOnClickListener {

            var password = findViewById<EditText>(R.id.password)

            for(user in userList){
                if(user.username.equals(email.text.toString()) && user.password.equals(password.text.toString())){
                    val intent = Intent(this, ShoppingActivity::class.java)
                        intent.putExtra("username", email.text.toString())
                        startActivity(intent)
                }
            }
        }

        var resultContracts = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if(result.resultCode == Activity.RESULT_OK) {
                var res = result.data?.getSerializableExtra("user")
                if(res !=null){
                    var user = res as User
                    userList.add(user)

                }

            }

        }


        createBtn.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            resultContracts.launch(intent)
        }



    }

    fun loadCredentials():ArrayList<User> {
        var vanitar: User = User("vanitar","heavens","vn@miu.edu","test1");
        var alex: User = User("alex","mode","am@miu.edu","test1");
        var jack: User = User("jack","ken","jk@miu.edu","test1");
        var win: User = User("win","pox","wp@miu.edu","test1");
        var dan: User = User("dan","don","dd@gmail.com","test1");

        var userList = arrayListOf<User>(vanitar,alex,jack,win,dan)

        return userList;
    }
}