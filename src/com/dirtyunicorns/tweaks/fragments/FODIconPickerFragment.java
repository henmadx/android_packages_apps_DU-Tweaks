/*
 * Copyright (C) 2020 The Evolution X Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dirtyunicorns.tweaks.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.logging.nano.MetricsProto;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class FODIconPickerFragment extends SettingsPreferenceFragment {

    private static final String KEY_SCREEN_OFF_FOD = "screen_off_fod";
    private SwitchPreference mScreenOffFOD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_fod_picker);

        mResolver = getActivity().getContentResolver();

        getActivity().getActionBar().setTitle(R.string.fod_icon_picker_title);

        mFooterPreferenceMixin.createFooterPreference().setTitle(R.string.fod_icon_picker_footer);

        boolean mScreenOffFODValue = Settings.System.getInt(mResolver,
                Settings.System.SCREEN_OFF_FOD, 0) != 0;

        mScreenOffFOD = (SwitchPreference) findPreference(KEY_SCREEN_OFF_FOD);
        mScreenOffFOD.setChecked(mScreenOffFODValue);
        mScreenOffFOD.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mScreenOffFOD) {
            int mScreenOffFODValue = (Boolean) newValue ? 1 : 0;
            Settings.System.putInt(mResolver, Settings.System.SCREEN_OFF_FOD, mScreenOffFODValue);
            Settings.Secure.putInt(mResolver, Settings.Secure.DOZE_ALWAYS_ON, mScreenOffFODValue);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.DIRTYTWEAKS;
    }
}
