package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

import java.io.Serializable;
import java.util.List;

@RedisHash("ReaderDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReaderDataModelRedis implements Serializable {

    @Id
    private String readerNumber;

    @Indexed
    private String username;

    @Indexed
    private String phoneNumber;

    private String birthDate;
    private boolean gdprConsent;
    private boolean marketingConsent;
    private boolean thirdPartySharingConsent;
    private List<String> interests;
    private String photoURI;
}
