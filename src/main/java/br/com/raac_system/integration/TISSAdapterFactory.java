package br.com.raac_system.integration;

import br.com.raac_system.entity.InsuranceConfig;
import org.springframework.stereotype.Component;

@Component
public class TISSAdapterFactory {

    private final MockTISSAdapter mockAdapter;
    private final BradescoTISSAdapter bradescoAdapter;

    public TISSAdapterFactory(MockTISSAdapter mockAdapter, BradescoTISSAdapter bradescoAdapter) {
        this.mockAdapter = mockAdapter;
        this.bradescoAdapter = bradescoAdapter;
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
                    // return unimedAdapter; (using mock for now)
                    return mockAdapter;
                } else {
                    return mockAdapter;
                }
            default:
                return mockAdapter;
        }
    }
}
