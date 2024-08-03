package cc.mrbird.batch.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class People {
    private String  name;
    private  String  age;
    private  String  adress;
    private  String  idCard;

}