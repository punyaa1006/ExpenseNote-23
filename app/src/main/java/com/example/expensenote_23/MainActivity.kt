package com.example.expensenote_23

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText
    private lateinit var btnSave: Button
    private lateinit var btnClear: Button
    private lateinit var tvLastItem: TextView
    private lateinit var tvTotal: TextView

    //ตัวแปรสำหรับเก็บค่ายอดรวมทั้้งหมด
    private var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //เชื่อมโยงตัวแปร เข้ากับID ในหน้า XML
        etAmount = findViewById<EditText>(R.id.etAmount)
        etNote = findViewById<EditText>(R.id.etNote)
        btnSave = findViewById<Button>(R.id.btnSave)
        btnClear = findViewById<Button>(R.id.btnClear)
        tvLastItem = findViewById<TextView>(R.id.tvLastItem)
        tvTotal = findViewById<TextView>(R.id.tvTotal)


        btnClear.setOnClickListener {
            etAmount.text.clear()
            etNote.text.clear()
            etAmount.clearFocus()
        }
        //กำหนดการทำงานเมื่อผู้ใช้ทำการกดปุ่มเพิ่มรายการ
        btnSave.setOnClickListener {
            //สร้างตัวแปรที่เอาไว้เก็บข้อมูลของช่อง
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()

            // ทำการตรวจสอบวาผู้ใช้ทำการกรอกจำนวนเงินแล้วหรือยัง
            if (amountText.isEmpty()) {
                etAmount.error = "กรุณาระบุจำนวนเงิน"
                etAmount.requestFocus()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            //แจ้งเตือนว่าผู้ใช้กรอกตัวเลขมา ไม่ใชตัวหนังสือ
            if (amount == null || amount <= 0) {
                etAmount.error = "จำนวนเงินไม่ถูกต้อง"
                etAmount.requestFocus()
                return@setOnClickListener
            }
            //เอาจำนวนเงินมารวมกัน
            total += amount
            //ตรวจสอบว่ามีการกรอกคำอธิบายมั้ย ถ้าไม่มี ให้เป็นคำว่า"ไม่ระบุ"
            val showNote = if (noteText.isNotEmpty()) noteText else "ไม่ระบุ"

            tvTotal.text = "รายการล่าสุด: $showNote - %.2f บาท".format(amount)
            tvLastItem.text = "ยอดรวมวันนี: %.2f บาท".format(total)
            Toast.makeText(this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show()

            etAmount.text.clear()
            etNote.text.clear()
            etAmount.clearFocus()
        }
        btnClear.setOnClickListener{
            total = 0.0

            etAmount.text.clear()
            etNote.text.clear()

            tvLastItem.text =   "รายการล่าสุด: -"
            tvTotal.text =  "ยอดรวมวันนี้: 0.00 บาท"
        }
    }
}
