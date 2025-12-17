package in.bhavesh.billingsoftware.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequest {


    private String name;
    private BigDecimal price;

    @JsonProperty("categoryId")
    private String categoryid;

    private String description;


}
