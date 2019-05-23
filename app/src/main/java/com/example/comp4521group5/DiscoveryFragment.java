package com.example.comp4521group5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


public class DiscoveryFragment extends Fragment {

    private int[] resId = new int[]{
            R.drawable.firebase_lockup_400, R.mipmap.contacts_press, R.drawable.ic_person_white,R.mipmap.contacts
    };
    private int count = 0;
ImageView iv;
    private View mView;
    private GestureDetector mGestureDetector;
    private int verticalMinistance = 100;
    private int minVelocity = 10;


    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_discovery, null);
//        iv = (ImageView) view.findViewById(R.id.imageView);
//        iv.setOnTouchListener(iv);
//
//        return view;
//
//    }
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_discovery, container, false);
            initViews();
            iv = (ImageView) mView.findViewById(R.id.imageView);

        } else if (mView.getParent() != null) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
        return mView;
    }


    private void initViews() {
        mGestureDetector = new GestureDetector(getActivity(), new LearnGestureListener());
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }


    public class LearnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            if (e1.getX() - e2.getX() > verticalMinistance && Math.abs(velocityX) > minVelocity) {
//                showToast("left");
//            } else if (e2.getX() - e1.getX() > verticalMinistance && Math.abs(velocityX) > minVelocity) {
//                showToast("right");
//            } else
                if (e1.getY() - e2.getY() > verticalMinistance && Math.abs(velocityY) > minVelocity) {
                showToast("up");
                count++;
                count%=(resId.length);
            } else if (e2.getY() - e1.getY() > verticalMinistance && Math.abs(velocityY) > minVelocity) {
                showToast("down");
                    count--;
                    count=(count+resId.length)%(resId.length);
            }
                iv.setImageResource(resId[count]);
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

}
