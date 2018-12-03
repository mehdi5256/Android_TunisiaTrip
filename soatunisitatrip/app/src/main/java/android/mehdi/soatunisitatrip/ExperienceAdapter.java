package android.mehdi.soatunisitatrip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.MyViewHolder> {
    RequestOptions options ;
    private Context mContext ;
    public List<experience> mExperience ;

    public  ExperienceAdapter(Context context,List lst)
    {
        this.mContext = context;
        this.mExperience = lst;
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
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



        holder.username.setText(mExperience.get(position).getNom());

        holder.date_exp.setText(mExperience.get(position).getUpload_date());
        holder.description_exp.setText(mExperience.get(position).getDescription());

        Glide.with(mContext).load(mExperience.get(position).getNom_image()).apply(options).into(holder.image_exp);


    }

    @Override
    public int getItemCount() {
        return mExperience.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView username;
        TextView date_exp;
        TextView description_exp;

        ImageView image_exp;



        public MyViewHolder(View itemView) {
            super(itemView);


            username=itemView.findViewById(R.id.post_user_name);

            date_exp=itemView.findViewById(R.id.post_date);
            description_exp=itemView.findViewById(R.id.descriptionn);

            image_exp=itemView.findViewById(R.id.post_image);

        }
    }
}
