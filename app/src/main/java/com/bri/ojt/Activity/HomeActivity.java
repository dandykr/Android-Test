package com.bri.ojt.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bri.ojt.Dialog.HomeDialog;
import com.bri.ojt.Dialog.TarikTunai;
import com.bri.ojt.Dialog.TransferOption;
import com.bri.ojt.Model.Request.GetVAbyVA;
import com.bri.ojt.Model.Response.VAbyGroupResponseCashcard;
import com.bri.ojt.Network.API;
import com.bri.ojt.Network.ResponseInterface;
import com.bri.ojt.Network.RetrofitClient;
import com.bri.ojt.R;
import com.bri.ojt.Util.BRISignature;
import com.bri.ojt.Util.Consts;
import com.bri.ojt.Util.SnackbarUtil;
import com.bri.ojt.Util.ThousandSeparatorTextWatcher;
import com.bri.ojt.Widget.CurvedBottomNav;
import com.bri.ojt.Widget.DashboardView;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

public class HomeActivity extends AppCompatActivity implements TarikTunai, TransferOption, ResponseInterface {

    private API api;
    private ProgressBar progressSaldo, progressName;
    private Consts consts;
    private TextView saldo, name, noVA, corpName;
    private CurvedBottomNav bottomNav;
    private ConstraintLayout constraintLayout;
    private List<PieEntry> chartData = new ArrayList<>();
    private DashboardView dashboardView;
    private FloatingActionButton btnTransfer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(FLAG_SECURE);
        setContentView(R.layout.activity_home);

        Thread thread = new Thread(this::setView);
        thread.start();

        api = RetrofitClient.getHInstance(this).getAPI();
        consts = Consts.getInstance();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setView() {
        saldo = findViewById(R.id.txt_saldo);
        name = findViewById(R.id.txt_name);
        corpName = findViewById(R.id.txt_corp_name);
        noVA = findViewById(R.id.txt_no_va);
        progressSaldo = findViewById(R.id.progress_saldo);
        progressName = findViewById(R.id.progress_name);
        constraintLayout = findViewById(R.id.constraint_layout);
        dashboardView = findViewById(R.id.dashboard);
        bottomNav = findViewById(R.id.bottom_nav);
        btnTransfer = findViewById(R.id.fab);
        bottomNav.setOnNavigationItemSelectedListener(navItemListener);
        btnTransfer.setOnClickListener(onClickListener);

        runOnUiThread(() -> {
            Handler handler = new Handler();
            handler.postDelayed(HomeActivity.this::getDataVA, 500);
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navItemListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.action_mutasi:
                startActivity(new Intent(HomeActivity.this, MutasiActivity.class));
                break;
            case R.id.action_statement:
                startActivity(new Intent(HomeActivity.this, StatementActivity.class));
                break;
            case R.id.action_tartun:
                HomeDialog tunaiDialog = new HomeDialog().newInstance(Consts.DIALOG_TARTUN);
                tunaiDialog.show(getSupportFragmentManager(), tunaiDialog.getTag());
                break;
//            case R.id.action_transfer:
//                HomeDialog transferDialog = new HomeDialog().newInstance(Consts.DIALOG_TRANSFER);
//                transferDialog.show(getSupportFragmentManager(), transferDialog.getTag());
//                break;
            case R.id.action_info:
                startActivity(new Intent(HomeActivity.this, InfoActivity.class));
                break;
        }
        return false;
    };

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.fab:
                HomeDialog transferDialog = new HomeDialog().newInstance(Consts.DIALOG_TRANSFER);
                transferDialog.show(getSupportFragmentManager(), transferDialog.getTag());
                break;
        }
    };

    private void getDataVA() {
        final GetVAbyVA vAbyVA = new GetVAbyVA(consts.getVAEncrypt());

        Map<String, String> map = BRISignature.getHeader(Consts.PATH_VA_BY_VA, "POST", consts.getToken()
                , consts.getGson().toJson(vAbyVA));

        Call<VAbyGroupResponseCashcard> call = api.getVAbyVA(map, vAbyVA);

        call.enqueue(new Callback<VAbyGroupResponseCashcard>() {
            @Override
            public void onResponse(Call<VAbyGroupResponseCashcard> call, Response<VAbyGroupResponseCashcard> response) {
                if (response.code() == 401) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(Consts.KEY_SESSION_END, true);
                    startActivity(intent);
                }
                VAbyGroupResponseCashcard vAbyGroupResponse = response.body();
                if (vAbyGroupResponse != null) {
                    if(response.code() == 200 && vAbyGroupResponse.getResponseCode().equalsIgnoreCase("00")) {

                        setChartData(vAbyGroupResponse.getDT().get(0));

                        runOnUiThread(() -> {
                            progressSaldo.setVisibility(View.GONE);
                            progressName.setVisibility(View.GONE);
                            String _saldo = String.format("Rp. %s", ThousandSeparatorTextWatcher.AddThousandSeparator(vAbyGroupResponse.getDT().get(0).getSisaPlafon()));
                            saldo.setText(_saldo);
                            corpName.setText(vAbyGroupResponse.getDT().get(0).getCorporateName());
                            name.setText(vAbyGroupResponse.getDT().get(0).getCustomerName());
                            noVA.setText(vAbyGroupResponse.getDT().get(0).getNomorVirtual());
                        });
                    } else {
                        Snackbar snackbar = SnackbarUtil.getSnackbar(HomeActivity.this, constraintLayout, vAbyGroupResponse.getResponseMessage());
                        snackbar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VAbyGroupResponseCashcard> call, Throwable t) {
                Snackbar snackbar = SnackbarUtil.getSnackbar(HomeActivity.this, constraintLayout, t.getMessage());
                snackbar.show();
            }
        });

    }

    private void setChartData(VAbyGroupResponseCashcard.DetailVA data) {
        chartData.clear();
        chartData.add(new PieEntry((float) data.getTotalPenarikan(), "Total Debet"));
        chartData.add(new PieEntry((float) data.getTotalSetoran(), "Total Kredit"));
        dashboardView.setTotalDebit(data.getTotalPenarikan());
        dashboardView.setTotalKredit(data.getTotalSetoran());
        dashboardView.setAlpha(1f);
        final Animation animMove = AnimationUtils.loadAnimation(this, R.anim.anim_move);
        dashboardView.startAnimation(animMove);

        dashboardView.setPieChartData(chartData);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //disable animation
//        overridePendingTransition(0,0);
    }

    @Override
    public void startActivity(Intent intent,@Nullable Bundle options) {
        super.startActivity(intent, options);
        //disable animation
//        overridePendingTransition(0,0);
    }

    @Override
    public void tarikEchannel() {
        startActivity(new Intent(HomeActivity.this, TarikTunaiActivity.class)
        .putExtra(Consts.KEY_TARTUN_FROM, Consts.TARTUN_ECHANNEL));
    }

    @Override
    public void tarikTeller() {
        startActivity(new Intent(HomeActivity.this, TarikTunaiActivity.class)
                .putExtra(Consts.KEY_TARTUN_FROM, Consts.TARTUN_TELLER));
    }

    @Override
    public void tfInternal() {
        startActivity(new Intent(HomeActivity.this, TransferActivity.class));
    }

    @Override
    public void tfExternal() {
        startActivity(new Intent(HomeActivity.this, TransferExternalActivity.class));
    }

    @Override
    public void OnBadRequest(String message) {

    }

    @Override
    public void OnUnauthorized(String message) {

    }
}
