package top.codezx.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SysUsers {
    private Integer count;
    private String placeName;
    private String sportProject;
    private String userId;
    private Integer limit;
    private Date arrivalDate;



}
