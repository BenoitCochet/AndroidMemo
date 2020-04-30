package fr.benoitcochet.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    MemoAdapter memoAdapter;
    TextInputEditText inputMemo;
    List<MemoDTO> memolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabaseHelper.getDatabase(this);

        //Init recycler
        RecyclerView recyclerView = findViewById(R.id.RecyclerMemo);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Récupération des données depuis la BDD
        memolist = AppDatabaseHelper.getDatabase(this).memoDAO().getListeMemos();
        //Init de l'adapter
        memoAdapter = new MemoAdapter(memolist, this);
        recyclerView.setAdapter(memoAdapter);

        //Init input nouveau mémo
        inputMemo = findViewById(R.id.InputMemo);

        //Init bouton nouveau mémo
        Button btnOk = findViewById(R.id.ButtonOK);
        btnOk.setOnClickListener(this);

        //Init interactions avec les mémos (swipe et move)
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(memoAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /*
    *  Fonction de clique pour la creation d'un mémo
    * */
    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.ButtonOK)
        {
            //Création du mémo
            MemoDTO newMemo = new MemoDTO(inputMemo.getText().toString());
            //Ajout dans la BDD
            AppDatabaseHelper.getDatabase(view.getContext()).memoDAO().insert(newMemo);
            //Actualisation de la liste
            memolist.add(newMemo);
            memoAdapter.notifyItemInserted(memolist.size());
            //Reset de l'input
            inputMemo.setText("");
        }

    }
}
