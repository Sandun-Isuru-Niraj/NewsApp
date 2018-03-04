package morasquad.me.loginapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sandun Isuru Niraj on 2/27/2018.
 */

public class MyAdapetr extends RecyclerView.Adapter<MyAdapetr.MyAdpterViewHolder>{

    public List<ListItem> listItems;
    private Context context;


    public MyAdapetr(List<ListItem> listItems, Context context){

        this.listItems = listItems;
        this.context = context;

    }

    @Override
    public MyAdpterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_list_item, null);
        return new MyAdpterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyAdpterViewHolder holder, int position) {

        ListItem listItem = listItems.get(position);
        holder.subject.setText(listItem.getHead());
        holder.message.setText(listItem.getDesc());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class MyAdpterViewHolder extends RecyclerView.ViewHolder{

        TextView subject;
        TextView message;

        public MyAdpterViewHolder(View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.textView1);
            message = itemView.findViewById(R.id.textView2);

        }
    }


}
