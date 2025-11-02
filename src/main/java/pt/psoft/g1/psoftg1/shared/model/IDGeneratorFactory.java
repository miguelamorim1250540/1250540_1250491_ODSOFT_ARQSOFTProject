package pt.psoft.g1.psoftg1.shared.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class IDGeneratorFactory {

    private AlgorithmId algorithmId;

    @Value("${algorithm}")
    private String algorithmName;

    private final ApplicationContext applicationContext;

    @Autowired
    public IDGeneratorFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        this.algorithmId = applicationContext.getBean(algorithmName, AlgorithmId.class);
    }

    public String generateId(String businessId) {
        return algorithmId.generateId(businessId);
    }

    public String generateIdUsing(String algorithmName, String businessId) {
        AlgorithmId algorithm = applicationContext.getBean(algorithmName, AlgorithmId.class);
        return algorithm.generateId(businessId);
    }

    public AlgorithmId getGenerator(String algorithmName) {
        return applicationContext.getBean(algorithmName, AlgorithmId.class);
    }

}
