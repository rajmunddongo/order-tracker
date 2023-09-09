package onlab.aut.bme.hu.java.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetProfileInfoResponse {

    private String name;
    private String address;
    private byte[] profilePicture;
}
