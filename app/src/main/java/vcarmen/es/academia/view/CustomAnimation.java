package vcarmen.es.academia.view;

import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

public final class CustomAnimation {

    public static View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition ) {
            return listView.getAdapter().getView(position, null, listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public static void startAnimationRight(View view, ListView listAlumno, View row) {

        TranslateAnimation animateRight = new TranslateAnimation(0, view.getWidth(), 0 , 0);
        animateRight.setDuration(500);
        animateRight.setFillAfter(true);

        row.startAnimation(animateRight);
        row.setVisibility(View.GONE);
    }

    public static void startAnimationLeft(View view, ListView listAlumno, View row) {

        final TranslateAnimation animateLeft = new TranslateAnimation(view.getWidth(), 0 ,0,0);
        animateLeft.setDuration(500);
        animateLeft.setFillAfter(true);

        row.setVisibility(View.VISIBLE);
        row.startAnimation(animateLeft);
    }
}
