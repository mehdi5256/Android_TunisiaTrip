package android.mehdi.soatunisitatrip.Accueil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.mehdi.soatunisitatrip.R;
import android.mehdi.soatunisitatrip.RecyclerViewAdapter;
import android.mehdi.soatunisitatrip.Type_Attraction_Activity;
import android.mehdi.soatunisitatrip.Ville;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class AdapterViewPager extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Ville> villes ;
    private ImageLoader imageLoader;


    //private Integer[] images = {R.drawable.test,R.drawable.jam,R.drawable.dogga};

    public AdapterViewPager (List<Ville> mvilles,Context context)
    {
        this.villes=mvilles;
        this.context=context;
    }

    @Override
    public int getCount() {
        return villes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.accueiltest,null);


        Ville ville = villes.get(position);
        TextView textViewnomville = (TextView) view.findViewById(R.id.tvtestnomville);
        ImageView imageView  = (ImageView)view.findViewById(R.id.testaccueilimg);
        //imageView.setImageResource(images[position]);
        textViewnomville.setText(villes.get(position).getNomville());
        imageLoader=CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(ville.getImage(),ImageLoader.getImageListener(imageView,R.drawable.logo_final,android.R.drawable.ic_dialog_alert));

       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(context,Type_Attraction_Activity.class);
                intent.putExtra("ville_id",villes.get(position).getId());
                intent.putExtra("ville_name",villes.get(position).getNomville());
                intent.putExtra("ville_image",villes.get(position).getImage());
                context.startActivity(intent);

            }
        });*/


        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);

        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object ;
        vp.removeView(view);
    }
}
