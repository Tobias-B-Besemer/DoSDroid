package gr.kalymnos.sk3m3l10.ddosdroid.mvc_model.attack.connectivity.client;

import android.content.Context;

import gr.kalymnos.sk3m3l10.ddosdroid.mvc_model.attack.repository.AttackDeletionReporter;
import gr.kalymnos.sk3m3l10.ddosdroid.mvc_model.attack.repository.FirebaseAttackDeletionReporter;
import gr.kalymnos.sk3m3l10.ddosdroid.mvc_model.attack.service.AttackScript;
import gr.kalymnos.sk3m3l10.ddosdroid.mvc_model.attack.service.AttackService;
import gr.kalymnos.sk3m3l10.ddosdroid.pojos.attack.Attack;

public class Client implements ConnectionManager.ConnectionManagerListener, AttackDeletionReporter.AttackDeletionListener {
    private Context context;
    private Attack attack;
    private AttackScript attackScript;
    private AttackDeletionReporter attackDeletionReporter;
    private ConnectionManager connectionManager;
    private ClientConnectionListener callback;

    public interface ClientConnectionListener {
        void onClientConnected(Client thisClient, Attack attack);

        void onClientConnectionError();

        void onClientDisconnected(Client thisClient, Attack attack);
    }

    public void setClientConnectionListener(ClientConnectionListener listener) {
        this.callback = listener;
    }

    public void connect(Context context, Attack attack) {
        initializeFields(context, attack);
        connectionManager.connect();
    }

    private void initializeFields(Context context, Attack attack) {
        this.context = context;
        this.attack = attack;
        this.attackScript = new AttackScript(attack.getWebsite());
        this.attackDeletionReporter = new FirebaseAttackDeletionReporter();
        this.attackDeletionReporter.setAttackDeletionListener(this);
        initializeConnectionManagerIfNotNull();
    }

    private void initializeConnectionManagerIfNotNull() {
        if (connectionManager == null) {
            ConnectionManager.Factory factory = new ConnectionManager.FactoryImp();
            connectionManager = factory.create(context, attack);
            connectionManager.setConnectionManagerListener(this);
        }
    }

    public void disconnect() {
        connectionManager.disconnect();
    }

    @Override
    public void onManagerConnection() {
        attackDeletionReporter.attach();
        attackScript.start();
        callback.onClientConnected(this, attack);
    }

    @Override
    public void onManagerError() {
        callback.onClientConnectionError();
    }

    @Override
    public void onManagerDisconnection() {
        releaseResources();
        callback.onClientDisconnected(this, attack);
    }

    private void releaseResources() {
        context = null;
        callback = null;
        attackScript.stopExecution();
        attackDeletionReporter.detach();
    }

    @Override
    public void onAttackDeleted(Attack attack) {
        AttackService.Action.stopAttack(attack, context);
    }
}