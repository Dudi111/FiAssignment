package com.example.fiappscreen

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var checkpan:Boolean= false
    private var checkdob:Boolean= false

    private lateinit var varifyViewmodel:VarificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        varifyViewmodel= ViewModelProvider(this).get(VarificationViewModel::class.java)

        var starttext= "First of the few steps to set You up with a bank account"
        tv_view2.text= "First of the few steps to set"+ "\n" +"you up with a bank account"
        var newtext= "Providing PAN and Date of birth helps us find and fetch your KYC from a central registry by the Government of India. Learn more"
        var spanString= SpannableString(newtext)
        var fcsBlue= ForegroundColorSpan(Color.BLUE)
        spanString.setSpan(fcsBlue, 117, 127, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_discp.text= spanString
        btn_next.setOnClickListener {
            if (et_pan.text.length==0){
                Toast.makeText(this, "Enter PAN details", Toast.LENGTH_SHORT).show()

            }else if (et_date.text.length==0 || et_month.text.length==0 || et_year.text.length==0 ){
                Toast.makeText(this, "Enter Date of birth", Toast.LENGTH_SHORT).show()

            }else {
                varifyViewmodel.pan_verification(et_pan.text.toString())
                varifyViewmodel.DOBverification(
                    et_date.text.toString().toInt(),
                    et_month.text.toString().toInt(),
                    et_year.text.toString().toInt()
                )
            }
        }

        varifyViewmodel.panLivedata.observe(this){
            checkpan = it


            if (checkpan && checkdob){
                Toast.makeText(this, "Details submitted Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        varifyViewmodel.dobLivedata.observe(this){
            checkdob = it

            if (!checkpan){
                Toast.makeText(this, "Invalid PAN number", Toast.LENGTH_SHORT).show()

            }else if (!checkdob){
                Toast.makeText(this, "Invalid date of birth", Toast.LENGTH_SHORT).show()

            }
            if (checkpan && checkdob){
                Toast.makeText(this, "Details submitted Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        tv_noPAN.setOnClickListener {
            finish()
        }
    }





}