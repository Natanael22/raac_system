package br.com.raac_system.integration;

import br.com.raac_system.entity.InsuranceConfig;
import org.springframework.stereotype.Component;

@Component
public class TISSAdapterFactory {

    private final MockTISSAdapter mockAdapter;
    private final BradescoTISSAdapter bradescoAdapter;
    private final UnimedTISSAdapter unimedAdapter;

    public TISSAdapterFactory(MockTISSAdapter mockAdapter, BradescoTISSAdapter bradescoAdapter,
            UnimedTISSAdapter unimedAdapter) {
        this.mockAdapter = mockAdapter;
        this.bradescoAdapter = bradescoAdapter;
        this.unimedAdapter = unimedAdapter;
    }

    public TISSPort getAdapter(InsuranceConfig config) {
        switch (config.getEnvironment()) {
            case DEV:
                return mockAdapter;
            case HML:
            case PROD:
                if ("BRADESCO".equalsIgnoreCase(config.getOperatorName())) {
                    return bradescoAdapter;
                } else if ("UNIMED".equalsIgnoreCase(config.getOperatorName())) {
                    return unimedAdapter;
                } else {
                    return mockAdapter;
                }
            default:
                return mockAdapter;
        }
    }
}
