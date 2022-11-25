package com.example.project;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FirstFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ImageView enter;

    RecyclerAdapter.RecyclerViewClickListener listener;
    List<Task> myList;
    List<Task> archivedTasks = new ArrayList<>();
    TextView predictedTime;
    LocalDate date = LocalDate.now();
//    String today=date.toString();
//    String exactDate=today.substring(date.getDayOfMonth());



    int small=0, big=0, medium=0, very_big=0;
    String url =  "https://padlle-exact-time.herokuapp.com/predict";

    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirstFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        Log.d("Today", String.valueOf(date.getDayOfWeek()));
        listUpdate();
//        Log.d("date",date.toString());
        Map<String, Object> mydate = new HashMap<>();
        mydate.put("completedTasks",0);
        db.collection("users").document(user.getUid()).collection("Completed").document(date.toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("date", "Document exists!");
                    } else {
                        Log.d("date", "Document does not exist!");

                        //add to doc

                        db.collection("users").document(user.getUid()).collection("Completed").document(date.toString())
                                .set(mydate)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("date", "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("date", "Error writing document", e);
                                    }
                                });
                    }
                } else {
                    Log.d("date", "Failed with: ", task.getException());
                }
            }
        });
        return inflater.inflate(R.layout.fragment_first, container, false);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listUpdate();
        enter = getActivity().findViewById(R.id.enter);

        myList = new ArrayList<>();
        predictedTime = getActivity().findViewById(R.id.predictedTime);

        setOnClickListener();

        recyclerView = getActivity().findViewById(R.id.recyclerView);
        recyclerAdapter = new com.example.project.RecyclerAdapter(myList, listener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // call sync recycle view here

//        myList.add(new Task("Task 1", 1));
//        myList.add(new Task("Task 2", 3));
//        myList.add(new Task("Task 3", 1));
//        myList.add(new Task("Task 4", 2));
//        myList.add(new Task("Task 5", 1));
//        myList.add(new Task("Task 6", 4));
//        myList.add(new Task("Task 7", 2));
//        myList.add(new Task("Task 8", 3));
//        small=3; medium=2; big=2; very_big=1;

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                startActivityForResult(intent, 1);
//                listUpdate();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setOnClickListener() {
        listener = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent (getActivity(), MainActivity3.class);
                intent.putExtra("time", myList.get(position).time);
                intent.putExtra("pos", position);
                intent.putExtra("uId", myList.get(position).uId);
                startActivityForResult(intent, 2);
            }
        };
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            String name = myList.get(position).name;
            int id=myList.get(position).id;
            String uId=myList.get(position).uId;
            long time=myList.get(position).time;
            String sTime=myList.get(position).sTime;
            switch(direction){
                case ItemTouchHelper.LEFT:

                    // delete task from firestore

                    db.collection("users").document(user.getUid()).collection("Tasks").document(uId)
                            .delete();
                    listUpdate();
//                    myList.remove(position);
//                    recyclerAdapter.notifyItemRemoved(position);

//                    removeTag(id);

                    Snackbar.make(recyclerView, "Deleted: " + name, Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // add the task back to firestore
                                    firestoreUpdate(id,name,sTime,time,uId);
                                    listUpdate();
//                                    myList.add(position, deleted);

//                                    addTag(id);

//                                    recyclerAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;

                case ItemTouchHelper.RIGHT:
//                    Task task = myList.get(position);
//                    String taskName = task.name;
//                    archivedTasks.add(task);
//                    int id2=myList.get(position).id;
//                    myList.remove(position);
//                    recyclerAdapter.notifyItemRemoved(position);
                    db.collection("users").document(user.getUid()).collection("Tasks").document(uId)
                            .update("completed", true);
                    db.collection("users").document(user.getUid()).collection("Completed").document(date.toString())
                            .update("completedTasks", FieldValue.increment(1));
                    Log.d("complete","1");

                    //update completed field




//                    db.collection("users").document(user.getUid()).collection("Completed").document(uId)
//                            .update(today, );

                    listUpdate();
//                    removeTag(id2);

                    Snackbar.make(recyclerView, "Completed: " + name, Snackbar.LENGTH_LONG)
                            .setAction( "UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    db.collection("users").document(user.getUid()).collection("Tasks").document(uId)
                                            .update("completed", false);
                                    //update completed field
                                    db.collection("users").document(user.getUid()).collection("Completed").document(date.toString())
                                            .update("completedTasks", FieldValue.increment(-1));
                                    listUpdate();
                                }
                            }).show();
                    break;

                // call sync recycle view here

            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            // copy paste it from: https://github.com/xabaras/RecyclerViewSwipeDecorator
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftLabel("Delete Task")
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_done)
                    .addSwipeRightLabel("Task Completed")
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            // call sync recycle view here
        }
    };
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // this function adds new tasks to the list
        if(requestCode==1 && resultCode==getActivity().RESULT_OK){
            String name=data.getStringExtra("a");
            int id=data.getIntExtra("b",0);

            firestoreUpdate(id,name,"00:00:00",0,"NULL");

            listUpdate();
//            addTag(id);

//            myList.add(new Task(name, id));

            // to update the list
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerView.setAdapter(recyclerAdapter);


            // call sync recycle view here
        }
        // this function gets the time spent on a task
        if(requestCode==2 && resultCode==getActivity().RESULT_OK){
            int position=data.getIntExtra("position", 0);
            //update time here
            String uId=data.getStringExtra("uId");
            long time=data.getLongExtra("time",0);
            String sTime=data.getStringExtra("sTime");
            db.collection("users").document(user.getUid()).collection("Tasks").document(uId)
                    .update(
                            "time", time,
                            "sTime",sTime

                    );
            listUpdate();
//            myList.get(position).sTime=data.getStringExtra("sTime");
//
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerView.setAdapter(recyclerAdapter);

            // call sync recycle view here
        }
    }

    /////////////////////////Helper functions///////////////////////////////////////////

    String calcSTime(long time){
        String sTime ="";
        long sec=time/1000,min=0,hr=0;
        if(sec>=60)
        {
            min=sec/60;
            sec=sec%60;
        }
        if(min>=60)
        {
            hr=min/60;
            min=min%60;
        }

        if(hr<10)
            sTime+='0';
        sTime+=String.valueOf(hr)+":";
        if(min<10)
            sTime+='0';
        sTime+=String.valueOf(min)+":";
        if(sec<10)
            sTime+='0';
        sTime+= String.valueOf(sec);
        Log.d("TAG", "calcSTime: " + sTime);
        return sTime;
    }
    public void Predict() {
//        tagUpdate();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("time");
                            long time = Integer.parseInt(data) - 98;
                            data = calcSTime(time*60*1000);
                            predictedTime.setText("Predicted Time: "+ data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_LONG);
                    }

                }){

            @Override
            protected Map<String, String> getParams(){
                // update tag details

                Map<String,String> params = new HashMap<String,String>();
                params.put("small", String.valueOf(small));
                params.put("medium", String.valueOf(medium));
                params.put("big", String.valueOf(big));
                params.put("very_big", String.valueOf(very_big));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }
    public void addTag(int id){
        switch(id){
            case 1:
                small++;
                break;
            case 2:
                medium++;
                break;
            case 3:
                big++;
                break;
            case 4:
                very_big++;
                break;
        }
        Predict();
    }
    private void firestoreUpdate(int id,String name,String sTime,long time,String uId) {
        //firestore
        Map<String,Object> taskDetail=new HashMap<>();
        taskDetail.put("name",name);
        taskDetail.put("id",id);
        taskDetail.put("time",time);
        taskDetail.put("completed",false);
        taskDetail.put("sTime",sTime);
        if(uId!="NULL") {
            taskDetail.put("uId",uId);
            db.collection("users").document(user.getUid()).collection("Tasks").document(uId).set(taskDetail, SetOptions.merge());
        }
        else {
            DocumentReference docref = db.collection("users").document(user.getUid()).collection("Tasks").document();
            taskDetail.put("uId", docref.getId());
            db.collection("users").document(user.getUid()).collection("Tasks").document(docref.getId()).set(taskDetail, SetOptions.merge());
        }
    }
    private void listUpdate() {
        db.collection("users").document(user.getUid()).collection("Tasks")
                .whereEqualTo("completed", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myList.clear();
                            small=0;
                            big=0;
                            medium=0;
                            very_big=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Task tt=document.toObject(Task.class);
                                Log.d("firebase",tt.getName());
                                myList.add(tt);
                                addTag(tt.getId());
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(recyclerAdapter);

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        Predict();

    }
//    public void removeTag(int id){
//        switch(id){
//            case 1:
//                small--;
//                break;
//            case 2:
//                medium--;
//                break;
//            case 3:
//                big--;
//                break;
//            case 4:
//                very_big--;
//                break;
//        }
//        Predict();
//    }
}