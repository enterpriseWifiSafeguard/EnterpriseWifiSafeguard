package com.github.enterprisewifisafeguard.ui;

import java.util.Set;

import com.github.enterprisewifisafeguard.R;
import com.github.enterprisewifisafeguard.utils.CertificateManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner fcert = (Spinner) findViewById(R.id.fca);
        Set<String> certName = CertificateManager.getInstance(this.getApplicationContext()).getAllCertNames();
        ArrayAdapter<String> certAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,certName.toArray(new String[certName.size()]));
        fcert.setAdapter(certAdapter);
        
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
}
