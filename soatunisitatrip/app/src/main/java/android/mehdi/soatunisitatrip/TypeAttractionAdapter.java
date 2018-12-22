package android.mehdi.soatunisitatrip;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class TypeAttractionAdapter extends RecyclerView.Adapter<TypeAttractionAdapter.MyViewHolder> {

    RequestOptions options ;
    private Context mContext ;
    public static List<Type_Attraction> mData;
    public static int id_attraction ;



    public  TypeAttractionAdapter(Context mContext,List lst )
    {
        this.mContext = mContext;
        this.mData = lst;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_type_attraction,parent,false);
        // click listener here
        MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.attraction_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_attraction = mData.get(viewHolder.getAdapterPosition()).getId();
                Intent intent = new Intent(mContext,AttractionParVille.class);

                mContext.startActivity(intent);


/*
                Toast.makeText(mContext,"id type_attraction:"+ mData.get(viewHolder.getAdapterPosition()).getId()+"idVille:"+ RecyclerViewAdapter.a, Toast.LENGTH_SHORT).show();
*/
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvname.setText(mData.get(position).getNom());

        Glide.with(mContext).load(mData.get(position).getImage()).apply(options).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout attraction_container;
        TextView tvname;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvtypeAttraction);
            image = itemView.findViewById(R.id.imgContact);
            attraction_container = itemView.findViewById(R.id.container_attraction);
        }
    }
}
