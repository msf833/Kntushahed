package ir.madamas.kntushahed.kntushahed.classes;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import ir.madamas.kntushahed.kntushahed.R;

/**
 * Created by NFP_7037 on 6/29/2017.
 */

public class bookletAdapter extends BaseAdapter {

    List<Booklet> bookletList;
    Context context;

    public bookletAdapter(Context applicationContext, List<Booklet> bookletList) {
        this.bookletList = bookletList;
        this.context = applicationContext;
    }

    @Override
    public int getCount() {
        return bookletList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookletList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = View.inflate(context,R.layout.booklet_list_item, null);


        /*if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.booklet_list_item, null, false);
        }*/

        Booklet booklet = (Booklet) getItem(position);

        if (booklet != null) {
            //ImageView imgView_bookletItem = (ImageView) row.findViewById(R.id.imgView_bookletItem);

            TextView bookletName = (TextView) row.findViewById(R.id.textView_bookletItem);
            bookletName.setText(booklet.name);

            TextView textView_bookletID = (TextView) row.findViewById(R.id.textView_bookletID);
            textView_bookletID.setText(booklet.ID);

            TextView parantez = (TextView) row.findViewById(R.id.parantez);
            parantez.setVisibility(View.GONE);

            if(Build.VERSION.SDK_INT > 19) {
                RatingBar ratingBar_bookletItem = (RatingBar) row.findViewById(R.id.ratingBar_bookletItem);
                ratingBar_bookletItem.setRating(Float.parseFloat(booklet.rate));
            }else {
                RatingBar ratingBar_bookletItem = (RatingBar) row.findViewById(R.id.ratingBar_bookletItem);
                ratingBar_bookletItem.setNumStars(1);
                parantez.setVisibility(View.VISIBLE);
                TextView ratingBarText_bookletItem = (TextView) row.findViewById(R.id.ratingBarText_bookletItem);
                ratingBarText_bookletItem.setText(booklet.rate+")");
            }





            row.setTag(R.string.avali, booklet.ID.toString());
            row.setTag(R.string.dovomi, booklet.name.toString());
            row.setTag(R.string.sevomi, booklet.rate.toString());
            row.setTag(R.string.chaharomi, booklet.level.toString());
            row.setTag(R.string.panjomi, booklet.link.toString());
            row.setTag(R.string.shishomi, booklet.price.toString());
            row.setTag(R.string.haftomi, booklet.size.toString());
            // Toast.makeText(getContext(), Oevent.getActiveFlag(), Toast.LENGTH_SHORT).show();
        }

        return row;
    }

}
