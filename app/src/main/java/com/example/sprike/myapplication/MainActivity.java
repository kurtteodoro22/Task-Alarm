package com.example.sprike.myapplication;


import android.app.FragmentTransaction;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    public static boolean passarFragment = false;
    private Button btnAgendar;
    private EditText edtAtividade;
    private FragmentItens fragmentItens;
    private FragmentNoItens fragmentNoItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAgendar = (Button) findViewById(R.id.btnTaskAgendarPessoal);
            btnAgendar.setOnClickListener(clickBtnAgendarPessoal());
        edtAtividade = (EditText) findViewById(R.id.editTasktPessoal);
            edtAtividade.setOnKeyListener(enterPressed());


        abrirFragment();

    }

    public void abrirFragment(){
        if(passarFragment){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            fragmentItens = new FragmentItens();
            ft.replace(R.id.layoutFragment, fragmentItens, "fragmentItens");
            ft.commit();
        }else{
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            fragmentNoItens = new FragmentNoItens();
            ft.add(R.id.layoutFragment, fragmentNoItens, "fragmentNoItens");
            ft.commit();
        }
    }

    public View.OnClickListener clickBtnAgendarPessoal(){
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtAtividade.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Adicione uma atividade", Toast.LENGTH_SHORT).show();
                }else{
                    if(!passarFragment) {
                        passarFragment = true;
                        abrirFragment();
                        fragmentItens.load(edtAtividade.getText().toString());
                        edtAtividade.setText("");
                        FragmentCerteza fragmentCerteza = new FragmentCerteza();
                        fragmentCerteza.show(getFragmentManager(), "casinha");
                    }else{
                        fragmentItens.load(edtAtividade.getText().toString());
                        edtAtividade.setText("");
                    }
                }
            }
        };

        return clickListener;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pessoal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.item_delete_pessoal:
                if(passarFragment){
                    fragmentItens.deleteAll();
                    passarFragment = false;
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void passarFragment(){
        passarFragment = false;
    }

    public View.OnKeyListener enterPressed(){
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER){
                    if(edtAtividade.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Adicione uma atividade", Toast.LENGTH_SHORT).show();
                    }else{
                        if(!passarFragment) {
                            passarFragment = true;
                            abrirFragment();
                            fragmentItens.load(edtAtividade.getText().toString());
                            edtAtividade.setText("");
                        }else{
                            fragmentItens.load(edtAtividade.getText().toString());
                            edtAtividade.setText("");
                        }
                    }
                }
                return false;
            }
        };
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
