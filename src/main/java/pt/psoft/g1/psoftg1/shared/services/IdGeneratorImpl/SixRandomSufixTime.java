package pt.psoft.g1.psoftg1.shared.services.IdGeneratorImpl;

import org.springframework.context.annotation.Profile;

import pt.psoft.g1.psoftg1.shared.services.IdGenerator;

@Profile("six-sufix-time")
public class SixRandomSufixTime implements IdGenerator {

    @Override
    public String generate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generate'");
    }
    
}
