package fr.benoitcochet.memo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.parceler.Parcels;

import java.util.Collections;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder>
{
    private List<MemoDTO> listeMemos;
    private AppCompatActivity mainActivity;

    public MemoAdapter(List<MemoDTO> listeMemos, AppCompatActivity mainActivity)
    {
        this.listeMemos = listeMemos;
        this.mainActivity = mainActivity;
    }

    @Override
    public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View viewMemo = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_item_liste, parent, false);
        return new MemoViewHolder(viewMemo);
    }

    @Override
    public void onBindViewHolder(MemoViewHolder holder, int position)
    {
        holder.textViewContenuMemo.setText(listeMemos.get(position).contenu);
    }

    @Override
    public int getItemCount()
    {
        return listeMemos.size();
    }

    public void onItemDismiss(int position)
    {
        if (position > -1)
        {
            AppDatabaseHelper.getDatabase(mainActivity).memoDAO().delete(listeMemos.get(position));
            listeMemos.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean onItemMove(int positionDebut, int positionFin)
    {
        Collections.swap(listeMemos, positionDebut, positionFin);
        notifyItemMoved(positionDebut, positionFin);
        return true;
    }

    public class MemoViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewContenuMemo;

        public MemoViewHolder(View itemView)
        {
            super(itemView);
            textViewContenuMemo = itemView.findViewById(R.id.contenu_memo);
            textViewContenuMemo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    MemoDTO memo = listeMemos.get(getAdapterPosition());

                    if(mainActivity.findViewById(R.id.FragmentLayout) != null)
                    {
                        DetailFragment fragment = new DetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("contenu", memo.contenu);
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.FragmentLayout, fragment, "detail_fragment");
                        fragmentTransaction.commit();
                    }
                    else
                    {
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("memo", Parcels.wrap(memo));
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}
