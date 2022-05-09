package sg.edu.rp.c346.id20041194.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {

    //Step 1: Declare the field variables
    TextView Display1;
    TextView Display2;
    Button btnDisplaySplit;
    Button btnDisplayReset;
    EditText BillAmount;
    EditText PaxAmount;
    EditText PercentAmount;
    ToggleButton btnDisplaySVS;
    ToggleButton btnDisplayGST;
    RadioGroup rgGrp;
    RadioButton Cash;
    RadioButton PayNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Step 2: Link the field variables to UI components in layout
        Display1 = findViewById(R.id.TotalBill);
        Display2 = findViewById(R.id.displayPayPerPerson);
        btnDisplaySplit = findViewById(R.id.btnSplit);
        btnDisplayReset = findViewById(R.id.btnReset);
        BillAmount = findViewById(R.id.Amountnum);
        PaxAmount = findViewById(R.id.Paxnum);
        PercentAmount = findViewById(R.id.Percentfloat);
        btnDisplaySVS = findViewById(R.id.btnSVS);
        btnDisplayGST = findViewById(R.id.btnGST);
        rgGrp = findViewById(R.id.payBy);
        Cash = findViewById(R.id.btnPayCash);
        PayNow = findViewById(R.id.btnPayNow);

        btnDisplaySplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for the action
                double Amount = Double.parseDouble(BillAmount.getText().toString());
                double People = Double.parseDouble(PaxAmount.getText().toString());
                double Discount = Double.parseDouble(PercentAmount.getText().toString());

                double gst = 1.0;
                if (btnDisplaySVS.isChecked()) {
                    gst *= 1.1;
                } else if (btnDisplayGST.isChecked()) {
                    gst *= 1.07;
                } else if (btnDisplaySVS.isChecked() && btnDisplayGST.isChecked()) {
                    gst *= 2.17;
                }

                double beforeSplitting = Amount * gst;
                double totalBill = beforeSplitting * (1 - (Discount/100));

                double splitBill = totalBill/People;
                Display1.setText(String.format("Total Bill: $%.2f", totalBill));

                int checkRadioId = rgGrp.getCheckedRadioButtonId();
                if (checkRadioId == R.id.btnPayCash) {
                    Display2.setText("Each pays: $" + splitBill + " in cash");

                } else if (checkRadioId == R.id.btnPayNow) {
                    Display2.setText("Each pays: $" + splitBill + " via paynow to 96745372");
                }
            }
        });

        btnDisplayReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for the action
                BillAmount.getText().clear();
                PaxAmount.getText().clear();
                PercentAmount.getText().clear();

                if (btnDisplaySVS.isChecked()) {
                    btnDisplaySVS.setChecked(false);
                }
                if (btnDisplayGST.isChecked()) {
                    btnDisplayGST.setChecked(false);
                }

                Display1.setText("");
                Display2.setText("");

                if (Cash.isChecked()) {
                    Cash.setChecked(false);

                }

                if (PayNow.isChecked()) {
                    PayNow.setChecked(false);
                }


            }
        });
    }
}