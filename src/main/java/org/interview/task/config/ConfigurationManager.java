package org.interview.task.config;

import lombok.NoArgsConstructor;
import org.aeonbits.owner.ConfigCache;

@NoArgsConstructor
public class ConfigurationManager {

    public static MainConfig config() {
        return ConfigCache.getOrCreate(MainConfig.class);
    }
}
