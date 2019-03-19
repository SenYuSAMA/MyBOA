package senyu.design.myboa;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;

public class MyItemDragAndSwipeCallback extends ItemDragAndSwipeCallback {
    public MyItemDragAndSwipeCallback(BaseItemDraggableAdapter adapter) {
        super(adapter);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        return true;
    }
}
