package android.mehdi.soatunisitatrip;

import android.content.Context;
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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>  {

    RequestOptions options ;
    private Context mContext ;
    public List<UserProfil> mVille ;

    public UserAdapter(Context context, List lst)
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
        view=mInflater.inflate(R.layout.item_experience,parent,false);

        final MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.username.setText(mVille.get(position).getNom());
        holder.date_exp.setText(mVille.get(position).getUpload_date());
        holder.description_exp.setText(mVille.get(position).getDescription());
        Glide.with(mContext).load(mVille.get(position).getImage()).apply(options).into(holder.image_exp);






    }

    @Override
    public int getItemCount() {
        return mVille.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView date_exp;
        TextView description_exp;

        ImageView image_exp,image_profil;

        TextView tvname,tvmail;

        LinearLayout linearLayout;


        public MyViewHolder(View itemView) {
            super(itemView);


            username=itemView.findViewById(R.id.post_user_name);

            date_exp=itemView.findViewById(R.id.post_date);
            description_exp=itemView.findViewById(R.id.descriptionn);

            image_exp=itemView.findViewById(R.id.post_image);
            image_profil=itemView.findViewById(R.id.post_profil_image);



            tvname = itemView.findViewById(R.id.nomuser);
            tvmail = itemView.findViewById(R.id.mailuser);

        }
    }
}
