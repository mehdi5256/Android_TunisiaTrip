package android.mehdi.soatunisitatrip;

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

public class AttractionByVilleAdapter extends RecyclerView.Adapter<AttractionByVilleAdapter.MyViewHolder> {

    RequestOptions options ;
    private Context mContext ;
    public  List<Attraction> mVille ;
    public static int a ;

    public static String tel;

    public AttractionByVilleAdapter(Context context, List lst)
    {

        this.mContext = context;
        this.mVille = lst;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_attractionbyville,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext,listeAttractionParVille.class);

                intent.putExtra("attraction_name",mVille.get(viewHolder.getAdapterPosition()).getNomAttraction());
                intent.putExtra("attraction_image",mVille.get(viewHolder.getAdapterPosition()).getImage());
                intent.putExtra("attraction_description",mVille.get(viewHolder.getAdapterPosition()).getDescription());
                intent.putExtra("attraction_siteweb",mVille.get(viewHolder.getAdapterPosition()).getSiteweb());
                intent.putExtra("attraction_email",mVille.get(viewHolder.getAdapterPosition()).getMail());
                intent.putExtra("attraction_telephone",mVille.get(viewHolder.getAdapterPosition()).getTelephone());
                intent.putExtra("attraction_adresse",mVille.get(viewHolder.getAdapterPosition()).getAdresse());

                mContext.startActivity(intent);
            }
        });



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.tvname.setText(mVille.get(position).getNomAttraction());
        holder.tvadresse.setText(mVille.get(position).getAdresse());
        /* holder.tel.setText(mVille.get(position).getTelephone());
        holder.maill.setText(mVille.get(position).getMail());
        holder.siteeweb.setText(mVille.get(position).getSiteweb());*/
        Glide.with(mContext).load(mVille.get(position).getImage()).apply(options).into(holder.imageville);
        tel = mVille.get(position).getTelephone();


    }

    @Override
    public int getItemCount() {
        return mVille.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvname,tvadresse,descrip,tel,maill,siteeweb;
        ImageView imageville;
        LinearLayout linearLayout;


        public MyViewHolder(View itemView){
            super(itemView);
            tvname=itemView.findViewById(R.id.nom_attraction);
            tvadresse=itemView.findViewById(R.id.adresse_attraction);
            imageville=itemView.findViewById(R.id.image_attraction);
            linearLayout=itemView.findViewById(R.id.containerattraction);
            /*descrip = itemView.findViewById(R.id.textView7);
            tel = itemView.findViewById(R.id.tal);
            maill = itemView.findViewById(R.id.mail);*/
/*
            siteeweb = itemView.findViewById(R.id.sitewe);
*/






        }
    }

}
