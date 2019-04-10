package android.mehdi.soatunisitatrip;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    RequestOptions options ;
    private Context mContext ;
    public static List<Ville> mVille ;
    public static int a ;
    public static String nom_ville;
    public static String lat;
    public static String longitude;

    public RecyclerViewAdapter(Context mContext, List lst) {


        this.mContext = mContext;
        this.mVille = lst;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

    }

    public RecyclerViewAdapter() {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view,view1 ;

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_ville,parent,false);


        final MyViewHolder viewHolder = new MyViewHolder(view);


        // click listener here

        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a = mVille.get(viewHolder.getAdapterPosition()).getId();
                nom_ville= mVille.get(viewHolder.getAdapterPosition()).getNomville();
                lat=mVille.get(viewHolder.getAdapterPosition()).getLatitude();
                longitude=mVille.get(viewHolder.getAdapterPosition()).getLongitude();
             //   Toast.makeText(mContext,"id_ville:"+ lat+longitude, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,Type_Attraction_Activity.class);
                intent.putExtra("ville_name",mVille.get(viewHolder.getAdapterPosition()).getNomville());
                intent.putExtra("ville_image",mVille.get(viewHolder.getAdapterPosition()).getImage());
                mContext.startActivity(intent);


            }


        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvname.setText(mVille.get(position).getNomville());
        Glide.with(mContext).load(mVille.get(position).getImage()).apply(options).into(holder.imageville);

    }

    @Override
    public int getItemCount() {
        return mVille.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvname;
        ImageView imageville;
        LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);

            view_container= itemView.findViewById(R.id.containerville);
            tvname = itemView.findViewById(R.id.nom_ville);
            imageville = itemView.findViewById(R.id.image_ville);



            }

    }




    }