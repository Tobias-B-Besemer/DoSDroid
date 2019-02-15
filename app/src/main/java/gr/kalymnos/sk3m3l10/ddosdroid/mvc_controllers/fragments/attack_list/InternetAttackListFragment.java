package gr.kalymnos.sk3m3l10.ddosdroid.mvc_controllers.fragments.attack_list;

import android.util.Log;

import gr.kalymnos.sk3m3l10.ddosdroid.pojos.attack.Attack;

import static gr.kalymnos.sk3m3l10.ddosdroid.constants.NetworkTypes.INTERNET;

public class InternetAttackListFragment extends AttackListFragment {
    private static final String TAG = AttackListFragment.TAG + "Internet";

    @Override
    public void onAttackUpload(Attack uploadedAttack) {
        if (uploadedAttack.getNetworkType() == INTERNET) {
            cacheAttackAccordingToContentType(uploadedAttack);
            viewMvc.bindAttacks(cachedAttacks);
        }
    }

    @Override
    public void onAttackUpdate(Attack changedAttack) {
        if (changedAttack.getNetworkType() == INTERNET) {
            cachedAttacks.remove(changedAttack);
            cacheAttackAccordingToContentType(changedAttack);
            viewMvc.bindAttacks(cachedAttacks);
        }
    }
}
