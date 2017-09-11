package com.rikardo308.sharecontacts;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rikardo308.sharecontacts.Model.EmployeeInfo;

import java.util.List;

public class EmployeeInfoAdapter extends RecyclerView.Adapter<EmployeeInfoAdapter.MyViewHolder> {

    private List<EmployeeInfo> employeeInfoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView first_name, last_name, company,city,country,phone;

        public MyViewHolder(View view) {
            super(view);
            first_name = (TextView) view.findViewById(R.id.contact_first_name);
            phone      = (TextView) view.findViewById(R.id.contact_phone);
        }
    }


    public EmployeeInfoAdapter(List<EmployeeInfo> employeeInfoList) {
        this.employeeInfoList = employeeInfoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EmployeeInfo employeeInfo = employeeInfoList.get(position);
        holder.first_name.setText(employeeInfo.getFirstName());
        holder.phone.setText(employeeInfo.getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return employeeInfoList.size();
    }
}