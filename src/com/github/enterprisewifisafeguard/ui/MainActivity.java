package com.github.enterprisewifisafeguard.ui;

import java.util.Set;

import com.github.enterprisewifisafeguard.R;
import com.github.enterprisewifisafeguard.core.WifiSetup;
import com.github.enterprisewifisafeguard.utils.CertificateManager;

import android.app.Activity;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends Activity {
	private Spinner fcert = null;
	private Spinner feap = null;
	private Spinner fPhase2 = null;
	private EditText fssid = null;
	private EditText fuser = null;
	private EditText fpass = null;
	private EditText fanonymous = null;
	private EditText fCn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get Values for the Certificate Chooser
        fcert = (Spinner) findViewById(R.id.fca);
        Set<String> certName = CertificateManager.getInstance(this.getApplicationContext()).getAllCertNames();
        ArrayAdapter<String> certAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,certName.toArray(new String[certName.size()]));
        fcert.setAdapter(certAdapter);
        //get the fields
        feap = (Spinner) findViewById(R.id.feap);
    	fPhase2 = (Spinner) findViewById(R.id.fphase2);
    	fssid = (EditText) findViewById(R.id.fssid);
    	fuser = (EditText) findViewById(R.id.fuser);
    	fpass = (EditText) findViewById(R.id.fpass);
    	fanonymous = (EditText) findViewById(R.id.fano);
    	fCn = (EditText) findViewById(R.id.fcn);
    	//button listener for config button
        Button button= (Button) findViewById(R.id.config_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              buttonPressed();
            }
        });

        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void buttonPressed() {
    	int eap;
    	int phase2;
    	//Select eap Mehod
    	String seap = feap.getSelectedItem().toString();
    	if (seap=="PEAP") {
    		eap = WifiEnterpriseConfig.Eap.PEAP;
    	}
    	else if (seap=="PWD") {
    		eap = WifiEnterpriseConfig.Eap.PWD;
    	}
    	else if (seap=="TLS") {
    		eap = WifiEnterpriseConfig.Eap.TLS;
    	}
    	else if (seap=="TTLS") {
    		eap = WifiEnterpriseConfig.Eap.TTLS;
    	}
    	else {
    		eap = -1;
    	}
    	//Select Phase2 Method
    	String sphase2 = fPhase2.getSelectedItem().toString();
         if (sphase2=="MSCHAP") {
    		phase2 = WifiEnterpriseConfig.Phase2.MSCHAP;
    	}
         else if (sphase2=="MSCHAPV2") {
     		phase2 = WifiEnterpriseConfig.Phase2.MSCHAPV2;
     	}
         else if (sphase2=="PAP") {
      		phase2 = WifiEnterpriseConfig.Phase2.PAP;
      	}
         else {
        	 phase2= -1;
         }
         
    	WifiSetup setup = new WifiSetup(this.getApplicationContext());
    	setup.createConnection("\""+fssid.getText().toString()+"\"", fuser.getText().toString()
    			, fpass.getText().toString(), fanonymous.getText().toString(), 
    			fCn.getText().toString(), eap, phase2, fcert.getSelectedItem().toString());
    }
}
