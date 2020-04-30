package fr.benoitcochet.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailFragment fragment = new DetailFragment();
        //Récupération de l'objet
        MemoDTO memo = Parcels.unwrap(getIntent().getParcelableExtra("memo"));
        //Passage au fragment
        Bundle bundle = new Bundle();
        bundle.putString("contenu", memo.contenu);
        fragment.setArguments(bundle);
        //Ajout du fragment au layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.conteneur_fragment, fragment, "detail_fragment");
        fragmentTransaction.commit();
    }
}
