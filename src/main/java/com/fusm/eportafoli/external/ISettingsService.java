package com.fusm.eportafoli.external;

import com.fusm.eportafoli.model.external.SettingRequest;
import org.springframework.stereotype.Service;

@Service
public interface ISettingsService {
    String getSetting(SettingRequest settingRequest);
}
