package com.padcmyanmar.smtz.wechatredesign.network

import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseAuthManagerImpl : AuthManager {

    private val mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var mFirestoreApi: FirestoreApi = FirestoreDatabaseImpl

    override fun login(
        phone: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAuth.signInWithEmailAndPassword("$phone@gmail.com", password).addOnCompleteListener {

            if(it.isSuccessful && it.isComplete){
                val userUID = Firebase.auth.currentUser?.uid
                userUID?.let{uid ->
                    onSuccess(uid)
                }
            } else {
                onFailure(it.exception?.message ?: "Please Check The Internet Connection")
            }
        }
    }

    override fun signUp(
        phone: String,
        password: String,
        userName: String,
        dateOfBirth: String,
        gender: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val email = "$phone@gmail.com"
        val user = Firebase.auth.currentUser

        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful && it.isComplete) {

                // create user in cloud firestore
                val userUID = user?.uid
                mFirestoreApi.addUser(phone, password, userName, dateOfBirth, gender, userUID?:"", "")
//                    Log.d("test" , "$phone $password $userName $dateOfBirth $gender ${userUID}}")
                userUID?.let{ uid ->
                    onSuccess(uid)
                }
            } else {
                onFailure(it.exception?.message ?: "Please check the internet connection")
            }
        }
    }

    override fun verify(phone: String, code: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val testCode = "1111"

        if(code == testCode) {
            onSuccess()
        } else {
            onFailure("Wrong OTP, please try again")
        }
    }

    override fun getUserName(): String {
        return ""
    }
}