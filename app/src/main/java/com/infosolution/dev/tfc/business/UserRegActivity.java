package com.infosolution.dev.tfc.business;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.infosolution.dev.tfc.R;

public class UserRegActivity extends AppCompatActivity {
    Button btnnxt;
    private String Contactperson,Storename,Position,Phone1,Phone2,Password,Repassword;
    private  String EmailId,Address,Country,City,ZipCode,Website,OtherInfo;
    private EditText etemail,etadd,etcountry,etcity,etzip,etwebsite,etotherInfo;
    private Spinner spin;
    String[] bankNames={"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria",
            "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
            "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island",
            "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi",
            "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad",
            "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
            "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)",
            "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
            "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia",
            "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan",
            "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia",
            "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala",
            "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)",
            "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)",
            "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati",
            "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan",
            "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of",
            "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique",
            "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of",
            "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal",
            "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria",
            "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama",
            "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico",
            "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia",
            "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia",
            "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia",
            "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands",
            "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname",
            "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic",
            "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo",
            "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands",
            "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States",
            "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam",
            "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen",
            "Yugoslavia", "Zambia", "Zimbabwe"};
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

       Intent intent=getIntent();
        Contactperson=intent.getStringExtra("contactperson");
        Storename=intent.getStringExtra("storename");
        Position=intent.getStringExtra("position");
        Phone1=intent.getStringExtra("phone1");
        Phone2=intent.getStringExtra("phone2");
        Password=intent.getStringExtra("password");
        Repassword=intent.getStringExtra("repassword");


        etemail=findViewById(R.id.et_emailreg);
        etadd=findViewById(R.id.et_addreg);
       // etcountry=findViewById(R.id.et_countryreg);
        etcity=findViewById(R.id.et_cityreg);
        etzip=findViewById(R.id.et_zipreg);
        etwebsite=findViewById(R.id.et_websitereg);
        etotherInfo=findViewById(R.id.et_otherinfo);
        btnnxt=findViewById(R.id.btn_nextreg);

       spin = (Spinner) findViewById(R.id.sp_country);


        //Creating the ArrayAdapter instance having the bank name list
        aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Country=spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        etemail.setTypeface(typefaceregular);
        etadd.setTypeface(typefaceregular);
//        etcountry.setTypeface(typefaceregular);
        etcity.setTypeface(typefaceregular);
        etzip.setTypeface(typefaceregular);
        etwebsite.setTypeface(typefaceregular);
        etotherInfo.setTypeface(typefaceregular);
        btnnxt.setTypeface(typefacebold);





        btnnxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EmailId=etemail.getText().toString();
                Address=etadd.getText().toString();
                Country=etcountry.getText().toString();
                City=etcity.getText().toString();
                ZipCode=etzip.getText().toString();
                Website=etwebsite.getText().toString();
                OtherInfo=etotherInfo.getText().toString();


                Intent intent = new Intent(UserRegActivity.this,UserRegNext.class);

                intent.putExtra("contactper",Contactperson);
                intent.putExtra("storename",Storename);
                intent.putExtra("position",Position);
                intent.putExtra("phone1",Phone1);
                intent.putExtra("phone2",Phone2);
                intent.putExtra("password",Password);

                intent.putExtra("emialid",EmailId);
                intent.putExtra("address",Address);
                intent.putExtra("country",Country);
                intent.putExtra("city",City);
                intent.putExtra("zip",ZipCode);
                intent.putExtra("website",Website);
                intent.putExtra("otherinfo",OtherInfo);

                startActivity(intent);
            }
        });
    }
}
