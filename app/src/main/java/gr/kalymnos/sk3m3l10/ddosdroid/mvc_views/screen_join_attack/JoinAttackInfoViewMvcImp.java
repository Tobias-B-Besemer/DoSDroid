package gr.kalymnos.sk3m3l10.ddosdroid.mvc_views.screen_join_attack;

import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gr.kalymnos.sk3m3l10.ddosdroid.R;

public class JoinAttackInfoViewMvcImp implements JoinAttackInfoViewMvc {
    private View root;
    private TextView website, date, attackForce, networkConf, launchTime;
    private FloatingActionButton fab;

    public JoinAttackInfoViewMvcImp(LayoutInflater inflater, ViewGroup container) {
        initViews(inflater, container);
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.screen_join_attack_info, container, false);
        website = root.findViewById(R.id.website_textview);
        date = root.findViewById(R.id.tv_website_creation_time);
        attackForce = root.findViewById(R.id.tv_attack_force);
        launchTime = root.findViewById(R.id.tv_launch_time);
        networkConf = root.findViewById(R.id.network_configuration);
        fab = root.findViewById(R.id.fab);
    }

    @Override
    public void setOnJoinAttackClickListener(OnJoinAttackClickListener listener) {
        fab.setOnClickListener(view -> {
            if (listener != null) {
                listener.onJoinAttackClicked();
            }
        });
    }

    @Override
    public void bindWebsite(String website) {
        this.website.setText(website);
    }

    @Override
    public void bindCreationDate(String date) {
        String prefix = root.getContext().getResources().getString(R.string.attack_date_prefix);
        this.date.setText(prefix + " " + date);
    }

    @Override
    public void bindAttackForce(int count) {
        attackForce.setText(attackForce.getContext().getString(R.string.people_joined_this_attack) + " " + count);
    }

    @Override
    public void bindLaunchDate(String text) {
        launchTime.setText(text);
    }

    @Override
    public void bindNetworkConfiguration(String networkConf) {
        this.networkConf.setText(networkConf);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
